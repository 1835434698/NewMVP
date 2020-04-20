package com.tangzy.tzymvp.kotlin;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

@FunctionalInterface
public interface FieldFilter {

    boolean accept(@NonNull Field field);
}

