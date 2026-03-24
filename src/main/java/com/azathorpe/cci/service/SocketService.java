package com.azathorpe.cci.service;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.cci.utils.PersistenceStorage;
import com.azathorpe.cci.model.Question;

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

    public static void startServer(){
        if(!isRunning){
            isRunning = true;
            Thread thread = new Thread(() -> {
                try(ServerSocket client = new ServerSocket(port)){
                    while (isRunning){
                        try(Socket server = client.accept()){
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
    public static void stopServer(){
        isRunning = false;
    }

    // When the port is received json from Competitive Companion
    // Do it own work
    public static void onReceive(Socket client){
        StringBuilder builder = new StringBuilder();
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if(line.contains("{") && line.contains("}"))
                    builder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(builder);
        Question message = JSON.parseObject(builder.toString(), Question.class);
//        PersistenceStorage.saveQuestionFile(message);
        //Switch old way to get inputStream..
        PersistenceStorage.saveSolvedFileInTemplate(message);
        PersistenceStorage.saveQuestionFile(message);
        System.out.println(message);
        System.out.println(message.getTestCases());
    }

    public static boolean isIsRunning() {
        return isRunning;
    }
}
