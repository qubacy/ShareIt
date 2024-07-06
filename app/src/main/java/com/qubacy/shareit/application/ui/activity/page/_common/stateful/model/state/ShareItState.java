package com.qubacy.shareit.application.ui.activity.page._common.stateful.model.state;

import com.qubacy.shareit.application.ui._common.presentation.error.ErrorPresentation;

import org.jetbrains.annotations.NotNull;

public abstract class ShareItState {
    private final ErrorPresentation _error;

    public ShareItState(@NotNull ErrorPresentation error) {
        _error = error;
    }

    public ErrorPresentation getError() {
        return _error;
    }
}
