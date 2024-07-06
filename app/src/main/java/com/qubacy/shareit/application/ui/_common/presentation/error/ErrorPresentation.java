package com.qubacy.shareit.application.ui._common.presentation.error;

public class ErrorPresentation {
    private final int _id;
    private final String _text;
    private final boolean _isCritical;

    ErrorPresentation(
        int id,
        String text,
        boolean isCritical
    ) {
        _id = id;
        _text = text;
        _isCritical = isCritical;
    }

    int getId() {
        return _id;
    }

    String getText() {
        return _text;
    }

    boolean isCritical() {
        return _isCritical;
    }
}
