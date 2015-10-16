package com.sabsari.dolphin.core.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.junit.Test;

public class ClientSocketTest {
    
    @Test
    public void _SocketInfo() {
        String host = "www.google.com";
        
        try (Socket socket = new Socket(host, 80)) {
            System.out.println("remote addr: " + socket.getInetAddress());
            System.out.println("remote port: " + socket.getPort());
            System.out.println("local addr: " + socket.getLocalAddress());
            System.out.println("local port: " + socket.getLocalPort());
        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch (SocketException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Test
    public void _PortScan() {
        String host = "localhost";
        int portRange = 1300;
        
        Socket socket = null;
        for (int i = 1024; i < portRange; i++) {
            try {
                System.out.print("scan port " + i);
//                socket = new Socket(host, i);       // 연결된 소켓 생성
                socket = new Socket();       // 연결된 소켓 생성
                SocketAddress addr = new InetSocketAddress(host, i);
                socket.connect(addr, 15);
                System.out.println(": found server!");                
            }
            catch(UnknownHostException ex) {
                ex.printStackTrace();
                break;
            }
            catch(IOException ex) {
                System.out.println(": none!");
            }
            finally {
                if (socket != null) {
                    try {
                        socket.close();
                    }
                    catch (IOException ex) {}
                }                
            }
            
        }
    }
    
    @Test
    public void _Dict() {
        String word = "gold";
        String host = "dict.org";
        int port = 2628;
        int timeout = 15000;
        
        Socket socket = null;
        try {
            socket = new Socket(host, port);
            socket.setSoTimeout(timeout);
            
            OutputStream out = socket.getOutputStream();
            Writer writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            
            writer.write("DEFINE fd-eng-lat " + word + "\r\n");
            writer.flush();
            
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.startsWith("250 ")) {
                    return;
                }
                else if (line.startsWith("552 ")) {
                    System.out.println("No definition found for " + word);
                    return;
                }
                else if (line.matches("\\d\\d\\d .*")) {
                    continue;
                }
                else if (line.trim().equals(".")) {
                    continue;
                }
                else
                    System.out.println(line);
            }
            
            writer.write("quit\r\n");
            writer.flush();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (socket != null) {
                try {
                    socket.close();
                }
                catch (IOException ex) {}
            }
        }
    }

    @Test
    public void _Daytime() throws UnknownHostException, IOException {
        try (Socket socket = new Socket("time.nist.gov", 13)) {
            socket.setSoTimeout(15000);
            InputStream in = socket.getInputStream();
            StringBuilder time = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(in, "ASCII");
            for (int c = reader.read(); c != -1; c = reader.read()) {
                time.append((char)c);
            }
            System.out.println(time.toString().trim());
        }
    }
    
}
