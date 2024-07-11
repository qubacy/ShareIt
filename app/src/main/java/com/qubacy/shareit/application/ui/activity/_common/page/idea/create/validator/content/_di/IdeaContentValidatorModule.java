package com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.content._di;

import com.qubacy.shareit.application.ui._common.validator.Validator;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.content.impl.IdeaContentValidatorImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@InstallIn(FragmentComponent.class)
@Module
public interface IdeaContentValidatorModule {
    @IdeaContentValidatorQualifier
    @Binds
    Validator bindIdeaContentValidator(IdeaContentValidatorImpl ideaContentValidator);
}
