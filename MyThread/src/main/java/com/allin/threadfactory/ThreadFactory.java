package com.allin.threadfactory;

import android.os.Looper;
import androidx.lifecycle.LifecycleOwner;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public enum ThreadFactory {
    /**
     * 单例
     */
    INSTANCE;

    /**
     * 工作线程执行
     * 自动维护生命周期，建议主线称使用
     * @param runWork
     */
    public void postWorker(final LifecycleOwner owner, final TaskRunnable runWork){
        if (Looper.getMainLooper() == Looper.myLooper()){
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter){
                    try {
                        runWork.run();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.from(ThreadPool.getExecutor()))
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                    .subscribe();
        }else {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postWorker(owner, runWork);
                        }
                    });
        }
    }
    /**
     * UI线程执行
     * 自动维护生命周期，建议主线称使用
     * @param runUi
     */
    public void postUi(final LifecycleOwner owner, final TaskRunnable runUi){
        if (Looper.getMainLooper() == Looper.myLooper()){
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter){
                    try {
                        runUi.run();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                    .subscribe();
        }else {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postUi(owner, runUi);
                        }
                    });
        }
    }

    /**
     * single线程执行。
     * 自动维护生命周期，建议主线称使用
     * @param  runSingle
     */
    public void postSingle(final LifecycleOwner owner, final TaskRunnable runSingle){
        if (Looper.getMainLooper() == Looper.myLooper()){
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter){
                    try {
                        runSingle.run();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.single())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                    .subscribe();
        }else {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postSingle(owner, runSingle);
                        }
                    });
        }
    }


    /**
     * 工作线程执行，UI线程执行。
     * runUi不会绑定生命周期不要执行耗时操作。
     * 自动维护runwork生命周期，建议主线称使用
     * @param runWork, runUi
     */
    public void postWorkerAndUi(final LifecycleOwner owner, final TaskRunnable runWork, final TaskRunnable runUi){
        if (Looper.getMainLooper() == Looper.myLooper()){
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter){
                    try {
                        runWork.run();
                        emitter.onNext("");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.from(ThreadPool.getExecutor())).observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object integer) throws Exception {
                            runUi.run();
                        }
                    });
        }else {
            Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postWorkerAndUi(owner, runWork, runUi);
                        }
                    });
        }
    }

    /**
     * 工作线程执行，UI线程执行。
     * runUi不会绑定生命周期不要执行耗时操作。
     * 自动维护生命周期，建议主线称使用
     * @param runWork, runUi
     */
    public void postUiAndWorker(final LifecycleOwner owner, final TaskRunnable runUi, final TaskRunnable runWork){
        if (Looper.getMainLooper() == Looper.myLooper()){
            try {
                runUi.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter){
                    try {
                        runWork.run();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.from(ThreadPool.getExecutor()))
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                    .subscribe();
        }else {
            Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postUiAndWorker(owner, runUi, runWork);
                        }
                    });
        }
    }

    /**
     * 工作线程执行，single线程执行。
     * 自动维护生命周期，建议主线称使用
     * @param runWork, runSingle
     */
    public void postWorkerAndSingle(final LifecycleOwner owner, final TaskRunnable runWork, final TaskRunnable runSingle){
        if (Looper.getMainLooper() == Looper.myLooper()){
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter){
                    try {
                        runWork.run();
                        postSingle(owner, runSingle);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.from(ThreadPool.getExecutor()))
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                    .subscribe();
        }else {
            Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postWorkerAndSingle(owner, runWork, runSingle);
                        }
                    });
        }
    }

    /**
     * 工作线程执行，single线程执行。
     * 自动维护生命周期，建议主线称使用
     * @param runWork, runUi
     */
    public void postSingleAndWorker(final LifecycleOwner owner, final TaskRunnable runSingle, final TaskRunnable runWork){
        if (Looper.getMainLooper() == Looper.myLooper()){
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter){
                    try {
                        runSingle.run();
                        postWorker(owner, runWork);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.single())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                    .subscribe();
        }else {
            Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postSingleAndWorker(owner, runSingle, runWork);
                        }
                    });
        }
    }

    /**
     * single线程执行，UI线程执行。
     * runUi不会绑定生命周期不要执行耗时操作。
     * 自动维护生命周期，建议主线称使用
     * @param runSingle, runUi
     */
    public void postSingleAndUi(final LifecycleOwner owner, final TaskRunnable runSingle, final TaskRunnable runUi){
        if (Looper.getMainLooper() == Looper.myLooper()){
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter){
                    try {
                        runSingle.run();
                        emitter.onNext("");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object integer) throws Exception {
                            runUi.run();
                        }
                    });
        }else {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postSingleAndUi(owner, runSingle, runUi);
                        }
                    });
        }
    }

    /**
     * UI线程执行, single线程执行。
     * runUi不会绑定生命周期不要执行耗时操作。
     * 自动维护生命周期，建议主线称使用
     * @param runSingle, runUi
     */
    public void postUiAndSingle(final LifecycleOwner owner, final TaskRunnable runUi, final TaskRunnable runSingle){
        if (Looper.getMainLooper() == Looper.myLooper()){
            try {
                runUi.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter){
                    try {
                        runSingle.run();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.single())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                    .subscribe();
        }else {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postUiAndSingle(owner, runUi, runSingle);
                        }
                    });
        }
    }

    /**
     * UI线程中执行
     * runUi不会绑定生命周期不要执行耗时操作。
     * 自动维护生命周期，建议主线称使用
     * @param runUi
     */
    public void postDelayedUI(final LifecycleOwner owner, final TaskRunnable runUi, final long delayMillis){
        if (Looper.getMainLooper() == Looper.myLooper()) {
            Observable.timer(delayMillis, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.<Long>autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            runUi.run();
                        }
                    });
        }else {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postDelayedUI(owner, runUi, delayMillis);
                        }
                    });
        }
    }

    /**
     * Worker线程中执行
     * 自动维护生命周期，建议主线称使用
     * @param runWork
     */
    public void postDelayedWorker(final LifecycleOwner owner, final TaskRunnable runWork, final long delayMillis){
        if (Looper.getMainLooper() == Looper.myLooper()) {
            Observable.timer(delayMillis, TimeUnit.MICROSECONDS)
                    .observeOn(Schedulers.from(ThreadPool.getExecutor()))
                    .as(AutoDispose.<Long>autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            runWork.run();
                        }
                    });
        }else {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postDelayedWorker(owner, runWork, delayMillis);
                        }
                    });
        }
    }

    /**
     * Single线程中执行
     * 自动维护生命周期，建议主线称使用
     * @param runSingle
     */
    public void postDelayedSingle(final LifecycleOwner owner, final TaskRunnable runSingle, final long delayMillis){
        if (Looper.getMainLooper() == Looper.myLooper()) {
            Observable.timer(delayMillis, TimeUnit.MICROSECONDS)
                    .observeOn(Schedulers.single())
                    .as(AutoDispose.<Long>autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            runSingle.run();
                        }
                    });
        }else {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postDelayedSingle(owner, runSingle, delayMillis);
                        }
                    });
        }
    }



    /**
     * 工作线程执行
     * 注意不要在single线程使用。
     * @param runWork
     */
    @SuppressWarnings("不能绑定生命周期，不推荐使用")
    public void postWorker(final TaskRunnable runWork){
        if (Looper.getMainLooper() != Looper.myLooper()){
            try {
                runWork.run();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            Observable.just(1)
                    .observeOn(Schedulers.from(ThreadPool.getExecutor()))
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postWorker(runWork);
                        }
                    });
        }
    }

    /**
     * UI线程执行
     * @param runUi
     */
    @SuppressWarnings("不能绑定生命周期，不推荐使用")
    public void postUi(final TaskRunnable runUi){
        if (Looper.getMainLooper() == Looper.myLooper()){
            try {
                runUi.run();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postUi(runUi);
                        }
                    });
        }
    }

    /**
     * single线程执行。
     * @param  runSingle
     */
    @SuppressWarnings("不能绑定生命周期，不推荐使用")
    public void postSingle(final TaskRunnable runSingle){
        Observable.just(1)
                .observeOn(Schedulers.single())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        runSingle.run();
                    }
                });
    }

    /**
     * 工作线程执行，UI线程执行。
     * 注意不要在single线程使用。
     * 不推荐使用，因为需要自己需要自己维护生命周期。
     * @param runWork, runUi
     */
    @SuppressWarnings("不能绑定生命周期，不推荐使用")
    public void postWorkerAndUi(final TaskRunnable runWork, final TaskRunnable runUi){
        if (Looper.getMainLooper() != Looper.myLooper()){
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter){
                    try {
                        runWork.run();
                        emitter.onNext("");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object integer) throws Exception {
                            runUi.run();
                        }
                    });
        }else {
            Observable.just(1)
                    .observeOn(Schedulers.from(ThreadPool.getExecutor()))
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postWorkerAndUi(runWork, runUi);
                        }
                    });
        }
    }

    /**
     * 工作线程执行，UI线程执行。
     * 不推荐使用，因为需要自己需要自己维护生命周期。
     * @param runWork, runUi
     */
    @SuppressWarnings("不能绑定生命周期，不推荐使用")
    public void postUiAndWorker(final TaskRunnable runUi, final TaskRunnable runWork){
        if (Looper.getMainLooper() == Looper.myLooper()){
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter){
                    try {
                        runUi.run();
                        emitter.onNext("");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).observeOn(Schedulers.from(ThreadPool.getExecutor()))
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object integer) throws Exception {
                            runWork.run();
                        }
                    });
        }else {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postUiAndWorker(runUi, runWork);
                        }
                    });
        }
    }

    /**
     * 工作线程执行，single线程执行。
     * 不推荐使用，因为需要自己需要自己维护生命周期。
     * @param runWork, runUi
     */
    @SuppressWarnings("不能绑定生命周期，不推荐使用")
    public void postWorkerAndSingle(final TaskRunnable runWork, final TaskRunnable runSingle){
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter){
                try {
                    runWork.run();
                    emitter.onNext("");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.from(ThreadPool.getExecutor()))
                .observeOn(Schedulers.single())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object integer) throws Exception {
                        runSingle.run();
                    }
                });
    }

    /**
     * 工作线程执行，single线程执行。
     * 不推荐使用，因为需要自己需要自己维护生命周期。
     * @param runWork, runUi
     */
    @SuppressWarnings("不能绑定生命周期，不推荐使用")
    public void postSingleAndWorker(final TaskRunnable runSingle, final TaskRunnable runWork){
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter){
                try {
                    runSingle.run();
                    emitter.onNext("");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.single())
                .observeOn(Schedulers.from(ThreadPool.getExecutor()))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object integer) throws Exception {
                        runWork.run();
                    }
                });
    }

    /**
     * single线程执行，UI线程执行。
     * 不推荐使用，因为需要自己需要自己维护生命周期。
     * @param runSingle, runUi
     */
    @SuppressWarnings("不能绑定生命周期，不推荐使用")
    public void postSingleAndUi(final TaskRunnable runSingle, final TaskRunnable runUi){
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter){
                try {
                    runSingle.run();
                    emitter.onNext("");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object integer) throws Exception {
                        runUi.run();
                    }
                });
    }

    /**
     * UI线程执行, single线程执行。
     * 不推荐使用，因为需要自己需要自己维护生命周期。
     * @param runSingle, runUi
     */
    @SuppressWarnings("不能绑定生命周期，不推荐使用")
    public void postUiAndSingle(final TaskRunnable runUi, final TaskRunnable runSingle){
        if (Looper.getMainLooper() == Looper.myLooper()){
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter){
                    try {
                        runUi.run();
                        emitter.onNext("");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).observeOn(Schedulers.single())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object integer) throws Exception {
                            runSingle.run();
                        }
                    });
        }else {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            postUiAndSingle(runUi, runSingle);
                        }
                    });
        }
    }

    /**
     * UI线程中执行
     * runUi期不要执行耗时操作。
     * 不推荐使用，因为需要自己需要自己维护生命周期。
     * @param runUi
     */
    @SuppressWarnings("不能绑定生命周期，不推荐使用")
    public void postDelayedUI(final TaskRunnable runUi, final long delayMillis){
        Observable.timer(delayMillis, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        runUi.run();
                    }
                });
    }

    /**
     * Worker线程中执行
     * 不推荐使用，因为需要自己需要自己维护生命周期。
     * @param runWork
     */
    @SuppressWarnings("不能绑定生命周期，不推荐使用")
    public void postDelayedWorker(final TaskRunnable runWork, final long delayMillis){
        Observable.timer(delayMillis, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.from(ThreadPool.getExecutor()))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        runWork.run();
                    }
                });
    }

    /**
     * Single线程中执行
     * 不推荐使用，因为需要自己需要自己维护生命周期。
     * @param runSingle
     */
    @SuppressWarnings("不能绑定生命周期，不推荐使用")
    public void postDelayedSingle(final TaskRunnable runSingle, final long delayMillis){
        Observable.timer(delayMillis, TimeUnit.MICROSECONDS)
                .observeOn(Schedulers.single())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        runSingle.run();
                    }
                });
    }
}
