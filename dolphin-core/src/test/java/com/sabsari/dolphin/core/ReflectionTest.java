package com.sabsari.dolphin.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ReflectionTest {
    
//    @Test
    public void _맵조작테스트() {
        Map<String, String> map = new HashMap<>();
        map.put("key0", "value0");
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        System.out.println(map);
        
        for (Map.Entry<String, String> e : map.entrySet()) {
            map.put(e.getKey(), e.getValue() + "__");
        }
        
        System.out.println(map);
    }
    
    @Test
    public void _객체필드변경테스트() {
        TestData data = new TestData();
        TrimObject.doProcess(new Object());
        
        System.out.println(data);
                
        int counter = 1;
        long start = System.currentTimeMillis();

        for (int i=0 ; i < counter ; i++) {
            TrimObject.doProcess(data);
        }
        
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        System.out.println(counter + " 건 처리 소요시간: " + elapsed + "ms");
        System.out.println("한 건당 평균 " + elapsed / (long)counter + "ms");
        
        System.out.println(data);
        
    }
    
    public static class TrimObject extends StringFieldsManipulation {

        private static TrimObject instance = new TrimObject();
        
        public static void doProcess(Object o) {
            instance.process(o);
        }
        
        private TrimObject() {}
        
        @Override
        protected String manipulate(String str) {
            if (str == null)
                return null;
            
            String t = str.trim();
            
            if (t.length() == 0)
                return null;
            else
                return t;
        }

        @Override
        protected String getBasePackage() {            
            return "com.sabsari";
        }        
    }
    
    @Getter
    @Setter
    @ToString
    public static class TestData {
        
        private int a;
        
        private Map<String, Object> map;
        
        private Random rand;
        
        private String key;
        
        private String id;
        
        private String ci;
        
        private String name;
        
        private String email;
        
        private Object nothing;
        
        private InnerData inner;
        
        public TestData() {
            a = 1;
            map = new HashMap<String, Object>();
            map.put("key0", "  value1");
            map.put("key1", new InnerData());
            map.put("key2", null);
            map.put("key2", 1004);
            rand = new Random();
            key = "key";
            id = null;
            ci = "";
            name = "  ";
            email = " email   ";
            nothing = null;
            inner = new InnerData();
        }
    }
    
    @Getter
    @Setter
    @ToString
    public static class InnerData {
        private String name;
        
        private List<Object> list;
        
        public InnerData() {
            name = " name    ";
            list = new ArrayList<>();
            list.add(" item  ");
            list.add(null);
            list.add(10);
        }
    }
}
