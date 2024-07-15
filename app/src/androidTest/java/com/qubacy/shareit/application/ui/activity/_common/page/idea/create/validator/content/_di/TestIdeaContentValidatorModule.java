package com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.content._di;

import com.qubacy.shareit.application.ui._common.validator.Validator;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.testing.TestInstallIn;

@Module
@TestInstallIn(
    components = {FragmentComponent.class},
    replaces = {IdeaContentValidatorModule.class}
)
public class TestIdeaContentValidatorModule {
    public static Validator instance;

    @IdeaContentValidatorQualifier
    @Provides
    public static Validator provideIdeaContentValidator() {
        instance = Mockito.mock(Validator.class);

        return instance;
    }
}
