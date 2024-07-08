package com.qubacy.shareit.application.ui.activity.page._common.stateful.model.state;

import com.qubacy.shareit.application._common.error.model.ShareItError;

import org.jetbrains.annotations.Nullable;

public abstract class ShareItState {
    public final ShareItError error;

    public ShareItState(@Nullable ShareItError error) {
        this.error = error;
    }

}
