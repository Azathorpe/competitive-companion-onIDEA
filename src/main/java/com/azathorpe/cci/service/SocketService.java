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

/**
 * @author Azathorpe
 * @version 1.0
 */
public class SocketService {
    private static final int port = 10043;

    private static boolean isRunning = false;

    private static final Logger LOGGER = Logger.getInstance(SocketService.class);

    /**
     * 实际使用这些数据的函数，单独抽出来是为了方便测试
     *
     * @param buffer StringBuilder containing the json data received from Competitive Companion
     */
    private static void runImpl(String buffer) {
        Debugger.log("Starting SocketService: ", buffer);
        //把buffer传给JsonParser解析，得到题目信息
        Question question = JSON.parseObject(buffer, Question.class);
        //把题目信息传给ProblemCreator创建题目
        String problemFile = PersistentStorage.createProblemFile(question);
        //把题目信息传给TestCaseCreator创建测试用例
        String testDataFile = PersistentStorage.createTestDataFile(question);

        LocalFileSystem.getInstance().refresh(false);
        VirtualFile vif = LocalFileSystem.getInstance().findFileByPath(problemFile);

        ApplicationManager.getApplication().invokeLater(() -> {
            boolean ok = MessageDialogBuilder.yesNo("New Problem Received",
                            "A new problem has been received from Competitive Companion. Do you want to open it?")
                    .ask(Infos.getProject());

            if(ok && vif != null)
                FileEditorManager.getInstance(Infos.getProject()).openFile(vif, true);

        });


    }


    /**
     * 开始服务
     */
    public static void startServer() {
        LOGGER.info("Starting SocketService");
        LOGGER.info("Listening on port: " + port);
        LOGGER.info(String.valueOf(Infos.settings));

        if (!isRunning) {
            isRunning = true;
            Thread thread = new Thread(() -> {
                try (ServerSocket client = new ServerSocket(port)) {
                    while (isRunning) {
                        try (Socket server = client.accept()) {
                            System.out.println("New connection accepted " + server.getInetAddress() + ":" + server.getPort());
                            onReceive(server);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, "SocketService-Thread");
            thread.start();
        }
    }

    /**
     * Stop Server at any time
     */
    public static void stopServer() {
        isRunning = false;
    }

    // When the port is received json from Competitive Companion
    // Do it own work
    public static void onReceive(Socket client) {
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

    public static boolean isIsRunning() {
        return isRunning;
    }
}
