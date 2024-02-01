package com.marzbani.adjoetask.infrastructure.useCase;


import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class UseCase {

    protected Disposable lastDisposable;
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void disposeLast() {
        if (lastDisposable != null && !lastDisposable.isDisposed()) {
            lastDisposable.dispose();
        }
    }

    public void dispose() {
        compositeDisposable.clear();
    }
}
