package com.azathorpe.cci.runner.compiler;

import com.azathorpe.cci.model.TestCase;
import com.azathorpe.cci.runner.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class KotlinCompiler implements Compiler {
    private static final Logger log = LoggerFactory.getLogger(KotlinCompiler.class);

    @Override
    public String compile(String sourceFile) {
        log.info(sourceFile + " is compiling...");
        // Compile
        try {
            Process compileProcess = new ProcessBuilder(
                    "kotlinc", sourceFile, "-include-runtime", "-d", sourceFile.replace(".kt", ".jar")
            ).start();
            boolean finished = compileProcess.waitFor(30, TimeUnit.SECONDS);
            if (!finished) {
                compileProcess.destroyForcibly();
                return "Compile Error: compilation timed out";
            }
            if (compileProcess.exitValue() != 0) {
                String errors = new String(compileProcess.getErrorStream().readAllBytes());
                return "Compile Error:\n" + errors;
            }
        } catch (Exception e) {
            log.error("Compilation failed", e);
            return "Compile Error: " + e.getMessage();
        }
        return NO_ERR;
    }

    @Override
    public Message execute(String sourceFile, TestCase testCases) {
        try {
            Process process = new ProcessBuilder(
                    "java", "-jar", sourceFile.replace(".kt", ".jar")
            ).start();

            new Thread(() -> {
                try (var stdin = process.getOutputStream()) {
                    stdin.write(testCases.getInput().getBytes());
                } catch (Exception ignored) {
                }
            }).start();

            String output = new String(process.getInputStream().readAllBytes());
            boolean finished = process.waitFor(30, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                return new Message("", "Execution Error: execution timed out");
            }
            if (process.exitValue() != 0) {
                String errors = new String(process.getErrorStream().readAllBytes());
                return new Message("", "Execution Error:\n" + errors);
            }
            return new Message(output, NO_ERR);
        } catch (Exception e) {
            log.error("Execution failed", e);
            return new Message("", "Execution Error: " + e.getMessage());
        }
    }
}
