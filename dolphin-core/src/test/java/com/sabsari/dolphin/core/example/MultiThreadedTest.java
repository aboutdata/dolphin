package com.sabsari.dolphin.core.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.junit.Test;

import com.sabsari.dolphin.core.value.ValueGenerator;
import com.sabsari.dolphin.core.value.ValueGeneratorImpl;

public class MultiThreadedTest {
    
    ValueGenerator msgGen = new ValueGeneratorImpl();

    @Test
    public void _멀티쓰레드서버테스트() throws InterruptedException {
        // 서버
        int port = 13;
        int sizeOfPool = 10;
        MultiThreaded server = new MultiThreaded(port, sizeOfPool);
        server.start();
        System.out.println("서버생성 port:" + server.getPort());
        
        // 클라이언트
        String host = "localhost";
        try (Socket client = new Socket(host, port)) {
            System.out.println("서버와 접속 완료 port:" + port);
            
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            String message;
            String recvMsg;
            
            while (true) {
                message = msgGen.generateRefreshToken();               
                out.write(message + MultiThreaded.END);
                out.flush();            
                System.out.println("보낸 메시지: " + message);                
                recvMsg = in.readLine();
                System.out.println("전송받은 메시지: " + recvMsg);
                System.out.println();
                Thread.sleep(1000);
            }
            
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
