package com.sabsari.dolphin.core.test;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class StreamTest {
    
    @Test
    public void _test() throws IOException {
        generateCharacters2(System.out);
        
        Thread t = new Thread();
        t.interrupt();
    }

    public static void generateCharacters(OutputStream out) throws IOException {
        int firstPrintableCharacter     = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine   = 72;
        
        int start   = firstPrintableCharacter;
        while (true) {
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
    
    public static void generateCharacters2(OutputStream out) throws IOException {
        int firstPrintableCharacter     = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine   = 72;               
        int start   = firstPrintableCharacter;
        byte[] line = new byte[numberOfCharactersPerLine + 2];
        
        while (true) {
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
