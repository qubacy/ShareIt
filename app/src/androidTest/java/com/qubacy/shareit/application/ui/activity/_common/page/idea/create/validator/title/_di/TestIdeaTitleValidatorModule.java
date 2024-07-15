package com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.title._di;

import com.qubacy.shareit.application.ui._common.validator.Validator;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.testing.TestInstallIn;

@Module
@TestInstallIn(
    components = {FragmentComponent.class},
    replaces = {IdeaTitleValidatorModule.class}
)
public abstract class TestIdeaTitleValidatorModule {
    public static Validator instance;

    @IdeaTitleValidatorQualifier
    @Provides
    public static Validator provideIdeaTitleValidator() {
        instance = Mockito.mock(Validator.class);

        return instance;
    }
}
