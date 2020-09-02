package com.google.common.base;

import android.annotation.SuppressLint;

import java.util.Set;

import static com.google.common.base.Predicates.checkNotNull;


public class Present<T> extends Optional<T> {

    private final T reference;

    Present(T reference) {
        this.reference = reference;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public T get() {
        return reference;
    }

    
    @Override
    public T or(T defaultValue) {
        checkNotNull(defaultValue, "use Optional.orNull() instead of Optional.or(null)");
        return reference;
    }

    
    @Override
    public Optional<T> or(Optional<? extends T> secondChoice) {
        checkNotNull(secondChoice);
        return this;
    }

    
    @Override
    public T or(Supplier<? extends T> supplier) {
        checkNotNull(supplier);
        return reference;
    }

    @Override
    public T orNull() {
        return reference;
    }
    
    
    @Override
    public <V> Optional<V> transform(Function<? super T, V> function) {
        return new Present<V>(
                checkNotNull(
                        function.apply(reference),
                        "the Function passed to Optional.transform() must not return null."));
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Present) {
            Present<?> other = (Present<?>) object;
            return reference.equals(other.reference);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0x598df91c + reference.hashCode();
    }

    @Override
    public String toString() {
        return "Optional.of(" + reference + ")";
    }

    private static final long serialVersionUID = 0;
}
