package com.sabsari.dolphin.core.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class UrlTest {
    
    @Test
    public void _proxy_system_property() {
        System.out.println(System.getProperty("http.proxyHost"));
        System.out.println(System.getProperty("http.proxyPort"));
    }

    @Test
    public void _URI() throws URISyntaxException {
        URI uri = new URI("http://www.google.com/%3c");
        System.out.println(uri.getScheme());
        System.out.println(uri.getAuthority());
        System.out.println(uri.getPath());
        System.out.println(uri.getRawPath());
    }
    
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
