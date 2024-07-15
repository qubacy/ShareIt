package com.qubacy.shareit.application.ui.activity._common.page.auth.validator.password._di;

import com.qubacy.shareit.application.ui._common.validator.Validator;
import com.qubacy.shareit.application.ui.activity._common.page.auth.validator.password.impl.PasswordValidatorImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@InstallIn(FragmentComponent.class)
@Module
public interface PasswordValidatorModule {
    @Binds
    @PasswordValidatorQualifier
    Validator bindPasswordValidator(PasswordValidatorImpl passwordValidator);
}
