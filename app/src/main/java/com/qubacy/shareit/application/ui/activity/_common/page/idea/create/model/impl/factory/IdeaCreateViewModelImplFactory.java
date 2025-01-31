package com.qubacy.shareit.application.ui.activity._common.page.idea.create.model.impl.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model.impl.IdeaCreateViewModelImpl;

import org.jetbrains.annotations.NotNull;

public class IdeaCreateViewModelImplFactory extends AbstractSavedStateViewModelFactory {
    private final ErrorBus _errorBus;
    private final DatabaseReference _database;

    public IdeaCreateViewModelImplFactory(
        @NotNull ErrorBus errorBus,
        @NotNull DatabaseReference database
    ) {
        _errorBus = errorBus;
        _database = database;
    }

    @NonNull
    @Override
    protected <T extends ViewModel> T create(
        @NonNull String s,
        @NonNull Class<T> aClass,
        @NonNull SavedStateHandle savedStateHandle
    ) {
        if (!aClass.isAssignableFrom(IdeaCreateViewModelImpl.class))
            throw new IllegalArgumentException();

        return (T) new IdeaCreateViewModelImpl(savedStateHandle, _errorBus, _database);
    }
}
