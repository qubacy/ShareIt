package com.qubacy.shareit.application.ui.activity.main.model.impl.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.qubacy.shareit.application.data.error.repository.source.local.database._common.LocalErrorDatabaseDataSource;
import com.qubacy.shareit.application.ui.activity.main.model.impl.ShareItActivityViewModelImpl;

import javax.inject.Inject;

public class ShareItActivityViewModelImplFactory extends AbstractSavedStateViewModelFactory {
    private final LocalErrorDatabaseDataSource _localErrorDatabaseDataSource;

    @Inject
    public ShareItActivityViewModelImplFactory(
        @NonNull LocalErrorDatabaseDataSource localErrorDatabaseDataSource
    ) {
        _localErrorDatabaseDataSource = localErrorDatabaseDataSource;
    }

    @NonNull
    @Override
    protected <T extends ViewModel> T create(
        @NonNull String s,
        @NonNull Class<T> aClass,
        @NonNull SavedStateHandle savedStateHandle
    ) {
        if (!aClass.isAssignableFrom(ShareItActivityViewModelImpl.class))
            throw new IllegalArgumentException();

        return (T) new ShareItActivityViewModelImpl(savedStateHandle, _localErrorDatabaseDataSource);
    }
}
