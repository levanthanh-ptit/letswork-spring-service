package com.letswork.springservice.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface  ObjectUtil<V,T> {

    public T convertMethod(V v);

    public default List<T> convertToList(List<V> vList){
        List<T> tList = new ArrayList<>();
        for (V e: vList) {
            tList.add(convertMethod(e));
        }
        return tList;
    };
}
