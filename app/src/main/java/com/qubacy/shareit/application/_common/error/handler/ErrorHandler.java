package com.qubacy.shareit.application._common.error.handler;

import androidx.annotation.NonNull;

import com.qubacy.shareit.application._common.exception.ShareItException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ErrorHandler implements Thread.UncaughtExceptionHandler {
    public interface Callback {
        void onErrorCaught(int id, @Nullable String cause);
    }

    private final Callback _callback;

    public ErrorHandler(@NotNull Callback callback) {
        _callback = callback;
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        if (!(e instanceof ShareItException exception)) throw new RuntimeException(e);

        _callback.onErrorCaught(exception.id, exception.cause);
    }
}
