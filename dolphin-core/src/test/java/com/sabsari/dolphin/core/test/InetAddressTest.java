package com.sabsari.dolphin.core.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.junit.Test;

public class InetAddressTest {
    @Test
    public void _isSpam() {
        System.out.println(isSpammer("207.34.56.23"));
    }
    
    public static boolean isSpammer(String arg) {
        try {
            InetAddress address = InetAddress.getByName(arg);
            byte[] quad = address.getAddress();
            String query = "sbl.spamhaus.org";
            for (byte octet : quad) {
                int unsignedByte = octet < 0 ? octet + 256 : octet;
                query = unsignedByte + "." + query;
            }
            
            InetAddress.getByName(query);
            return true;
        }
        catch (UnknownHostException ex) {
            return false;
        }
    }
    
    @Test
    public void _networkInterface_test0() throws SocketException {
        NetworkInterface ni = NetworkInterface.getByName("wlan0");
        System.out.println(ni);
        
        Enumeration<InetAddress> e = ni.getInetAddresses();
        while (e.hasMoreElements()) {
            InetAddress i = e.nextElement();
            System.out.println(i);
        }
    }
    
    @Test
    public void _networkInterface_test() throws SocketException {
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        while (n.hasMoreElements()) {
            NetworkInterface i = n.nextElement();
            System.out.println(i);
        }
    }
    
    @Test
    public void _addr_test() throws IOException  {
        InetAddress addr = InetAddress.getByName("www.google.com");
        System.out.println(addr.isReachable(10000));
    }
}
