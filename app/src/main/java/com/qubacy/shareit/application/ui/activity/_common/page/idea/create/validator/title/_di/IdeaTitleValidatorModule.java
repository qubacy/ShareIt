package com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.title._di;

import com.qubacy.shareit.application.ui._common.validator.Validator;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.title.impl.IdeaTitleValidatorImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@InstallIn(FragmentComponent.class)
@Module
public interface IdeaTitleValidatorModule {
    @IdeaTitleValidatorQualifier
    @Binds
    Validator bindIdeaTitleValidator(IdeaTitleValidatorImpl ideaTitleValidator);
}
