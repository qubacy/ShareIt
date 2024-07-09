package com.qubacy.shareit.application.data.error.repository.source.local.database._common.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = ErrorDatabaseEntity.TABLE_NAME)
public class ErrorDatabaseEntity {
    public static final String TABLE_NAME = "Error";

    public static final String ID_PROP_NAME = "id";
    public static final String IS_CRITICAL_PROP_NAME = "is_critical";

    @PrimaryKey
    @ColumnInfo(name = ID_PROP_NAME)
    public long id;
    @ColumnInfo(name = IS_CRITICAL_PROP_NAME)
    public boolean isCritical;
}
