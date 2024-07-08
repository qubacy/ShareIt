package com.qubacy.shareit.application.ui.activity.page.auth.validator.password.impl;

import androidx.annotation.NonNull;

import com.qubacy.shareit.application.ui._common.validator.Validator;

import javax.inject.Inject;

public class PasswordValidatorImpl implements Validator {
    @Inject
    public PasswordValidatorImpl() {}

    @NonNull
    @Override
    public String regExp() {
        return "^\\S{8,}$";
    }
}
