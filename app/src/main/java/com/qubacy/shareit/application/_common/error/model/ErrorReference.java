package com.qubacy.shareit.application._common.error.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorReference that)) return false;

        return id == that.id && Objects.equals(cause, that.cause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cause);
    }
}
