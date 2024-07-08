package com.qubacy.shareit.application.ui.activity.model.impl.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.qubacy.shareit.application.ui.activity.model.impl.ShareItActivityViewModelImpl;

public class ShareItActivityViewModelImplFactory extends AbstractSavedStateViewModelFactory {
    @NonNull
    @Override
    protected <T extends ViewModel> T create(
        @NonNull String s,
        @NonNull Class<T> aClass,
        @NonNull SavedStateHandle savedStateHandle
    ) {
        if (!aClass.isAssignableFrom(ShareItActivityViewModelImpl.class))
            throw new IllegalArgumentException();

        return (T) new ShareItActivityViewModelImpl(savedStateHandle);
    }
}
