package com.qubacy.shareit.application.data.error.repository.source.local.database._common.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.jetbrains.annotations.NotNull;

@Entity(
    tableName = ErrorLocalizationDatabaseEntity.TABLE_NAME,
    primaryKeys = {
        ErrorLocalizationDatabaseEntity.ID_PROP_NAME,
        ErrorLocalizationDatabaseEntity.LANG_TOKEN_PROP_NAME
    },
    foreignKeys = {
        @ForeignKey(
            entity = ErrorDatabaseEntity.class,
            parentColumns = {ErrorDatabaseEntity.ID_PROP_NAME},
            childColumns = {ErrorLocalizationDatabaseEntity.ID_PROP_NAME},
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class ErrorLocalizationDatabaseEntity {
    public static final String TABLE_NAME = "ErrorLocalization";

    public static final String ID_PROP_NAME = "id";
    public static final String LANG_TOKEN_PROP_NAME = "lang";
    public static final String LOCALIZATION_PROP_NAME = "localization";

    @ColumnInfo(name = ID_PROP_NAME)
    public long id;
    @ColumnInfo(name = LANG_TOKEN_PROP_NAME)
    @NotNull
    public String lang;
    @ColumnInfo(name = LOCALIZATION_PROP_NAME)
    @NotNull
    public String localization;
}

