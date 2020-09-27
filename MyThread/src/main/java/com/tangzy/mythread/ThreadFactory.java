//package com.tangzy.mythread;
//
//import android.os.Looper;
//import android.util.Log;
//
//import androidx.lifecycle.LifecycleOwner;
//
//import com.uber.autodispose.AutoDispose;
//import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
//
//import java.util.concurrent.TimeUnit;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
//
//public enum ThreadFactory {
//    /**
//     * 单例
//     */
//    INSTANCE;
//    private static final String TAG = "ThreadMainActivity";
////    不使用subscribe
//    /**
//     * 工作线程执行
//     * 自动维护生命周期，建议主线称使用
//     * @param runWork
//     */
//    public void postWorker(final LifecycleOwner owner, final TaskRunnable runWork){
//        if (Looper.getMainLooper() == Looper.myLooper()){
//            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> emitter){
//                    try {
//                        Log.d(TAG, "1 ; ThreadName = "+Thread.currentThread().getName());
//                        runWork.run();
//                    }catch (Exception e){
//                        Log.d(TAG, "e12 = "+e.getMessage());
//                    }
//                }
//            }).subscribeOn(Schedulers.from(ThreadPool.getExecutor()))
//                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//                    .subscribe();
//        }else {
//            Observable.just(1)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "2 ; ThreadName = "+Thread.currentThread().getName());
//                            postWorker(owner, runWork);
//                        }
//                    });
//        }
//    }
//    /**
//     * UI线程执行
//     * 自动维护生命周期，建议主线称使用
//     * @param runUi
//     */
//    public void postUi(final LifecycleOwner owner, final TaskRunnable runUi){
//        if (Looper.getMainLooper() == Looper.myLooper()){
//            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> emitter){
//                    try {
//                        Log.d(TAG, "1 ; ThreadName = "+Thread.currentThread().getName());
//                        runUi.run();
//                    }catch (Exception e){
//                        Log.d(TAG, "e12 = "+e.getMessage());
//                    }
//                }
//            }).subscribeOn(AndroidSchedulers.mainThread())
//                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//                    .subscribe();
//        }else {
//            Observable.just(1)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "2 ; ThreadName = "+Thread.currentThread().getName());
//                            postUi(owner, runUi);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * single线程执行。
//     * 自动维护生命周期，建议主线称使用
//     * @param  runSingle
//     */
//    public void postSingle(final LifecycleOwner owner, final TaskRunnable runSingle){
//        if (Looper.getMainLooper() == Looper.myLooper()){
//            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> emitter){
//                    try {
//                        Log.d(TAG, "5 ; ThreadName = "+Thread.currentThread().getName());
//                        runSingle.run();
//                    }catch (Exception e){
//                        Log.d(TAG, "e12 = "+e.getMessage());
//                    }
//                }
//            }).subscribeOn(Schedulers.single())
//                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//                    .subscribe();
//        }else {
//            Observable.just(1)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "6 ; ThreadName = "+Thread.currentThread().getName());
//                            postSingle(owner, runSingle);
//                        }
//                    });
//        }
//    }
//
//
//    /**
//     * 工作线程执行，UI线程执行。
//     * runUi不会绑定生命周期不要执行耗时操作。
//     * 自动维护runwork生命周期，建议主线称使用
//     * @param runWork, runUi
//     */
//    public void postWorkerAndUi(final LifecycleOwner owner, final TaskRunnable runWork, final TaskRunnable runUi){
//        if (Looper.getMainLooper() == Looper.myLooper()){
//            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> emitter){
//                    try {
//                        Log.d(TAG, "1 ; ThreadName = "+Thread.currentThread().getName());
//                        runWork.run();
//                        emitter.onNext("");
//                    }catch (Exception e){
//                        Log.d(TAG, "e12 = "+e.getMessage());
//                    }
//                }
//            }).subscribeOn(Schedulers.from(ThreadPool.getExecutor())).observeOn(AndroidSchedulers.mainThread())
//                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//                    .subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(Object integer) throws Exception {
//                            Log.d(TAG, "27 ; ThreadName = "+Thread.currentThread().getName());
//                            runUi.run();
//                        }
//                    });
//        }else {
//            Observable.just(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "2 ; ThreadName = "+Thread.currentThread().getName());
//                            postWorkerAndUi(owner, runWork, runUi);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * 工作线程执行，UI线程执行。
//     * runUi不会绑定生命周期不要执行耗时操作。
//     * 自动维护生命周期，建议主线称使用
//     * @param runWork, runUi
//     */
//    public void postUiAndWorker(final LifecycleOwner owner, final TaskRunnable runUi, final TaskRunnable runWork){
//        if (Looper.getMainLooper() == Looper.myLooper()){
//            try {
//                runUi.run();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> emitter){
//                    try {
//                        Log.d(TAG, "28 ; ThreadName = "+Thread.currentThread().getName());
//                        runWork.run();
//                    }catch (Exception e){
//                        Log.d(TAG, "e12 = "+e.getMessage());
//                    }
//                }
//            }).subscribeOn(Schedulers.from(ThreadPool.getExecutor()))
//                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//                    .subscribe();
//        }else {
//            Observable.just(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "4 ; ThreadName = "+Thread.currentThread().getName());
//                            postUiAndWorker(owner, runUi, runWork);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * 工作线程执行，single线程执行。
//     * 自动维护生命周期，建议主线称使用
//     * @param runWork, runSingle
//     */
//    public void postWorkerAndSingle(final LifecycleOwner owner, final TaskRunnable runWork, final TaskRunnable runSingle){
//        if (Looper.getMainLooper() == Looper.myLooper()){
//            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> emitter){
//                    try {
//                        Log.d(TAG, "5 ; ThreadName = "+Thread.currentThread().getName());
//                        runWork.run();
//                        postSingle(owner, runSingle);
//                    }catch (Exception e){
//                        Log.d(TAG, "e12 = "+e.getMessage());
//                    }
//                }
//            }).subscribeOn(Schedulers.from(ThreadPool.getExecutor()))
//                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//                    .subscribe();
//        }else {
//            Observable.just(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "6 ; ThreadName = "+Thread.currentThread().getName());
//                            postWorkerAndSingle(owner, runWork, runSingle);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * 工作线程执行，single线程执行。
//     * 自动维护生命周期，建议主线称使用
//     * @param runWork, runUi
//     */
//    public void postSingleAndWorker(final LifecycleOwner owner, final TaskRunnable runSingle, final TaskRunnable runWork){
//        if (Looper.getMainLooper() == Looper.myLooper()){
//            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> emitter){
//                    try {
//                        Log.d(TAG, "7 ; ThreadName = "+Thread.currentThread().getName());
//                        runSingle.run();
//                        postWorker(owner, runWork);
//                    }catch (Exception e){
//                        Log.d(TAG, "e12 = "+e.getMessage());
//                    }
//                }
//            }).subscribeOn(Schedulers.single())
//                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//                    .subscribe();
//        }else {
//            Observable.just(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "8 ; ThreadName = "+Thread.currentThread().getName());
//                            postSingleAndWorker(owner, runSingle, runWork);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * single线程执行，UI线程执行。
//     * runUi不会绑定生命周期不要执行耗时操作。
//     * 自动维护生命周期，建议主线称使用
//     * @param runSingle, runUi
//     */
//    public void postSingleAndUi(final LifecycleOwner owner, final TaskRunnable runSingle, final TaskRunnable runUi){
//        if (Looper.getMainLooper() == Looper.myLooper()){
//            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> emitter){
//                    try {
//                        Log.d(TAG, "9 ; ThreadName = "+Thread.currentThread().getName());
//                        runSingle.run();
//                        emitter.onNext("");
//                    }catch (Exception e){
//                        Log.d(TAG, "e12 = "+e.getMessage());
//                    }
//                }
//            }).subscribeOn(Schedulers.single())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//                    .subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(Object integer) throws Exception {
//                            Log.d(TAG, "31 ; ThreadName = "+Thread.currentThread().getName());
//                            runUi.run();
//                        }
//                    });
//        }else {
//            Observable.just(1)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "10 ; ThreadName = "+Thread.currentThread().getName());
//                            postSingleAndUi(owner, runSingle, runUi);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * UI线程执行, single线程执行。
//     * runUi不会绑定生命周期不要执行耗时操作。
//     * 自动维护生命周期，建议主线称使用
//     * @param runSingle, runUi
//     */
//    public void postUiAndSingle(final LifecycleOwner owner, final TaskRunnable runUi, final TaskRunnable runSingle){
//        if (Looper.getMainLooper() == Looper.myLooper()){
//            try {
//                runUi.run();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> emitter){
//                    try {
//                        Log.d(TAG, "32 ; ThreadName = "+Thread.currentThread().getName());
//                        runSingle.run();
//                    }catch (Exception e){
//                        Log.d(TAG, "e12 = "+e.getMessage());
//                    }
//                }
//            }).subscribeOn(Schedulers.single())
//                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//                    .subscribe();
//        }else {
//            Observable.just(1)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "12 ; ThreadName = "+Thread.currentThread().getName());
//                            postUiAndSingle(owner, runUi, runSingle);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * UI线程中执行
//     * runUi不会绑定生命周期不要执行耗时操作。
//     * 自动维护生命周期，建议主线称使用
//     * @param runUi
//     */
//    public void postDelayedUI(final LifecycleOwner owner, final TaskRunnable runUi, final long delayMillis){
//        if (Looper.getMainLooper() == Looper.myLooper()) {
//            Observable.timer(delayMillis, TimeUnit.MILLISECONDS)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .as(AutoDispose.<Long>autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//                    .subscribe(new Consumer<Long>() {
//                        @Override
//                        public void accept(Long aLong) throws Exception {
//                            runUi.run();
//                        }
//                    });
//        }else {
//            Observable.just(1)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "12 ; ThreadName = " + Thread.currentThread().getName());
//                            postDelayedUI(owner, runUi, delayMillis);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * Worker线程中执行
//     * 自动维护生命周期，建议主线称使用
//     * @param runWork
//     */
//    public void postDelayedWorker(final LifecycleOwner owner, final TaskRunnable runWork, final long delayMillis){
//        if (Looper.getMainLooper() == Looper.myLooper()) {
//            Observable.timer(delayMillis, TimeUnit.MICROSECONDS)
//                    .observeOn(Schedulers.from(ThreadPool.getExecutor()))
//                    .as(AutoDispose.<Long>autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//                    .subscribe(new Consumer<Long>() {
//                        @Override
//                        public void accept(Long aLong) throws Exception {
//                            runWork.run();
//                        }
//                    });
//        }else {
//            Observable.just(1)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "12 ; ThreadName = " + Thread.currentThread().getName());
//                            postDelayedWorker(owner, runWork, delayMillis);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * Single线程中执行
//     * 自动维护生命周期，建议主线称使用
//     * @param runSingle
//     */
//    public void postDelayedSingle(final LifecycleOwner owner, final TaskRunnable runSingle, final long delayMillis){
//        if (Looper.getMainLooper() == Looper.myLooper()) {
//            Observable.timer(delayMillis, TimeUnit.MICROSECONDS)
//                    .observeOn(Schedulers.single())
//                    .as(AutoDispose.<Long>autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//                    .subscribe(new Consumer<Long>() {
//                        @Override
//                        public void accept(Long aLong) throws Exception {
//                            runSingle.run();
//                        }
//                    });
//        }else {
//            Observable.just(1)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "12 ; ThreadName = " + Thread.currentThread().getName());
//                            postDelayedSingle(owner, runSingle, delayMillis);
//                        }
//                    });
//        }
//    }
//
//
//
//    /**
//     * 工作线程执行
//     * 注意不要在single线程使用。
//     * @param runWork
//     */
//    @SuppressWarnings("不能绑定生命周期，不推荐使用")
//    public void postWorker(final TaskRunnable runWork){
//        if (Looper.getMainLooper() != Looper.myLooper()){
//            try {
//                Log.d(TAG, "1 ; ThreadName = "+Thread.currentThread().getName());
//                runWork.run();
//            }catch (Exception e){
//                Log.d(TAG, "e12 = "+e.getMessage());
//            }
//        }else {
//            Observable.just(1)
//                    .observeOn(Schedulers.from(ThreadPool.getExecutor()))
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "2 ; ThreadName = "+Thread.currentThread().getName());
//                            postWorker(runWork);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * UI线程执行
//     * @param runUi
//     */
//    @SuppressWarnings("不能绑定生命周期，不推荐使用")
//    public void postUi(final TaskRunnable runUi){
//        if (Looper.getMainLooper() == Looper.myLooper()){
//            try {
//                Log.d(TAG, "1 ; ThreadName = "+Thread.currentThread().getName());
//                runUi.run();
//            }catch (Exception e){
//                Log.d(TAG, "e12 = "+e.getMessage());
//            }
//        }else {
//            Observable.just(1)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "2 ; ThreadName = "+Thread.currentThread().getName());
//                            postUi(runUi);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * single线程执行。
//     * @param  runSingle
//     */
//    @SuppressWarnings("不能绑定生命周期，不推荐使用")
//    public void postSingle(final TaskRunnable runSingle){
//        Observable.just(1)
//                .observeOn(Schedulers.single())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "6 ; ThreadName = "+Thread.currentThread().getName());
//                        runSingle.run();
//                    }
//                });
//    }
//
//    /**
//     * 工作线程执行，UI线程执行。
//     * 注意不要在single线程使用。
//     * 不推荐使用，因为需要自己需要自己维护生命周期。
//     * @param runWork, runUi
//     */
//    @SuppressWarnings("不能绑定生命周期，不推荐使用")
//    public void postWorkerAndUi(final TaskRunnable runWork, final TaskRunnable runUi){
//        if (Looper.getMainLooper() != Looper.myLooper()){
//            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> emitter){
//                    try {
//                        Log.d(TAG, "13 ; ThreadName = "+Thread.currentThread().getName());
//                        runWork.run();
//                        emitter.onNext("");
//                    }catch (Exception e){
//                        Log.d(TAG, "e12 = "+e.getMessage());
//                    }
//                }
//            }).observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(Object integer) throws Exception {
//                            Log.d(TAG, "14 ; ThreadName = "+Thread.currentThread().getName());
//                            runUi.run();
//                        }
//                    });
//        }else {
//            Observable.just(1)
//                    .observeOn(Schedulers.from(ThreadPool.getExecutor()))
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "15 ; ThreadName = "+Thread.currentThread().getName());
//                            postWorkerAndUi(runWork, runUi);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * 工作线程执行，UI线程执行。
//     * 不推荐使用，因为需要自己需要自己维护生命周期。
//     * @param runWork, runUi
//     */
//    @SuppressWarnings("不能绑定生命周期，不推荐使用")
//    public void postUiAndWorker(final TaskRunnable runUi, final TaskRunnable runWork){
//        if (Looper.getMainLooper() == Looper.myLooper()){
//            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> emitter){
//                    try {
//                        Log.d(TAG, "16 ; ThreadName = "+Thread.currentThread().getName());
//                        runUi.run();
//                        emitter.onNext("");
//                    }catch (Exception e){
//                        Log.d(TAG, "e12 = "+e.getMessage());
//                    }
//                }
//            }).observeOn(Schedulers.from(ThreadPool.getExecutor()))
//                    .subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(Object integer) throws Exception {
//                            Log.d(TAG, "17 ; ThreadName = "+Thread.currentThread().getName());
//                            runWork.run();
//                        }
//                    });
//        }else {
//            Observable.just(1)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "18 ; ThreadName = "+Thread.currentThread().getName());
//                            postUiAndWorker(runUi, runWork);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * 工作线程执行，single线程执行。
//     * 不推荐使用，因为需要自己需要自己维护生命周期。
//     * @param runWork, runUi
//     */
//    @SuppressWarnings("不能绑定生命周期，不推荐使用")
//    public void postWorkerAndSingle(final TaskRunnable runWork, final TaskRunnable runSingle){
//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> emitter){
//                try {
//                    Log.d(TAG, "19 ; ThreadName = "+Thread.currentThread().getName());
//                    runWork.run();
//                    emitter.onNext("");
//                }catch (Exception e){
//                    Log.d(TAG, "e12 = "+e.getMessage());
//                }
//            }
//        }).subscribeOn(Schedulers.from(ThreadPool.getExecutor()))
//                .observeOn(Schedulers.single())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object integer) throws Exception {
//                        Log.d(TAG, "20 ; ThreadName = "+Thread.currentThread().getName());
//                        runSingle.run();
//                    }
//                });
//    }
//
//    /**
//     * 工作线程执行，single线程执行。
//     * 不推荐使用，因为需要自己需要自己维护生命周期。
//     * @param runWork, runUi
//     */
//    @SuppressWarnings("不能绑定生命周期，不推荐使用")
//    public void postSingleAndWorker(final TaskRunnable runSingle, final TaskRunnable runWork){
//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> emitter){
//                try {
//                    Log.d(TAG, "21 ; ThreadName = "+Thread.currentThread().getName());
//                    runSingle.run();
//                    emitter.onNext("");
//                }catch (Exception e){
//                    Log.d(TAG, "e12 = "+e.getMessage());
//                }
//            }
//        }).subscribeOn(Schedulers.single())
//                .observeOn(Schedulers.from(ThreadPool.getExecutor()))
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object integer) throws Exception {
//                        Log.d(TAG, "22 ; ThreadName = "+Thread.currentThread().getName());
//                        runWork.run();
//                    }
//                });
//    }
//
//    /**
//     * single线程执行，UI线程执行。
//     * 不推荐使用，因为需要自己需要自己维护生命周期。
//     * @param runSingle, runUi
//     */
//    @SuppressWarnings("不能绑定生命周期，不推荐使用")
//    public void postSingleAndUi(final TaskRunnable runSingle, final TaskRunnable runUi){
//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> emitter){
//                try {
//                    Log.d(TAG, "23 ; ThreadName = "+Thread.currentThread().getName());
//                    runSingle.run();
//                    emitter.onNext("");
//                }catch (Exception e){
//                    Log.d(TAG, "e12 = "+e.getMessage());
//                }
//            }
//        }).subscribeOn(Schedulers.single())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object integer) throws Exception {
//                        Log.d(TAG, "24 ; ThreadName = "+Thread.currentThread().getName());
//                        runUi.run();
//                    }
//                });
//    }
//
//    /**
//     * UI线程执行, single线程执行。
//     * 不推荐使用，因为需要自己需要自己维护生命周期。
//     * @param runSingle, runUi
//     */
//    @SuppressWarnings("不能绑定生命周期，不推荐使用")
//    public void postUiAndSingle(final TaskRunnable runUi, final TaskRunnable runSingle){
//        if (Looper.getMainLooper() == Looper.myLooper()){
//            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> emitter){
//                    try {
//                        Log.d(TAG, "25 ; ThreadName = "+Thread.currentThread().getName());
//                        runUi.run();
//                        emitter.onNext("");
//                    }catch (Exception e){
//                        Log.d(TAG, "e12 = "+e.getMessage());
//                    }
//                }
//            }).observeOn(Schedulers.single())
//                    .subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(Object integer) throws Exception {
//                            runSingle.run();
//                        }
//                    });
//        }else {
//            Observable.just(1)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Integer>() {
//                        @Override
//                        public void accept(Integer integer) throws Exception {
//                            Log.d(TAG, "26 ; ThreadName = "+Thread.currentThread().getName());
//                            postUiAndSingle(runUi, runSingle);
//                        }
//                    });
//        }
//    }
//
//    /**
//     * UI线程中执行
//     * runUi期不要执行耗时操作。
//     * 不推荐使用，因为需要自己需要自己维护生命周期。
//     * @param runUi
//     */
//    @SuppressWarnings("不能绑定生命周期，不推荐使用")
//    public void postDelayedUI(final TaskRunnable runUi, final long delayMillis){
//        Observable.timer(delayMillis, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        runUi.run();
//                    }
//                });
//    }
//
//    /**
//     * Worker线程中执行
//     * 不推荐使用，因为需要自己需要自己维护生命周期。
//     * @param runWork
//     */
//    @SuppressWarnings("不能绑定生命周期，不推荐使用")
//    public void postDelayedWorker(final TaskRunnable runWork, final long delayMillis){
//        Observable.timer(delayMillis, TimeUnit.MILLISECONDS)
//                .observeOn(Schedulers.from(ThreadPool.getExecutor()))
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        runWork.run();
//                    }
//                });
//    }
//
//    /**
//     * Single线程中执行
//     * 不推荐使用，因为需要自己需要自己维护生命周期。
//     * @param runSingle
//     */
//    @SuppressWarnings("不能绑定生命周期，不推荐使用")
//    public void postDelayedSingle(final TaskRunnable runSingle, final long delayMillis){
//        Observable.timer(delayMillis, TimeUnit.MICROSECONDS)
//                .observeOn(Schedulers.single())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        runSingle.run();
//                    }
//                });
//    }
//}
