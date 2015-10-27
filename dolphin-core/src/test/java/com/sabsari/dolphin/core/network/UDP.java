package com.sabsari.dolphin.core.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.junit.Test;

public class UDP {

    @Test
    public void _udpDaytime() throws InterruptedException {
        try (DatagramSocket socket = new DatagramSocket(0)) {
            socket.setSoTimeout(15000);
            InetAddress host = InetAddress.getByName("time.nist.gov");
            DatagramPacket request = new DatagramPacket(new byte[1], 1, host, 13);
            DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
            socket.send(request);
            socket.receive(response);
            String result = new String(response.getData(), 0, response.getLength(), "US-ASCII");
            System.out.println(result);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
