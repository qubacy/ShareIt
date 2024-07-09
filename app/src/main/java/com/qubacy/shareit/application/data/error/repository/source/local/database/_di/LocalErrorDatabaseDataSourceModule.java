package com.qubacy.shareit.application.data.error.repository.source.local.database._di;

import com.qubacy.shareit.application.data.error.repository.source.local.database._common.LocalErrorDatabaseDataSource;
import com.qubacy.shareit.application.data.error.repository.source.local.database.impl.LocalErrorDatabaseDataSourceImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public interface LocalErrorDatabaseDataSourceModule {
    @Binds
    LocalErrorDatabaseDataSource bindLocalErrorDatabaseDataSource(
        LocalErrorDatabaseDataSourceImpl localErrorDatabaseDataSource
    );
}
