package com.sabsari.dolphin.core.network.whois;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Whois {
    
    public static void main(String[] args) {
        try {
            Whois whois = new Whois();
            String result = whois.lookUpNames("google", SearchFor.DOMAIN, SearchIn.NAME, false);
            System.out.println(result);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public final static int DEFAULT_PORT = 43;
    public final static String DEFAULT_HOST = "whois.internic.net";
    public final static String END = "\r\n";
    
    private int port = DEFAULT_PORT;
    private InetAddress host;
    
    public Whois() throws UnknownHostException {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }
    
    public Whois(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public Whois(InetAddress host) {
        this(host, DEFAULT_PORT);
    }
    
    public Whois(String hostname, int port) throws UnknownHostException {
        this(InetAddress.getByName(hostname), port);
    }
    
    public Whois(String hostname) throws UnknownHostException {
        this(hostname, DEFAULT_PORT);
    }
    
    public String lookUpNames(String target, SearchFor category, SearchIn group, boolean exactMatch) {
        String suffix = "";
        if (!exactMatch) suffix = ".";
        
        String prefix = category.label + " " + group.label;
        String query = prefix + target + suffix;
        
        try (Socket socket = new Socket()) {
            SocketAddress addr = new InetSocketAddress(host, port);
            socket.connect(addr);
            
            Writer out = new OutputStreamWriter(socket.getOutputStream(), "ASCII");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ASCII"));
            
            out.write(query + END);
            out.flush();            
            
            StringBuilder res = new StringBuilder();
            String theLine = null;
            while ((theLine = in.readLine()) != null) {
                res.append(theLine);
                res.append(END);
            }
            
            return res.toString();
        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (SocketException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public InetAddress getHost() {
        return host;
    }
    
    public void setHost(String hostname) throws UnknownHostException {
        this.host = InetAddress.getByName(hostname);
    }
    
    public enum SearchFor {
        ANY("Any"),
        NETWORK("Network"),
        PERSON("Person"),
        HOST("Host"),
        DOMAIN("Domain"),
        ORGANIZATION("Organization"),
        GROUP("Group"),
        GATEWAY("Gateway"),
        ASN("ASN");
        
        private String label;
        
        private SearchFor(String label) {
            this.label = label;
        }
        
        public String getLabel() {
            return label;
        }
    }
    
    public enum SearchIn {
        ALL(""),
        NAME("Name"),
        MAILBOX("Mailbox"),
        HANDLE("!");
        
        private String label;
        
        private SearchIn(String label) {
            this.label = label;
        }
        
        public String getLabel() {
            return label;
        }
    }
}
