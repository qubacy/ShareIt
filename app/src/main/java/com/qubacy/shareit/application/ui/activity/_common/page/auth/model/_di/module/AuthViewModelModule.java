package com.qubacy.shareit.application.ui.activity._common.page.auth.model._di.module;

import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model.impl.factory.AuthViewModelImplFactory;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@InstallIn(FragmentComponent.class)
@Module
public abstract class AuthViewModelModule {
    @AuthViewModelFactoryQualifier
    @Provides
    static ViewModelProvider.Factory provideAuthViewModelFactory(
        ErrorBus errorBus
    ) {
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        return new AuthViewModelImplFactory(errorBus, firebaseAuth);
    }
}
