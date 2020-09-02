package com.google.common.base;


import java.io.Serializable;
import java.util.Set;

public abstract class Optional<T> implements Serializable {


    public static <T> Optional<T> absent() {
        return Absent.withType();
    }

    public static <T> Optional<T> fromNullable(T nullableReference) {
        return (nullableReference == null) ? Optional.<T>absent() : new Present<T>(nullableReference);
    }

    Optional() {}
    public abstract T get();
    public abstract T or(T defaultValue);
    public abstract <V> Optional<V> transform(Function<? super T, V> function);

    public abstract boolean isPresent();
    public abstract Optional<T> or(Optional<? extends T> secondChoice);
    public abstract T orNull();
    public abstract T or(Supplier<? extends T> supplier);
    public abstract String toString();
    public abstract int hashCode();
    public abstract boolean equals(Object object);
}
