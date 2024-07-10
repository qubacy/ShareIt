package com.qubacy.shareit.application._common.error;

public enum ErrorEnum {
    // UI:
    // Auth:
    INVALID_EMAIL_OR_PASSWORD(0),
    SIGN_UP_FAIL(1),
    SIGN_IN_FAIL(2),

    // Idea List:
    DATABASE_FAIL(3)
    ;

    public final int id;

    private ErrorEnum(int id) {
        this.id = id;
    }
}
