package com.sabsari.dolphin.core.network.example;

import org.junit.Test;

public class EchoTest {

    int port = 13;
    String host = "localhost";
    
    @Test
    public void _멀티쓰레드서버테스트() throws InterruptedException {        
        int sizeOfPool = 10;
        MultiThreaded server = new MultiThreaded(port, sizeOfPool);
        server.start();
                
        TestClient client = new TestClient(host, port);
        client.doTest();
    }
    
    @Test
    public void _멀티플렉싱서버테스트() throws InterruptedException {
        SingleThreadMultiplexing server = new SingleThreadMultiplexing(port);
        server.start();
        
        TestClient client = new TestClient(host, port);
        client.doTest();
    }
    
    @Test
    public void _비동기완료통지서버테스트() throws InterruptedException {
        AsyncCompletionHandler server = new AsyncCompletionHandler(port);
        server.start();
        
        TestClient client = new TestClient(host, port);
        client.doTest();
    }
}
