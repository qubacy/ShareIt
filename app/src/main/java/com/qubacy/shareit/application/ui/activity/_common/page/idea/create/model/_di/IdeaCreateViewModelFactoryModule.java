package com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._di;

import androidx.lifecycle.ViewModelProvider;

import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model.impl.factory.IdeaCreateViewModelImplFactory;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@InstallIn(FragmentComponent.class)
@Module
public abstract class IdeaCreateViewModelFactoryModule {
    @IdeaCreateViewModelFactoryQualifier
    @Provides
    static ViewModelProvider.Factory provideIdeaCreateViewModelFactory(
        ErrorBus errorBus
    ) {
        return new IdeaCreateViewModelImplFactory(errorBus);
    }
}
