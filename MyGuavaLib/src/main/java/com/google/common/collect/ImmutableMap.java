package com.google.common.collect;


import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImmutableMap<K, V> extends LinkedHashMap<K, V> implements Serializable {

    public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if ((map instanceof ImmutableMap)) {
            @SuppressWarnings("unchecked") // safe since map is not writable
                    ImmutableMap<K, V> kvMap = (ImmutableMap<K, V>) map;
            return kvMap;
        }
        ImmutableMap map1 = new ImmutableMap();

        Iterator<? extends Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<? extends K, ? extends V> next = iterator.next();
            map1.put(next.getKey(), next.getValue());
        }
        return map1;
    }
}

