package com.google.common.base;


public interface Supplier<T> {
    /**
     * Retrieves an instance of the appropriate type. The returned object may or may not be a new
     * instance, depending on the implementation.
     *
     * @return an instance of the appropriate type
     */
    T get();
}
