package com.qubacy.shareit.application.data.error.repository.source.local.database.impl;

import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application._common.error.model.ShareItError;
import com.qubacy.shareit.application.data.error.repository.source.local.database._common.dao.ErrorDatabaseDao;
import com.qubacy.shareit.application.data.error.repository.source.local.database._common.view.ErrorDatabaseView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.rxjava3.core.Single;

public class LocalErrorDatabaseDataSourceImplTest {
    private LocalErrorDatabaseDataSourceImpl _instance;

    private ErrorDatabaseDao _errorDatabaseDaoMock;
    private Single<ErrorDatabaseView> _errorDatabaseDaoGetErrorViewByIdAndLangResult;

    @Before
    public void setup() {
        _errorDatabaseDaoMock = mockErrorDatabaseDao();
        _instance = new LocalErrorDatabaseDataSourceImpl(_errorDatabaseDaoMock);
    }

    private ErrorDatabaseDao mockErrorDatabaseDao() {
        final ErrorDatabaseDao errorDatabaseDao = Mockito.mock(ErrorDatabaseDao.class);

        Mockito.when(errorDatabaseDao.getErrorViewByIdAndLang(Mockito.anyLong(), Mockito.anyString()))
            .thenAnswer(invocation -> _errorDatabaseDaoGetErrorViewByIdAndLangResult);

        return errorDatabaseDao;
    }

    @Test
    public void getErrorByReferenceTest() {
        final ErrorReference errorReference = new ErrorReference(0);
        final ErrorDatabaseView errorDatabaseView = new ErrorDatabaseView(
            errorReference.id, "ts", "test", false);

        final ShareItError expectedError = ShareItError
            .fromErrorDatabaseView(errorDatabaseView, null);

        _errorDatabaseDaoGetErrorViewByIdAndLangResult = Single.just(errorDatabaseView);

        final Single<ShareItError> getErrorResultSingle = _instance
            .getErrorByReference(errorReference);
        final ShareItError gottenError = getErrorResultSingle.blockingGet();

        Mockito.verify(_errorDatabaseDaoMock)
            .getErrorViewByIdAndLang(Mockito.anyLong(), Mockito.anyString());
        Assert.assertEquals(expectedError, gottenError);
    }
}
