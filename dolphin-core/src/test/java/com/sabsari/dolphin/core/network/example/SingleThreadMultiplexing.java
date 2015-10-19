package com.sabsari.dolphin.core.network.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SingleThreadMultiplexing extends Thread {

    public static final String END = "\r\n";
    
    private int port = 18;
    
    private ServerSocketChannel serverChannel;
    private Selector selector;
    
    public SingleThreadMultiplexing(int port) {
        this.port = port;
    }
    
    public int getPort() {
        return port;
    }
    
    @Override
    public void run() {
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            System.out.println("서버생성 port:" + port);
            
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);                        
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        
        while (true) {
            try {
                selector.select();
            }
            catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
                   
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> itr = readyKeys.iterator();
            while (itr.hasNext()) {
                SelectionKey key = itr.next();
                itr.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();
                        SocketChannel connSocket = server.accept();
                        connSocket.configureBlocking(false);
                        SelectionKey clientKey = connSocket.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
//                        ByteBuffer buffer = ByteBuffer.allocate(1024); 
                        clientKey.attach(buffer);
                    }
                    
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel)key.channel();
                        ByteBuffer buffer = (ByteBuffer)key.attachment();
                        client.read(buffer);
                    }
                    
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel)key.channel();
                        ByteBuffer buffer = (ByteBuffer)key.attachment();
                        buffer.flip();
                        client.write(buffer);
                        buffer.compact();
                    }
                }
                catch (IOException ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    }
                    catch (IOException e) {}
                }
            }
        }
    }
}
