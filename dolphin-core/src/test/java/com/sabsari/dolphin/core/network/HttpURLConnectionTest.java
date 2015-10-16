package com.sabsari.dolphin.core.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.junit.Test;
import org.springframework.http.HttpMethod;

public class HttpURLConnectionTest {

    @Test
    public void _head() {
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod(HttpMethod.HEAD.name());
            System.out.println(url + " was last modified at " + new Date(http.getLastModified()));
            
            InputStream bIn = new BufferedInputStream(http.getInputStream());
            try (Reader in = new InputStreamReader(bIn)) {
                int c;
                while ((c = in.read()) != -1) {
                    System.out.write(c);
                }
            }
        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();            
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
