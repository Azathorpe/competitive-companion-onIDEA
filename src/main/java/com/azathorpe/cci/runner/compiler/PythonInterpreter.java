package com.azathorpe.cci.runner.compiler;

import com.azathorpe.cci.model.TestCase;
import com.intellij.openapi.diagnostic.Logger;
import com.azathorpe.cci.runner.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PythonInterpreter implements Compiler {

    private static final Logger log = Logger.getInstance(PythonInterpreter.class);

    @Override
    public String compile(String sourceFile) {
        log.info(sourceFile + " — Python needs no compilation, skipping.");
        return NO_ERR;
    }

    @Override
    public Message execute(String sourceFile, TestCase testCases) {
        try {
            Process process = new ProcessBuilder("python", sourceFile).start();

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
        } catch (IOException e) {
            return new Message("", "Python not found. Please install Python and add it to PATH.\n" + e.getMessage());
        } catch (InterruptedException | java.util.concurrent.ExecutionException | TimeoutException e) {
            log.error(e.toString());
            return new Message("", e.toString());
        }
    }
}
