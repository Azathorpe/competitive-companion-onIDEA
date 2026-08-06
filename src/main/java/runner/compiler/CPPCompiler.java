package runner.compiler;

import com.azathorpe.cci.model.TestCase;
import com.azathorpe.cci.utils.FilesUtils;
import com.azathorpe.cci.utils.JdkPathUtil;
import com.azathorpe.cci.utils.PersistentStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import runner.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CPPCompiler implements Compiler{
    private static final Logger log = LoggerFactory.getLogger(CPPCompiler.class);

    @Override
    public String compile(String sourceFile) {
        log.info("{} is compiling...", sourceFile);
        // 编译
        try {
            Process compileProcess = new ProcessBuilder(
                   "g++","-o","run.exe",sourceFile
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
            Process process = new ProcessBuilder(
                    PersistentStorage.getCompilePath() + "/run.exe"
            ).start();

            new Thread(() -> {
                try (OutputStream stdin = process.getOutputStream()) {
                    stdin.write(testCases.getInput().getBytes(StandardCharsets.UTF_8));
                } catch (IOException ignored) {
                }
            }).start();

            // 线程1：消费 stdout
            CompletableFuture<String> stdoutFuture = CompletableFuture.supplyAsync(() -> {
                try (var is = process.getInputStream()) {
                    return new String(is.readAllBytes(), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    return "";
                }
            });

            // 线程2：消费 stderr
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
            log.error("{}", e.toString());
            return new Message("", e.toString());
        }
    }
}
