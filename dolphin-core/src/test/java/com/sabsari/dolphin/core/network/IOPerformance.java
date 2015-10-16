package com.sabsari.dolphin.core.network;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

public class IOPerformance {

    private int count = 1000000;
    private char[] dummy = new char[] {'d', 'u', 'm', 'm', 'y', ' ', 'd', 'u', 'm', 'm', 'y', ' ', 'd', 'u', 'm', 'm', 'y', ' ', 'd', 'u', 'm', 'm', 'y', ' ', 'd', 'u', 'm', 'm', 'y', ' ', 'd', 'u', 'm', 'm', 'y', ' ', 'd', 'u', 'm', 'm', 'y', ' ', 'd', 'u', 'm', 'm', 'y', ' ', 'd', 'u', 'm', 'm', 'y', ' ', 'd', 'u', 'm', 'm', 'y', ' ', 'd', 'u', 'm', 'm', 'y', '\r', '\n'};
        
    @Test
    public void _버퍼없음() throws IOException {
        long start = System.currentTimeMillis();
        
        FileWriter wr = new FileWriter("d:\\test1.txt");
        for (int i = 0; i <= count; i++) {
            wr.write(dummy, 0, dummy.length);
        }
        wr.close();
        
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        System.out.println("버퍼없음 : " + elapsed + "ms");
    }
    
    @Test
    public void _버퍼있음() throws IOException {
        long start = System.currentTimeMillis();
        
        BufferedWriter wr = new BufferedWriter(new FileWriter("d:\\test2.txt"));
        for (int i = 0; i <= count; i++) {
            wr.write(dummy, 0, dummy.length);
        }
        wr.close();
        
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        System.out.println("버퍼있음 : " + elapsed + "ms");
    }
    
//    @Test
    public void _버퍼있() throws IOException {
        long start = System.currentTimeMillis();
        
        PrintWriter wr = new PrintWriter(new BufferedWriter(new FileWriter("d:\\test2.txt")));
        for (int i = 0; i <= count; i++) {
            wr.println(dummy);
        }
        wr.close();
        
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        System.out.println("버퍼있 : " + elapsed + "ms");
    }
     /*


      public void test4() throws IOException {
         PrintWriter wr = new PrintWriter(new FileWriter("test4.txt"));
         for (int i = 0; i <= count; i++) {
            for (int j=0; j < size; j++) 
               wr.println(str1);
         }
         wr.close();
      }
      
      public void test5() throws IOException {
          PrintWriter wr = new PrintWriter(new BufferedWriter(new FileWriter("test5.txt")),true);
          for (int i = 0; i <= count; i++) {
             for (int j=0; j < size; j++) 
                wr.println(str1);
          }
          wr.close();
       }

       public void test6() throws IOException {
          PrintWriter wr = new PrintWriter(new FileWriter("test6.txt"),true);
          for (int i = 0; i <= count; i++) {
             for (int j=0; j < size; j++) 
                wr.println(str1);
          }
          wr.close();
       }
       */
}
