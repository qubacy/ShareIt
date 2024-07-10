package com.qubacy.shareit.application.ui.activity.model._common.state;

import com.qubacy.shareit.application._common.error.model.ShareItError;
import com.qubacy.shareit.application.ui.activity._common.model.state.ShareItState;

import org.jetbrains.annotations.Nullable;

public class ShareItActivityState extends ShareItState {
    @Nullable
    public final ShareItError error;

    public ShareItActivityState(
        @Nullable ShareItError error
    ) {
        this.error = error;
    }
}
