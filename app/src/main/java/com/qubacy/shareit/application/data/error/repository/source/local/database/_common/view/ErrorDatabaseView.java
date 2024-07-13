package com.qubacy.shareit.application.data.error.repository.source.local.database._common.view;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

import com.qubacy.shareit.application.data.error.repository.source.local.database._common.entity.ErrorDatabaseEntity;
import com.qubacy.shareit.application.data.error.repository.source.local.database._common.entity.ErrorLocalizationDatabaseEntity;

import org.jetbrains.annotations.NotNull;

@DatabaseView(
    "SELECT " +
        ErrorDatabaseEntity.TABLE_NAME + '.' + ErrorDatabaseEntity.ID_PROP_NAME + ',' +
        ErrorLocalizationDatabaseEntity.TABLE_NAME + '.' + ErrorLocalizationDatabaseEntity.LANG_TOKEN_PROP_NAME + ',' +
        ErrorLocalizationDatabaseEntity.TABLE_NAME + '.' + ErrorLocalizationDatabaseEntity.LOCALIZATION_PROP_NAME + ',' +
        ErrorDatabaseEntity.TABLE_NAME + '.' + ErrorDatabaseEntity.IS_CRITICAL_PROP_NAME + ' ' +
    "FROM " + ErrorDatabaseEntity.TABLE_NAME + ", " + ErrorLocalizationDatabaseEntity.TABLE_NAME + ' ' +
    "WHERE " + ErrorDatabaseEntity.TABLE_NAME + '.' + ErrorDatabaseEntity.ID_PROP_NAME + " = " +
        ErrorLocalizationDatabaseEntity.TABLE_NAME + '.' + ErrorLocalizationDatabaseEntity.ID_PROP_NAME
)
public class ErrorDatabaseView {
    public static final String VIEW_NAME = ErrorDatabaseView.class.getSimpleName();

    public static final String ID_PROP_NAME = ErrorDatabaseEntity.ID_PROP_NAME;
    public static final String LANG_PROP_NAME = ErrorLocalizationDatabaseEntity.LANG_TOKEN_PROP_NAME;
    public static final String LOCALIZATION_PROP_NAME = ErrorLocalizationDatabaseEntity.LOCALIZATION_PROP_NAME;
    public static final String IS_CRITICAL_PROP_NAME = ErrorDatabaseEntity.IS_CRITICAL_PROP_NAME;

    @ColumnInfo(name = ID_PROP_NAME)
    public long id;
    @ColumnInfo(name = LANG_PROP_NAME)
    @NotNull
    public String lang;
    @ColumnInfo(name = LOCALIZATION_PROP_NAME)
    @NotNull
    public String localization;
    @ColumnInfo(name = IS_CRITICAL_PROP_NAME)
    public boolean isCritical;

    public ErrorDatabaseView() {}
    public ErrorDatabaseView(
        long id,
        @NotNull String lang,
        @NotNull String localization,
        boolean isCritical
    ) {
        this.id = id;
        this.lang = lang;
        this.localization = localization;
        this.isCritical = isCritical;
    }
}
