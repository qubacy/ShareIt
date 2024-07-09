package com.qubacy.shareit.application._common.error.bus._common;

import com.qubacy.shareit.application._common.error.model.ErrorReference;

import org.jetbrains.annotations.NotNull;

public interface ErrorBus {
    interface Listener {
        void onErrorGotten(@NotNull ErrorReference errorReference);
    }

    void setup(@NotNull Listener listener);
    void dispose();

    void emitError(@NotNull ErrorReference errorReference);
}
