package com.sabsari.dolphin.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.WordUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class StringFieldsManipulation {
    
    private String basePackage = null;
    
    private Set<Class<?>> set;
        
    protected StringFieldsManipulation() {
        basePackage = null;
        initSet();
    }
    
    protected StringFieldsManipulation(String basePackageToCkeck) {
        basePackage = basePackageToCkeck;
        initSet();
    }
    
    private void initSet() {
        set = new HashSet<Class<?>>();
        set.add(Boolean.class);
        set.add(Character.class);
        set.add(Byte.class);
        set.add(Short.class);
        set.add(Integer.class);
        set.add(Long.class);
        set.add(Float.class);
        set.add(Double.class);
        set.add(Void.class);
    }

    public void process(Object o) {
        if (o == null)
            return;
        
        Class<?> c = o.getClass();
        if (c == String.class || isPrimitiveType(c) || !isInBasePackage(o)) {
            return;
        }
        
        Field[] fields = c.getDeclaredFields();
        
        for (Field f : fields) {
//            if (f.getType() == c) {
//                log.debug("Infinite circle!");
//                throw new IllegalStateException("an infinite circle was detected.");
//            }
            
            try {
                Method getter = c.getDeclaredMethod(getGetter(f.getName()));
                
                if (f.getType() == String.class) {                 
                    Method setter = c.getDeclaredMethod(getSetter(f.getName()), String.class);
                    String value = (String) getter.invoke(o);
                    setter.invoke(o, manipulate(value));
                }
                else {
                    Object obj = null;
                    obj = getter.invoke(o);
                    
                    if (obj == null) {
                        ;
                    }
                    else if (obj instanceof Collection) {
                        @SuppressWarnings("unchecked")
                        Collection<Object> col = (Collection<Object>)obj;
                        Object[] objs = col.toArray();                        
                        for (int i = 0; i < objs.length; i++) {
                            if (objs[i] == null || isPrimitiveType(objs[i].getClass())) {
                                continue;
                            }
                            
                            col.remove(objs[i]);                          
                            if (objs[i].getClass() == String.class) {                                
                                objs[i] = manipulate((String)objs[i]);                                
                            }
                            else {
                                process(objs[i]);
                            }
                            col.add(objs[i]);
                        }
                    }
                    else if (obj instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<Object, Object> map = (Map<Object, Object>)obj;
                        for (Entry<Object, Object> entry : map.entrySet()) {
                            if (entry.getValue() == null || isPrimitiveType(entry.getValue().getClass())) {
                                ;
                            }
                            else if (entry.getValue().getClass() == String.class) {
                                map.put(entry.getKey(), manipulate((String)entry.getValue()));
                            }
                            else {
                                process(entry.getValue());
                            }
                        }
                    }
                    else {                        
                        process(obj);
                    }
                }
            }
            catch (NoSuchMethodException ex) {
                log.debug("Not found method: " + ex.getMessage());
            }
            catch (Exception ex) {
                ex.printStackTrace();
                log.debug(ex.getMessage());
            }
        }
    }
    
    protected String getBasePackage() {
        return basePackage;
    }
    
    protected abstract String manipulate(String str);
    
    private boolean isInBasePackage(Object obj) {
        return basePackage == null || obj.getClass().getName().startsWith(basePackage);
    }
    
    private String getGetter(String name) {
        return "get" + WordUtils.capitalize(name);
    }
    
    private String getSetter(String name) {
        return "set" + WordUtils.capitalize(name);
    }
    
    public boolean isPrimitiveType(Class<?> cls) {
        return set.contains(cls);
    }
}
