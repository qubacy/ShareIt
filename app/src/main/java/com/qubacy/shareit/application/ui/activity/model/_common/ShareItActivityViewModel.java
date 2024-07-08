package com.qubacy.shareit.application.ui.activity.model._common;

import androidx.lifecycle.ViewModel;

import com.qubacy.shareit.application._common.error.model.ShareItError;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ShareItActivityViewModel extends ViewModel {
    // todo: consider refactoring this by implementing a state:
    @Nullable
    public abstract ShareItError lastError();
    @NotNull
    public abstract ShareItError retrieveError(int id, @Nullable String cause);
}
