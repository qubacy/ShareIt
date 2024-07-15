package com.qubacy.shareit.application._common.error.bus._di;


import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.testing.TestInstallIn;

@Module
@TestInstallIn(
    components = {SingletonComponent.class},
    replaces = {ErrorBusModule.class}
)
public abstract class TestErrorBusModule {
    public static ErrorBus instance;

    @Singleton
    @Provides
    public static ErrorBus provideErrorBus() {
        instance = Mockito.mock(ErrorBus.class);

        return instance;
    }
}
