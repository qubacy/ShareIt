package com.qubacy.shareit.application.ui.activity._common.page.auth.validator.email.impl;

import androidx.annotation.NonNull;

import com.qubacy.shareit.application.ui._common.validator.Validator;

import javax.inject.Inject;

public class EmailValidatorImpl implements Validator {
    @Inject
    public EmailValidatorImpl() {}

    @NonNull
    @Override
    public String regExp() {
        return "^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,6}$";
    }
}
