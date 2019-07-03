package com.letswork.springservice.utils;

import java.lang.reflect.Field;

public class ObjectUtil<T,V> {
    private Field[] fieldT;
    private Field[] fieldV;
    private Class<T> typeT;
    private Class<V> typeV;

    public ObjectUtil(Class<T> typeT, Class<V> typeV){
        fieldT = typeT.getFields();
        fieldV = typeV.getFields();
        this.typeT = typeT;
        this.typeV = typeV;
    }
    public final void objectCopy(Class<T> tClass){
        for (Field t : fieldT) {
            for (Field v: fieldV) {
                if(t.getName().equals(v.getName())){
                    v = t;
                }
            }
        }
        try {
            V ret = typeV.newInstance();


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
