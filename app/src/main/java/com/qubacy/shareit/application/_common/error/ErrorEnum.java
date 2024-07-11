package com.qubacy.shareit.application._common.error;

public enum ErrorEnum {
    // todo: restructure:

    // UI:
    // Auth:
    INVALID_EMAIL_OR_PASSWORD(0),
    SIGN_UP_FAIL(1),
    SIGN_IN_FAIL(2),

    // Database:
    DATABASE_FAIL(3),

    // Idea Create:
    INVALID_IDEA_DATA(4),

    ;

    public final int id;

    private ErrorEnum(int id) {
        this.id = id;
    }
}
