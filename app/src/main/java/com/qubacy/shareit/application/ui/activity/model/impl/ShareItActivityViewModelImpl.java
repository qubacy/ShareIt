package com.qubacy.shareit.application.ui.activity.model.impl;

import androidx.lifecycle.SavedStateHandle;

import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application._common.error.model.ShareItError;
import com.qubacy.shareit.application.data.error.repository.source.local.database._common.LocalErrorDatabaseDataSource;
import com.qubacy.shareit.application.ui.activity.model._common.ShareItActivityViewModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class ShareItActivityViewModelImpl extends ShareItActivityViewModel {
    static String ERROR_KEY = "error";

    @NotNull
    private final SavedStateHandle _store;
    @NotNull
    private final LocalErrorDatabaseDataSource _localErrorDatabaseDataSource;

    @Nullable
    private ShareItError _error;

    public ShareItActivityViewModelImpl(
        @NotNull SavedStateHandle store,
        @NotNull LocalErrorDatabaseDataSource localErrorDatabaseDataSource
    ) {
        _store = store;
        _localErrorDatabaseDataSource = localErrorDatabaseDataSource;

        recoverState();
    }

    @Override
    protected void onCleared() {
        _store.set(ERROR_KEY, _error);

        super.onCleared();
    }

    private void recoverState() {
        if (_store.contains(ERROR_KEY)) _error = _store.get(ERROR_KEY);
    }

    @Override
    public @NotNull ShareItError retrieveError(@NotNull ErrorReference errorReference) {
        _error = _localErrorDatabaseDataSource.getErrorByReference(errorReference)
            .subscribeOn(Schedulers.io())
            .blockingGet();

        return _error;
    }

    @Override
    public void absorbError() {
        _error = null;
    }

    @Override
    public @Nullable ShareItError lastError() {
        return _error;
    }
}
