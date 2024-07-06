package com.qubacy.shareit.application.ui.activity.page.auth.model._di.module;

import androidx.lifecycle.ViewModelProvider;

import com.qubacy.shareit.application.ui.activity.page.auth.model.impl.factory.AuthViewModelImplFactory;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@InstallIn(FragmentComponent.class)
@Module
public abstract class AuthViewModelModule {
    @AuthViewModelFactoryQualifier
    @Provides
    static ViewModelProvider.Factory provideAuthViewModelFactory() {
        return new AuthViewModelImplFactory();
    }
}
