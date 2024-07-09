package com.qubacy.shareit.application._common.error.bus._di;

import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.bus.impl.ErrorBusImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public interface ErrorBusModule {
    @Singleton
    @Binds
    ErrorBus bindErrorBus(ErrorBusImpl errorBus);
}
