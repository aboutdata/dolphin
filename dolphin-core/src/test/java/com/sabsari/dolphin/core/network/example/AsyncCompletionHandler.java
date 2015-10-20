package com.sabsari.dolphin.core.network.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncCompletionHandler extends Thread {

    private int port;
    private AsynchronousServerSocketChannel server;
    
    public AsyncCompletionHandler(int port) {
        this.port = port;
    }
    
    public int getPort() {
        return port;
    }
    
    @Override
    public void run() {
        try {
            server = AsynchronousServerSocketChannel.open();
            server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            server.bind(new InetSocketAddress(port));
            System.out.println("서버생성 port:" + port);
            
            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(final AsynchronousSocketChannel connection, Object attachment) {
                    if (server.isOpen()) {
                        server.accept(null, this);
                    }
                    echo2(connection);
                }
                
                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("failed accept!");
                }
            });
            
            System.in.read();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }
    
    void echo(final AsynchronousSocketChannel asyncSocketChannel) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        
        try {
            while (true) {
                Future<Integer> readResult = asyncSocketChannel.read(buffer);
                readResult.get(10, TimeUnit.SECONDS);
                
                buffer.flip();
                Future<Integer> writeResult = asyncSocketChannel.write(buffer);
                writeResult.get(10, TimeUnit.SECONDS);
                
                if (buffer.hasRemaining()) {
                    buffer.compact();
                } 
                else {
                    buffer.clear();
                } 
            }
        }
        catch (InterruptedException | ExecutionException | TimeoutException | CancellationException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (asyncSocketChannel != null)
                    asyncSocketChannel.close();
            }
            catch (IOException ex) {}
        }
    }
    
    void echo2(final AsynchronousSocketChannel asyncSocketChannel) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        
        try {
            asyncSocketChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {                
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    attachment.flip();
                    Future<Integer> writeResult = asyncSocketChannel.write(attachment);
                    
                    try {
                        writeResult.get(10, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        System.out.println("write failed! " + e.getMessage());
                    }
                    
                    attachment.clear();
                    asyncSocketChannel.read(attachment, attachment, this);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.out.println("read failed! " + exc.getMessage());
                }
            });
        }
        catch (Exception ex) {
            System.out.println("echo failed! " + ex);
        }
        finally {
            try {
                if (asyncSocketChannel != null)
                    asyncSocketChannel.close();
            }
            catch (IOException ex) {}
        }
    }
}
