package com.tangzy.tzymvp.kotlin;

import androidx.annotation.NonNull;

@FunctionalInterface
public interface BaseUrlGetter {
    String get(@NonNull String baseUrlType, int env);
}
