package com.google.common.collect;


import android.util.ArrayMap;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Maps {

    private Maps() {}

    private enum EntryFunction implements Function<Map.Entry<?, ?>, Object> {
        KEY {
            @Override
            
            public Object apply(Map.Entry<?, ?> entry) {
                return entry.getKey();
            }
        },
        VALUE {
            @Override
            
            public Object apply(Map.Entry<?, ?> entry) {
                return entry.getValue();
            }
        };
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K, V> Map<K, V> filterKeys(
            Map<K, V> unfiltered, final Predicate<? super K> keyPredicate) {
        if (unfiltered == null || unfiltered.size() == 0){
            return null;
        }
        checkNotNull(keyPredicate);
        Map map = new ArrayMap();

        Iterator<Map.Entry<K, V>> iterator = unfiltered.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, V> next = iterator.next();
            if (keyPredicate.apply(next.getKey())){
                map.put(next.getKey(), next.getValue());
            }
        }

        return map;
    }

    public static <K, V> ImmutableMap<K, V> toMap(
            Iterator<K> keys, Function<? super K, V> valueFunction) {
        checkNotNull(valueFunction);
        // Using LHM instead of a builder so as not to fail on duplicate keys
        Map<K, V> builder = newLinkedHashMap();
        while (keys.hasNext()) {
            K key = keys.next();
            builder.put(key, valueFunction.apply(key));
        }
        return ImmutableMap.copyOf(builder);
    }


    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    public static <T extends Object> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    @SuppressWarnings("unchecked")
    static <V> Function<Map.Entry<?, V>, V> valueFunction() {
        return (Function) EntryFunction.VALUE;
    }


}
