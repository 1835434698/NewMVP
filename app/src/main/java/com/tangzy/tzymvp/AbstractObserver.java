package com.tangzy.tzymvp;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class AbstractObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public abstract void onNext(T t);
}
