package com.qubacy.shareit.application._common.exception;

import com.qubacy.shareit.application._common.error.model.ErrorReference;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShareItException extends RuntimeException {
    public final ErrorReference errorReference;

    public ShareItException(int id, @Nullable String cause) {
        this(new ErrorReference(id, cause));
    }

    public ShareItException(@NotNull ErrorReference errorReference) {
        this.errorReference = errorReference;
    }
}