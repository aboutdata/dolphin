package com.sabsari.dolphin.core.network.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreaded extends Thread {
    
    private int port;
    private int sizeOfPool;
    private ExecutorService pool = null;
    
    public int getPort() {
        return port;
    }

    public int getSizeOfPool() {
        return sizeOfPool;
    }
    
    public MultiThreaded(int port, int sizeOfPool) {
        this.port = port;
        this.sizeOfPool = sizeOfPool;
        pool = Executors.newFixedThreadPool(sizeOfPool);
    }

    public static boolean isOpen(ServerSocket ss) {
        return ss.isBound() && !ss.isClosed();
    }
        
    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("서버생성 port:" + port);
            while (true) {
                try {
                    Socket clientConn = server.accept();
                    pool.execute(new Task(clientConn));
                }
                catch (IOException ex) {
                    System.err.println("Exception: " + ex.getMessage());
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private static class Task implements Runnable {

        private Socket socket;
        
        public Task(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                
                String recvMsg = null;
                while(true) {
                    recvMsg = in.readLine();
                    out.write(recvMsg + "\r\n");
                    out.flush();
                }
            }
            catch (IOException ex) {
                System.err.println("Exception: " + ex.getMessage());
            }
            finally {
                try {
                    socket.close();
                }
                catch(IOException ex) {}
            }
        }
    }
}
