package com.google.common.collect;

import java.util.Collection;
import java.util.Iterator;

import static com.google.common.base.Predicates.checkNotNull;

public class Iterators {
    public static <T> boolean addAll(Collection<T> addTo, Iterator<? extends T> iterator) {
        checkNotNull(addTo);
        checkNotNull(iterator);
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= addTo.add(iterator.next());
        }
        return wasModified;
    }
}
