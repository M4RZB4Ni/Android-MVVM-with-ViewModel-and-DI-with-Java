package com.marzbani.adjoetask.infrastructure.useCase;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public abstract class SingleUseCase<T> extends UseCase {
    public interface OnSuccess<T> {
        void onSuccess(T t);
    }

    public interface OnError {
        void onError(Throwable throwable);
    }

    public interface OnFinished {
        void onFinished();
    }
    protected abstract Single<T> buildUseCaseSingle();

    public void execute(
            OnSuccess<T> onSuccess,
            OnError onError,
            OnFinished onFinished
    ) {
        disposeLast();
        Disposable disposable = buildUseCaseSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished::onFinished)
                .subscribe(onSuccess::onSuccess, onError::onError);

        compositeDisposable.add(disposable);
    }
}


