package com.qubacy.shareit.application.ui.activity._common.page.auth.validator.email._di;

import com.qubacy.shareit.application.ui._common.validator.Validator;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.testing.TestInstallIn;

@Module
@TestInstallIn(
    components = {FragmentComponent.class},
    replaces = {EmailValidatorModule.class}
)
public abstract class TestEmailValidatorModule {
    public static Validator instance;

    @EmailValidatorQualifier
    @Provides
    public static Validator provideEmailValidator() {
        instance = Mockito.mock(Validator.class);

        return instance;
    }
}
