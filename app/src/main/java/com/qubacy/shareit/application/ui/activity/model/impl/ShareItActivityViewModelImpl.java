package com.qubacy.shareit.application.ui.activity.model.impl;

import androidx.lifecycle.SavedStateHandle;

import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application._common.error.model.ShareItError;
import com.qubacy.shareit.application.ui.activity.model._common.ShareItActivityViewModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShareItActivityViewModelImpl extends ShareItActivityViewModel {
    static String ERROR_KEY = "error";

    @NotNull
    private final SavedStateHandle _store;
    @Nullable
    private ShareItError _error;

    public ShareItActivityViewModelImpl(
        @NotNull SavedStateHandle store
    ) {
        _store = store;

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
        // todo: retrieving the error using a local error data source;

        // todo: set the gotten error as _error;

        _error = new ShareItError(errorReference.id, "msg", false); // todo: delete;

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
