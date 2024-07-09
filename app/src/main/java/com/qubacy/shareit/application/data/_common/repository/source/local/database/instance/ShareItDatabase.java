package com.qubacy.shareit.application.data._common.repository.source.local.database.instance;

import androidx.room.RoomDatabase;

import com.qubacy.shareit.application.data.error.repository.source.local.database._common.dao.ErrorDatabaseDao;
import com.qubacy.shareit.application.data.error.repository.source.local.database._common.entity.ErrorDatabaseEntity;
import com.qubacy.shareit.application.data.error.repository.source.local.database._common.entity.ErrorLocalizationDatabaseEntity;
import com.qubacy.shareit.application.data.error.repository.source.local.database._common.view.ErrorDatabaseView;

@androidx.room.Database(
    entities = {ErrorDatabaseEntity.class, ErrorLocalizationDatabaseEntity.class},
    views = {ErrorDatabaseView.class},
    version = 2
)
public abstract class ShareItDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "database.db";

    public abstract ErrorDatabaseDao errorDao();
}
