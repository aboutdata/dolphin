package com.sabsari.dolphin.core.network.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class Server {
    
    @Test
    public void _DaytimeServerThreadPool() {
        int port = 13;
        int sizeOfPool = 50;
        ExecutorService pool = Executors.newFixedThreadPool(sizeOfPool);
        
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                try {
                    Socket connection = server.accept();
                    pool.execute(new DaytimeTask(connection));
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
    
    private static class DaytimeTask implements Runnable {

        private static final String END = "\r\n";
        private Socket connection;
        
        public DaytimeTask(Socket connection) {
            this.connection = connection;
        }
        
        @Override
        public void run() {
            try {
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                out.write(new Date().toString() + END);
                out.flush();
            }
            catch (IOException ex) {
                System.err.println("Exception: " + ex.getMessage());
            }
            finally {
                try {
                    connection.close();
                }
                catch(IOException ex) {}
            }
        }
    }
    
    @Test
    public void _DaytimeServer() {
        int port = 13;
        String END = "\r\n";
        
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                try (Socket connection = server.accept()) {
                    Writer out = new OutputStreamWriter(connection.getOutputStream());
                    out.write(new Date().toString() + END);
                    out.flush();
                    connection.close();
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
}
