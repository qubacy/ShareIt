package com.qubacy.shareit.application._common.error.handler;

import androidx.annotation.NonNull;

import com.qubacy.shareit.application._common.error.model.ShareItError;
import com.qubacy.shareit.application._common.exception.ShareItException;

import org.jetbrains.annotations.NotNull;

public class ErrorHandler implements Thread.UncaughtExceptionHandler {
    public interface Callback {
        void onErrorCaught(@NotNull ShareItError error);
    }

    private final Callback _callback;

    public ErrorHandler(@NotNull Callback callback) {
        _callback = callback;
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        if (!(e instanceof ShareItException)) throw new RuntimeException(e);

        _callback.onErrorCaught(((ShareItException) e).error);
    }
}
