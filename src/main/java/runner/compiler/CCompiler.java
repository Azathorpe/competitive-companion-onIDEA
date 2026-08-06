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

public class CCompiler implements Compiler {

    private static final Logger log = Logger.getInstance(CCompiler.class);

    private static String getExecutablePath() {
        String name = System.getProperty("os.name").toLowerCase().contains("win") ? "run.exe" : "run";
        return PersistentStorage.getCompilePath() + File.separator + name;
    }

    @Override
    public String compile(String sourceFile) {
        String exePath = getExecutablePath();
        log.info(sourceFile + " is compiling... -> " + exePath);

        // 确保编译输出目录存在
        File outputDir = new File(PersistentStorage.getCompilePath());
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try {
            Process compileProcess = new ProcessBuilder(
                    "gcc", "-o", exePath, sourceFile
            ).redirectErrorStream(true).start();
            boolean finished = compileProcess.waitFor(30, TimeUnit.SECONDS);
            if (!finished) {
                compileProcess.destroyForcibly();
                return "Compile Error: compilation timed out";
            }
            if (compileProcess.exitValue() != 0) {
                String errors = new String(compileProcess.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                return "Compile Error:\n" + errors;
            }
            // 确认编译产物确实已生成
            if (!new File(exePath).exists()) {
                return "Compile Error: gcc reported success but executable not found:\n" + exePath;
            }
        } catch (IOException e) {
            return "Compile Error: gcc not found. Please install GCC and add it to PATH.\n" + e.getMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return NO_ERR;
    }

    @Override
    public Message execute(String sourceFile, TestCase testCases) {
        String exePath = getExecutablePath();
        try {
            Process process = new ProcessBuilder(exePath).start();

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
