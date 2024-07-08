package com.qubacy.shareit.application.ui.activity.model._di;

import androidx.lifecycle.ViewModelProvider;

import com.qubacy.shareit.application.ui.activity.model.impl.factory.ShareItActivityViewModelImplFactory;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@InstallIn(ActivityComponent.class)
@Module
public abstract class ShareItActivityViewModelModule {
    @ShareItActivityViewModelFactoryQualifier
    @Provides
    static ViewModelProvider.Factory provideShareItActivityViewModelFactory() {
        return new ShareItActivityViewModelImplFactory();
    }
}
