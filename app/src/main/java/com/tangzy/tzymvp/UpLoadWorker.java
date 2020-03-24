package com.tangzy.tzymvp;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.tangzy.tzymvp.util.Logger;

/**
 * class
 *
 * @author Administrator
 * @date 2020/3/24
 */
public class UpLoadWorker extends Worker {
    private static final String TAG = "UpLoadWorker";

    public UpLoadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        workerParams.getInputData();//输入参数。
    }

    @NonNull
    @Override
    public Result doWork() {
        Logger.d(TAG, "doWork name = "+Thread.currentThread().getName());

        return Result.success();
    }
}
