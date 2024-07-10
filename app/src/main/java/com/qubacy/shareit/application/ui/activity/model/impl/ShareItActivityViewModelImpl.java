package com.qubacy.shareit.application.ui.activity.model.impl;

import androidx.lifecycle.SavedStateHandle;

import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.data.error.repository.source.local.database._common.LocalErrorDatabaseDataSource;
import com.qubacy.shareit.application.ui.activity.model._common.ShareItActivityViewModel;
import com.qubacy.shareit.application.ui.activity.model._common.state.ShareItActivityState;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class ShareItActivityViewModelImpl extends ShareItActivityViewModel {
    static final String STATE_KEY = "state";

    @NotNull
    private final SavedStateHandle _store;
    @NotNull
    private final LocalErrorDatabaseDataSource _localErrorDatabaseDataSource;

    private final BehaviorSubject<ShareItActivityState> _stateController;
    @Nullable
    private Disposable _errorSubscription;

    public ShareItActivityViewModelImpl(
        @NotNull SavedStateHandle store,
        @NotNull LocalErrorDatabaseDataSource localErrorDatabaseDataSource
    ) {
        _store = store;
        _localErrorDatabaseDataSource = localErrorDatabaseDataSource;

        _stateController = BehaviorSubject.createDefault(
            new ShareItActivityState(null)
        );

        recoverState();
    }

    @Override
    protected void onCleared() {
        _store.set(STATE_KEY, _stateController.getValue());

        if (_errorSubscription != null) _errorSubscription.dispose();

        super.onCleared();
    }

    private void recoverState() {
        if (!_store.contains(STATE_KEY)) return;

        final ShareItActivityState preservedState = _store.get(STATE_KEY);

        _stateController.onNext(preservedState);
    }

    @Override
    public void retrieveError(@NotNull ErrorReference errorReference) {
        _errorSubscription = _localErrorDatabaseDataSource.getErrorByReference(errorReference)
            .subscribeOn(Schedulers.io())
            .subscribe((error) -> {
                _stateController.onNext(new ShareItActivityState(error));
            });
    }

    @Override
    public void absorbError() {
        _stateController.onNext(new ShareItActivityState(null));
    }

    @Override
    public @NotNull Observable<ShareItActivityState> getState() {
        return _stateController;
    }
}
