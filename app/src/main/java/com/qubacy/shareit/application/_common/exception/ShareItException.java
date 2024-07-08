package com.qubacy.shareit.application._common.exception;

import com.qubacy.shareit.application._common.error.model.ShareItError;

import org.jetbrains.annotations.NotNull;

public class ShareItException extends RuntimeException {
    public final ShareItError error;

    public ShareItException(@NotNull ShareItError error) {
        this.error = error;
    }
}