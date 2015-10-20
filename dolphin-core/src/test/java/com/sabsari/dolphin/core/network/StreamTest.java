package com.sabsari.dolphin.core.network;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class StreamTest {
    
    @Test
    public void _test() throws IOException {
        long start = System.currentTimeMillis();
        
        generateCharacters(System.out, 10000); 
        
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        System.out.println("걸린시간: " + elapsed + "ms");
    }

    public static void generateCharacters(OutputStream out, int count) throws IOException {
        int firstPrintableCharacter     = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine   = 72;
        
        int start   = firstPrintableCharacter;
        int t = 0;
        while (t++ < count) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++) {
                out.write(
                        ((i - firstPrintableCharacter) % numberOfPrintableCharacters)
                        + firstPrintableCharacter
                        );
            }
            out.write('\r');
            out.write('\n');
            start = ((start + 1) - firstPrintableCharacter)
                    % numberOfPrintableCharacters + firstPrintableCharacter;
        }
    }
    
    public static void generateCharactersBuf(OutputStream out, int count) throws IOException {
        int firstPrintableCharacter     = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine   = 72;               
        int start   = firstPrintableCharacter;
        byte[] line = new byte[numberOfCharactersPerLine + 2];
        
        int t = 0;
        while (t++ < count) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++) {
                line[i - start] = (byte)((i - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter);                
            }
            line[numberOfCharactersPerLine] = (byte)'\r';
            line[numberOfCharactersPerLine + 1] = (byte)'\n';
            out.write(line);
            start = ((start + 1) - firstPrintableCharacter)
                    % numberOfPrintableCharacters + firstPrintableCharacter;
        }
    }
}
