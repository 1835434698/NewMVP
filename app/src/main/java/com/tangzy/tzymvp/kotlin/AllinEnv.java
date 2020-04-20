package com.tangzy.tzymvp.kotlin;


import androidx.annotation.IntDef;
import androidx.annotation.Keep;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public interface AllinEnv extends Env{

    /**
     * 线上测试环境
     */
    @Keep
    @EnvDef
    int ENV_DETEST = 2;

    /**
     * 预上线环境
     */
    @Keep
    @EnvDef
    int ENV_PRE_ONLINE = 3;

    @Retention(SOURCE)
    @IntDef({ENV_ONLINE, ENV_OFFLINE, ENV_DETEST, ENV_PRE_ONLINE})
    @interface Def {
    }
}