package com.qubacy.shareit.application.data._common.repository.source.local.database.instance._di;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.qubacy.shareit.application.data._common.repository.source.local.database.instance.ShareItDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public abstract class ShareItDatabaseModule {
    @NonNull
    @Provides
    @Singleton
    public static ShareItDatabase provideShareItDatabase(
        @ApplicationContext Context context
    ) {
        return Room.databaseBuilder(
            context,
            ShareItDatabase.class,
            ShareItDatabase.DATABASE_NAME
        ).createFromAsset(
            ShareItDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build();
    }
}
