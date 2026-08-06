package runner.compiler;

import com.azathorpe.cci.model.TestCase;
import com.azathorpe.cci.utils.PersistentStorage;
import com.intellij.openapi.diagnostic.Logger;
import runner.Message;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CPPCompiler implements Compiler {

    private static final Logger log = Logger.getInstance(CPPCompiler.class);
    private static final String EXECUTABLE = PersistentStorage.getCompilePath() + File.separator + "run";

    @Override
    public String compile(String sourceFile) {
        log.info(sourceFile + " is compiling...");
        try {
            Process compileProcess = new ProcessBuilder(
                    "g++", "-o", EXECUTABLE, sourceFile
            ).start();
            boolean finished = compileProcess.waitFor(30, TimeUnit.SECONDS);
            if (!finished) {
                compileProcess.destroyForcibly();
                return "Compile Error: compilation timed out";
            }
            if (compileProcess.exitValue() != 0) {
                String errors = new String(compileProcess.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                return "Compile Error:\n" + errors;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return NO_ERR;
    }

    @Override
    public Message execute(String sourceFile, TestCase testCases) {
        try {
            Process process = new ProcessBuilder(EXECUTABLE).start();

            new Thread(() -> {
                try (OutputStream stdin = process.getOutputStream()) {
                    stdin.write(testCases.getInput().getBytes(StandardCharsets.UTF_8));
                } catch (IOException ignored) {
                }
            }).start();

            CompletableFuture<String> stdoutFuture = CompletableFuture.supplyAsync(() -> {
                try (var is = process.getInputStream()) {
                    return new String(is.readAllBytes(), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    return "";
                }
            });

            CompletableFuture<String> stderrFuture = CompletableFuture.supplyAsync(() -> {
                try (var es = process.getErrorStream()) {
                    return new String(es.readAllBytes(), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    return "";
                }
            });

            boolean finished = process.waitFor(10, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                return new Message("", "Time Limit Exceeded");
            }
            String output = stdoutFuture.get(5, TimeUnit.SECONDS);
            String errors = stderrFuture.get(5, TimeUnit.SECONDS);

            return new Message(output, errors);
        } catch (IOException | InterruptedException | java.util.concurrent.ExecutionException | TimeoutException e) {
            log.error(e.toString());
            return new Message("", e.toString());
        }
    }
}
