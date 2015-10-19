package com.sabsari.dolphin.core.network.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.sabsari.dolphin.core.value.ValueGenerator;
import com.sabsari.dolphin.core.value.ValueGeneratorImpl;

public class TestClient {

    public static final String END = "\r\n";

    private ValueGenerator msgGen;
    private String host;
    private int port;
    
    public TestClient(String host, int port) {
        this.host = host;
        this.port = port;
        msgGen = new ValueGeneratorImpl();
    }
    
    public int getPort() {
        return port;
    }
    
    public void doTest() throws InterruptedException {        
        try (Socket client = new Socket(host, port)) {
            System.out.println("접속 완료 서버 port:" + port + ", 클라이언트 port:" + client.getLocalPort());            
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            String message;
            String recvMsg;
            int count = 0;
            
            while (true) {
                message = msgGen.generateRefreshToken();               
                out.write(message + END);
                out.flush();
                System.out.println("phase " + ++count);
                System.out.println("보낸 메시지: " + message);                
                recvMsg = in.readLine();
                System.out.println("받은 메시지: " + recvMsg);
                System.out.println();
                Thread.sleep(1000);
            }            
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
