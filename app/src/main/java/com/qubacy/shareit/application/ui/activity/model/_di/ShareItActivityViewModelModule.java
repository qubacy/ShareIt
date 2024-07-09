package com.qubacy.shareit.application.ui.activity.model._di;

import androidx.lifecycle.ViewModelProvider;

import com.qubacy.shareit.application.ui.activity.model.impl.factory.ShareItActivityViewModelImplFactory;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@InstallIn(ActivityComponent.class)
@Module
public interface ShareItActivityViewModelModule {
    @ShareItActivityViewModelFactoryQualifier
    @Binds
    ViewModelProvider.Factory bindShareItActivityViewModelFactory(
        ShareItActivityViewModelImplFactory shareItActivityViewModelFactory
    );
}
