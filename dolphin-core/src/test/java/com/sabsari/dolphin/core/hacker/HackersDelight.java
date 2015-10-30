package com.sabsari.dolphin.core.hacker;

import org.junit.Test;

public class HackersDelight {

    @Test
    public void _보수확인() {
        int i = 18;
        System.out.println(binary(i));
        System.out.println(onesComplement(i));
        System.out.println(twosComplement(i));
    }
    
    @Test
    public void _가장오른쪽1비트끄기() {
        int i = 18;
        int r = i & (i-1);
        System.out.println("원본: " + Integer.toBinaryString(i));
        System.out.println("off: " + Integer.toBinaryString(r));
    }
    
    @Test
    public void _가장오른쪽0비트켜기() {
        int i = 18;
        int r = i | (i+1);
        System.out.println("원본: " + Integer.toBinaryString(i));
        System.out.println("on: " + Integer.toBinaryString(r));
    }
    
    public String onesComplement(int s) {
        return binary(-s - 1);
    }
    
    public String twosComplement(int s) {
        return binary(-s);
    }
    
    public String binary(int s) {        
        char[] b = Integer.toBinaryString(s).toCharArray();
        int length = 32;
        int paddingLen = length - b.length;
        char space = ' ';
        char padding = '0';
        int index = 0;
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i % 4 == 0 && i != 0) {
                sb.append(space);
            }
            if (i < paddingLen) {
                sb.append(padding);
            }
            else {
                sb.append(b[index++]);
            }
        }
        
        return sb.toString();
    }
}
