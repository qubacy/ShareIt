package com.qubacy.shareit.application._common.error.model;

import javax.annotation.Nullable;

public class ErrorReference {
    public final int id;
    public final String cause;

    public ErrorReference(int id) {
        this(id, null);
    }

    public ErrorReference(
        int id,
        @Nullable String cause
    ) {
        this.id = id;
        this.cause = cause;
    }
}
