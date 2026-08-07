package com.azathorpe.cci.service;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.cci.model.Question;
import com.azathorpe.cci.utils.Debugger;
import com.azathorpe.cci.utils.Infos;
import com.azathorpe.cci.utils.PersistentStorage;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.ui.MessageDialogBuilder;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author Azathorpe
 * @version 1.0
 */
public class SocketService {
    private static final int port = 10043;

    private static volatile boolean isRunning = false;
    private static ServerSocket serverSocket;
    private static Thread serverThread;

    private static final Logger LOGGER = Logger.getInstance(SocketService.class);

    /**
     * 实际使用这些数据的函数，单独抽出来是为了方便测试
     */
    private static void runImpl(String buffer) {
        Debugger.log("Starting SocketService: ", buffer);
        Question question = JSON.parseObject(buffer, Question.class);
        String problemFile = PersistentStorage.createProblemFile(question);
        String testDataFile = PersistentStorage.createTestDataFile(question);

        LocalFileSystem.getInstance().refresh(false);
        VirtualFile vif = LocalFileSystem.getInstance().findFileByPath(problemFile);

        ApplicationManager.getApplication().invokeLater(() -> {
            boolean ok = MessageDialogBuilder.yesNo("New Problem Received",
                            "A new problem has been received from Competitive Companion. Do you want to open it?")
                    .ask(Infos.getProject());

            if (ok && vif != null)
                FileEditorManager.getInstance(Infos.getProject()).openFile(vif, true);
        });
    }

    /**
     * 开始监听端口
     */
    public static void startServer() {
        if (isRunning) return;

        LOGGER.info("Starting SocketService on port: " + port);
        isRunning = true;
        serverThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                while (isRunning) {
                    try (Socket socket = serverSocket.accept()) {
                        LOGGER.info("New connection: " + socket.getInetAddress() + ":" + socket.getPort());
                        onReceive(socket);
                    } catch (SocketException e) {
                        // serverSocket.close() 被 stopServer() 调用，正常退出
                    }
                }
            } catch (IOException e) {
                if (isRunning) LOGGER.error("Socket error", e);
            } finally {
                closeServerSocket();
            }
        }, "SocketService-Thread");
        serverThread.setDaemon(true);
        serverThread.start();
    }

    /**
     * 停止监听 — 关闭 ServerSocket 让 accept() 立刻返回
     */
    public static void stopServer() {
        isRunning = false;
        closeServerSocket();
    }

    private static void closeServerSocket() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException ignored) {
        }
    }

    private static void onReceive(Socket client) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("{") && line.contains("}"))
                    builder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        runImpl(builder.toString());
    }

    public static boolean isRunning() {
        return isRunning;
    }
}
