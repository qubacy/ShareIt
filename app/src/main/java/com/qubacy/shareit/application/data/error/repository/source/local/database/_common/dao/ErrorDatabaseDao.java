package com.qubacy.shareit.application.data.error.repository.source.local.database._common.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.qubacy.shareit.application.data.error.repository.source.local.database._common.view.ErrorDatabaseView;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Maybe;

@Dao
public interface ErrorDatabaseDao {
    @Query(
        "SELECT * FROM ErrorDatabaseView WHERE id = :id AND lang = :lang"
    )
    Maybe<ErrorDatabaseView> getErrorViewByIdAndLang(long id, @NotNull String lang);
}
