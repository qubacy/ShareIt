package com.qubacy.shareit.application.data.error.repository.source.local.database._common.dao._di;

import com.qubacy.shareit.application.data._common.repository.source.local.database.instance.ShareItDatabase;
import com.qubacy.shareit.application.data.error.repository.source.local.database._common.dao.ErrorDatabaseDao;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public abstract class ErrorDatabaseDaoModule {
    @Provides
    public static ErrorDatabaseDao provideErrorDatabaseDao(
        ShareItDatabase database
    ) {
        return database.errorDao();
    }
}
