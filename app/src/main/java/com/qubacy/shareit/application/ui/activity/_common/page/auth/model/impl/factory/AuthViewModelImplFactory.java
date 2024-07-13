package com.qubacy.shareit.application.ui.activity._common.page.auth.model.impl.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model.impl.AuthViewModelImpl;

import org.jetbrains.annotations.NotNull;

public class AuthViewModelImplFactory extends AbstractSavedStateViewModelFactory {
    private final ErrorBus _errorBus;
    private final FirebaseAuth _firebaseAuth;

    public AuthViewModelImplFactory(
        @NotNull ErrorBus errorBus,
        @NotNull FirebaseAuth firebaseAuth
    ) {
        _errorBus = errorBus;
        _firebaseAuth = firebaseAuth;
    }

    @NonNull
    @Override
    protected <T extends ViewModel> T create(
        @NonNull String s,
        @NonNull Class<T> aClass,
        @NonNull SavedStateHandle savedStateHandle
    ) {
        if (!aClass.isAssignableFrom(AuthViewModelImpl.class))
            throw new IllegalArgumentException();

        return (T) new AuthViewModelImpl(savedStateHandle, _errorBus, _firebaseAuth);
    }
}
