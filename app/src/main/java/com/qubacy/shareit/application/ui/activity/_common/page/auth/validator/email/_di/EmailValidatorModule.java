package com.qubacy.shareit.application.ui.activity._common.page.auth.validator.email._di;

import com.qubacy.shareit.application.ui._common.validator.Validator;
import com.qubacy.shareit.application.ui.activity._common.page.auth.validator.email.impl.EmailValidatorImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@InstallIn(FragmentComponent.class)
@Module
public interface EmailValidatorModule {
    @Binds
    @EmailValidatorQualifier
    Validator bindEmailValidator(EmailValidatorImpl emailValidator);
}