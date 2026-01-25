package com.azathorpe.competitivecompaniononidea.service;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.competitivecompaniononidea.OIMessage;
import com.intellij.openapi.components.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public final class SocketService {
    private int port = 10043;

    public void startServer(){
        try(ServerSocket client = new ServerSocket(port)){
            while (true){
                try(Socket server = client.accept()){
                    System.out.println("New connection accepted " + server.getInetAddress() + ":" + server.getPort());
                    onReceive(server);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // When the port is received json from Competitive Companion
    // Do it own work
    public void onReceive(Socket client){
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

        System.out.println(builder.toString());
        OIMessage message = JSON.parseObject(builder.toString(), OIMessage.class);
        System.out.println(message);
        System.out.println(message.getTestCases());
    }


}
