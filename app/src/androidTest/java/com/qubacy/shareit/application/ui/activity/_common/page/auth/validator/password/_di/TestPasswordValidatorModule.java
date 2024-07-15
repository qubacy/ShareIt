package com.qubacy.shareit.application.ui.activity._common.page.auth.validator.password._di;

import com.qubacy.shareit.application.ui._common.validator.Validator;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.testing.TestInstallIn;

@Module
@TestInstallIn(
    components = {FragmentComponent.class},
    replaces = {PasswordValidatorModule.class}
)
public abstract class TestPasswordValidatorModule {
    public static Validator instance;

    @PasswordValidatorQualifier
    @Provides
    public static Validator providePasswordValidator() {
        instance = Mockito.mock(Validator.class);

        return instance;
    }
}
