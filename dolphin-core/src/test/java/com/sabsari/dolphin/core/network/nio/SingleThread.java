package com.sabsari.dolphin.core.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

public class SingleThread {

    public  static int port = 18;
    
    @Test
    public void _nio() {
        
        ServerSocketChannel serverChannel;
        Selector selector;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            SelectionKey key = serverChannel.register(selector, SelectionKey.OP_ACCEPT);                        
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
                        ByteBuffer buffer = ByteBuffer.allocate(100);
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
