package com.tangzy.tzymvp.kotlin;

import androidx.annotation.IntDef;
import androidx.annotation.Keep;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Keep
public interface Env {

    /** 线上环境 */
    @Keep
    @EnvDef
    int ENV_ONLINE = 0;
    /** 线下环境 */
    @Keep
    @EnvDef
    int ENV_OFFLINE = 1;

    @Retention(SOURCE)
    @IntDef({ENV_ONLINE, ENV_OFFLINE})
    @interface Def {}

}
