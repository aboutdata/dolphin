package com.sabsari.dolphin.core.network.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.junit.Test;

public class SecureSocket {

    int port = 443;
    String host = "www.google.co.kr";
    
    @Test
    public void _httpsClient() {
        SSLSocketFactory f = (SSLSocketFactory)SSLSocketFactory.getDefault();
        SSLSocket socket = null;
        
        try {
            socket = (SSLSocket)f.createSocket(host, port);
            
            String[] supported = socket.getSupportedCipherSuites();
            for (String c : supported) {
                System.out.println(c);
            }
            socket.setEnabledCipherSuites(supported);
            
            Writer out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            // https는 GET요청 시 전체 URL 사용
            out.write("GET http://" + host + "/ HTTP/1.1\r\n");
            out.write("Host: " + host + "\r\n");
            out.write("\r\n");
            out.flush();
            
            // 응답읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            int c;
            while ((c = in.read()) != -1) {
                System.out.write(c);
            }
            
            
            
            // 헤더읽기
//            String s;
//            while (!(s = in.readLine()).equals("")) {
//                System.out.println(s);
//            }
//            System.out.println();
            
            // 길이 읽기
//            String contentLength = in.readLine();
//            int length = Integer.MAX_VALUE;
//            try {
//                length = Integer.parseInt(contentLength.trim(), 16);
//            }
//            catch (NumberFormatException ex) {
//                
//            }
//            System.out.println("contentLength: " + contentLength);
//            
//            int c;
//            int i = 1;
//            while ((c = in.read()) != -1 && i++ < length) {
//                System.out.write(c);
//            }
//            
//            System.out.println();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (socket != null) socket.close();
            }
            catch (IOException ex) {}
        }
                
    }
}
