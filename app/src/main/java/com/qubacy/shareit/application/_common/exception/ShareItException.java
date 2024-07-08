package com.qubacy.shareit.application._common.exception;

import androidx.annotation.StringRes;

import org.jetbrains.annotations.Nullable;

public class ShareItException extends RuntimeException {
    @StringRes
    public final int id;
    public final String cause;

    public ShareItException(int id, @Nullable String cause) {
        this.id = id;
        this.cause = cause;
    }
}