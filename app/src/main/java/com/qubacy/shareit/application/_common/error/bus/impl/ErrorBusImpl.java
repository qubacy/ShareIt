package com.qubacy.shareit.application._common.error.bus.impl;

import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.model.ErrorReference;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class ErrorBusImpl implements ErrorBus {
    private final PublishSubject<ErrorReference> _controller = PublishSubject.create();
    private final Observable<ErrorReference> _observable;

    private Disposable _subscription;

    @Inject
    public ErrorBusImpl() {
        _observable = _controller.subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void setup(@NotNull Listener listener) {
        _subscription = _observable.subscribe(
            errorReference -> listener.onErrorGotten(errorReference)
        );
    }

    @Override
    public void dispose() {
        _subscription.dispose();
    }

    @Override
    public void emitError(@NotNull ErrorReference errorReference) {
        _controller.onNext(errorReference);
    }
}
