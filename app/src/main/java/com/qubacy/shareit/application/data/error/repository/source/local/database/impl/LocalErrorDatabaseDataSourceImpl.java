package com.qubacy.shareit.application.data.error.repository.source.local.database.impl;

import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application._common.error.model.ShareItError;
import com.qubacy.shareit.application.data.error.repository.source.local.database._common.LocalErrorDatabaseDataSource;
import com.qubacy.shareit.application.data.error.repository.source.local.database._common.dao.ErrorDatabaseDao;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class LocalErrorDatabaseDataSourceImpl implements LocalErrorDatabaseDataSource {
    private final ErrorDatabaseDao _errorDatabaseDao;

    @Inject
    public LocalErrorDatabaseDataSourceImpl(@NotNull ErrorDatabaseDao errorDatabaseDao) {
        _errorDatabaseDao = errorDatabaseDao;
    }

    @Override
    public Single<ShareItError> getErrorByReference(@NotNull ErrorReference errorReference) {
        final String lang = Locale.getDefault().getLanguage();

        return _errorDatabaseDao.getErrorViewByIdAndLang(errorReference.id, lang)
            .map(errorDatabaseView -> ShareItError.fromErrorDatabaseView(
                errorDatabaseView, errorReference.cause
            ));
    }
}
