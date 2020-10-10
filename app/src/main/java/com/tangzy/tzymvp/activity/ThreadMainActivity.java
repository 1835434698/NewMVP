package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.allin.threadfactory.TaskRunnable;
import com.allin.threadfactory.ThreadFactory;
import com.allin.threadfactory.ThreadPool;
import com.tangzy.tzymvp.R;

import io.reactivex.ObservableOnSubscribe;

public class ThreadMainActivity extends AppCompatActivity {

    private static final String TAG = "ThreadMainActivity1";

    //    private ThreadFactory threadFactory;
    private ObservableOnSubscribe<Object> objectObservableOnSubscribe;
    int i = 0;
    int j = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_thread_main);
//        threadFactory = new ThreadFactory();
        runTask();
    }

    private void runTask() {
//        objectObservableOnSubscribe = new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> emitter){
//                try {
//                    Log.d(TAG, "work run name : "+Thread.currentThread().getName());
//                    workTask();
//                    emitter.onNext("");
//                }catch (Exception e){
//                    Log.d("ThreadMainActivity", "e12 = "+e.getMessage());
//                }
//            }
//        };
//        Consumer<Object> consumer = new Consumer<Object>() {
//            @Override
//            public void accept(Object integer) throws Exception {
//                Log.d(TAG, "UI run i = " + i + " j = " + j + " name : " + Thread.currentThread().getName());
//                uiTask();
//            }
//        };
//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> emitter){
//                try {
//                    Log.d(TAG, "work run name : "+Thread.currentThread().getName());
//                    workTask();
//                    emitter.onNext("");
//                }catch (Exception e){
//                    Log.d("ThreadMainActivity", "e12 = "+e.getMessage());
//                }
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object integer) throws Exception {
//                        Log.d(TAG, "UI run i = " + i + " j = " + j + " name : " + Thread.currentThread().getName());
//                        uiTask();
//                    }
//                });

        new Thread(new Runnable() {
            @Override
            public void run() {

//        ThreadFactory.INSTANCE.postWorker(ThreadMainActivity.this, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work1 run name : " + Thread.currentThread().getName());
//                workSing();
//            }
//        });
//        ThreadFactory.INSTANCE.postUi(ThreadMainActivity.this, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work2 run name : " + Thread.currentThread().getName());
//                uiTask();
//            }
//        });
//        ThreadFactory.INSTANCE.postSingle(ThreadMainActivity.this, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work3 run name : " + Thread.currentThread().getName());
//                workSing();
//            }
//        });
//
//        ThreadFactory.INSTANCE.postWorkerAndUi(ThreadMainActivity.this, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work4 run name : " + Thread.currentThread().getName());
//                workSing();
//            }
//        }, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "UI4 run i = " + i + " j = " + j + " name : " + Thread.currentThread().getName());
//                uiTask();
//
//            }
//        });
//        ThreadFactory.INSTANCE.postUiAndWorker(ThreadMainActivity.this, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work5 run name : " + Thread.currentThread().getName());
//                uiTask();
//            }
//        }, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "UI5 run i = " + i + " j = " + j + " name : " + Thread.currentThread().getName());
//                workSing();
//
//            }
//        });
//        ThreadFactory.INSTANCE.postWorkerAndSingle(ThreadMainActivity.this, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work6 run name : " + Thread.currentThread().getName());
//                workSing();
//            }
//        }, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "UI6 run i = " + i + " j = " + j + " name : " + Thread.currentThread().getName());
//                workSing();
//
//            }
//        });
//        ThreadFactory.INSTANCE.postSingleAndWorker(ThreadMainActivity.this, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work7 run name : " + Thread.currentThread().getName());
//                workSing();
//                finish();
//            }
//        }, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "UI7 run i = " + i + " j = " + j + " name : " + Thread.currentThread().getName());
//                workSing();
//
//            }
//        });
//        ThreadFactory.INSTANCE.postSingleAndUi(ThreadMainActivity.this, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work8 run name : " + Thread.currentThread().getName());
//                workSing();
//            }
//        }, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "UI8 run i = " + i + " j = " + j + " name : " + Thread.currentThread().getName());
//                uiTask();
//
//            }
//        });
//        ThreadFactory.INSTANCE.postUiAndSingle(ThreadMainActivity.this, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work9 run name : " + Thread.currentThread().getName());
//                uiTask();
//            }
//        }, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "UI9 run i = " + i + " j = " + j + " name : " + Thread.currentThread().getName());
//                workSing();
//
//            }
//        });
//
//
//        ThreadFactory.INSTANCE.postDelayedUI(ThreadMainActivity.this, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work10 run name : " + Thread.currentThread().getName());
//                workSing();
//            }
//        }, 3000);
//
//        ThreadFactory.INSTANCE.postDelayedWorker(ThreadMainActivity.this, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work11 run name : " + Thread.currentThread().getName());
//                uiTask();
//            }
//        }, 3000);
//
//        ThreadFactory.INSTANCE.postDelayedSingle(ThreadMainActivity.this, new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work12 run name : " + Thread.currentThread().getName());
//                workSing();
//            }
//        }, 3000);
//
//
//        ThreadFactory.INSTANCE.postDelayedUI(new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work13 run name : " + Thread.currentThread().getName());
//                workSing();
//            }
//        }, 3000);
//
//        ThreadFactory.INSTANCE.postDelayedWorker(new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work14 run name : " + Thread.currentThread().getName());
//                uiTask();
//            }
//        }, 3000);
//
//        ThreadFactory.INSTANCE.postDelayedSingle(new TaskRunnable() {
//            @Override
//            public void run() throws Exception {
//                Log.d(TAG, "work15 run name : " + Thread.currentThread().getName());
//                workSing();
//            }
//        }, 3000);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "work16 run name : " + Thread.currentThread().getName());
                try {
                    workSing();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e(TAG, "work16 error = " + e.getMessage());
                }
                Log.d(TAG, "work16 over");

            }
        };
        mHandler.postDelayed(runnable, 3000);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "work16 removeCallbacks : " + Thread.currentThread().getName());
                mHandler.removeCallbacks(runnable);
                ThreadPool.getThreadPool().execute(runnable);
//
            }
        }).start();
    }

    private Handler mHandler = new Handler();

    private void workSing() throws InterruptedException {
        int i = 0;
        Log.d(TAG, "work run i = " + i);
        while (i < 50) {
            Log.d(TAG, "i = "+i);
            i++;
            Thread.sleep(200);
        }

    }

    private void uiTask() {
        int i = 0;
        Log.d(TAG, "work run i = " + i);
        while (i < 50) {
//            Log.d(TAG, "i++");
            i++;
//            Thread.sleep(100);
        }
    }

    private void workTask(int j) throws InterruptedException {
        int i = 0;
        Log.d(TAG, "work run i = " + i + " j = " + j);
        if (j >= 10) {
            throw new InterruptedException();
        }
        while (i < 10) {
//            Log.d(TAG, "i++");
            i++;
            Thread.sleep(100);
        }
        runTask();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
//        objectObservableOnSubscribe = null;
    }
}
