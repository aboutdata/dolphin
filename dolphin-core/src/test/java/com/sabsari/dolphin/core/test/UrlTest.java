package com.sabsari.dolphin.core.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class UrlTest {

    @Test
    public void _InputStreamTest2() {
        try {
            URL u = new URL("http://csb.stanford.edu/class/public/pages/sykes_webdesign/05_simple.html");
            URLConnection uc = u.openConnection();        
            System.out.println(uc.getContentEncoding());
            try (InputStream in = uc.getInputStream()) {
                int c;
                while ((c = in.read()) != -1) {
                    System.out.write(c);
                }
            }      
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    @Test
    public void _InputStreamTest() {
        try {
            URL u = new URL("http://csb.stanford.edu/class/public/pages/sykes_webdesign/05_simple.html");
            try (InputStream in = u.openStream()) {
                int c;
                while ((c = in.read()) != -1) {
                    System.out.write(c);
                }
            }      
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
