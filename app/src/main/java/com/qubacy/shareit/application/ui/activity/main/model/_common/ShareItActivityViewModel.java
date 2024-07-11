package com.qubacy.shareit.application.ui.activity.main.model._common;

import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.ui.activity._common.model.base.ShareItViewModel;
import com.qubacy.shareit.application.ui.activity.main.model._common.state.ShareItActivityState;

import org.jetbrains.annotations.NotNull;

public abstract class ShareItActivityViewModel extends ShareItViewModel<ShareItActivityState> {
    @NotNull
    public abstract void retrieveError(@NotNull ErrorReference errorReference);
    public abstract void absorbError();
}
