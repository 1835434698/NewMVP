package com.google.common.collect;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Collections2 {

    //todo 替换名字与Function
    public static <F, T> Collection<T> transform(
            Collection<F> fromCollection, Function<? super F, T> function) {

        if (fromCollection == null || function == null){
            return null;
        }
        List list = new ArrayList();
        try {
            Iterator<F> iterator = fromCollection.iterator();
            while (iterator.hasNext()) {
                F next = iterator.next();
                if (function != null) {
                    list.add(function.apply(next));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public static <E> Collection<E> filter(Collection<E> unfiltered, Predicate<? super E> predicate) {

        if (unfiltered == null || predicate == null){
            return null;
        }
        List list = new ArrayList();
        if (predicate == Predicates.notNull()){
            try {
                Iterator<E> iterator = unfiltered.iterator();
                while (iterator.hasNext()) {
                    E next = iterator.next();
                    if (next != null) {
                        list.add(next);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (predicate == Predicates.isNull()){
            try {
                Iterator<E> iterator = unfiltered.iterator();
                while (iterator.hasNext()) {
                    E next = iterator.next();
                    if (next == null) {
                        list.add(next);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (predicate instanceof Predicates.IsEqualToPredicate){
            try {
                Iterator<E> iterator = unfiltered.iterator();
                while (iterator.hasNext()) {
                    E next = iterator.next();
                    if (predicate.hashCode()==next.hashCode()) {
                        list.add(next);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (predicate instanceof Predicates.InstanceOfPredicate){
            try {
                Iterator<E> iterator = unfiltered.iterator();
                while (iterator.hasNext()) {
                    E next = iterator.next();
                    if (next != null && next.getClass().hashCode() == predicate.hashCode()) {
                        list.add(next);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                Iterator<E> iterator = unfiltered.iterator();
                while (iterator.hasNext()) {
                    E next = iterator.next();
                    if (next != null && predicate.apply(next)) {
                        list.add(next);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return list;
    }

    static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection<T>) iterable;
    }



}