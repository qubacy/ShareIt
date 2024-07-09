package com.qubacy.shareit.application.data.error.repository.source.local.database._common;

import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application._common.error.model.ShareItError;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Single;

public interface LocalErrorDatabaseDataSource {
    Single<ShareItError> getErrorByReference(@NotNull ErrorReference errorReference);
}
