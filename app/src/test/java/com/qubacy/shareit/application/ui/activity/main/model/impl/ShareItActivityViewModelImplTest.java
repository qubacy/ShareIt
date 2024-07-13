package com.qubacy.shareit.application.ui.activity.main.model.impl;

import androidx.lifecycle.SavedStateHandle;

import com.qubacy.shareit._test.rule.RxImmediateSchedulerRule;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application._common.error.model.ShareItError;
import com.qubacy.shareit.application.data.error.repository.source.local.database._common.LocalErrorDatabaseDataSource;
import com.qubacy.shareit.application.ui.activity.main.model._common.state.ShareItActivityState;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class ShareItActivityViewModelImplTest {
    @Rule
    public RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();

    private SavedStateHandle _savedStateHandleMock;
    private LocalErrorDatabaseDataSource _localErrorDatabaseSourceMock;

    @Before
    public void setup() {
        _savedStateHandleMock = mockSavedStateHandle();
        _localErrorDatabaseSourceMock = mockLocalErrorDatabaseDataSource();
    }

    private SavedStateHandle mockSavedStateHandle() {
        final SavedStateHandle savedStateHandleMock = Mockito.mock(SavedStateHandle.class);

        return savedStateHandleMock;
    }

    private LocalErrorDatabaseDataSource mockLocalErrorDatabaseDataSource() {
        final LocalErrorDatabaseDataSource localErrorDatabaseDataSourceMock =
            Mockito.mock(LocalErrorDatabaseDataSource.class);

        return localErrorDatabaseDataSourceMock;
    }

    @Test
    public void recoverStateOnCreatedTest() {
        final ShareItError error = new ShareItError(0, "test", false);
        final ShareItActivityState expectedState = new ShareItActivityState(error);

        Mockito.when(_savedStateHandleMock.contains(ShareItActivityViewModelImpl.STATE_KEY))
            .thenReturn(true);
        Mockito.when(_savedStateHandleMock.get(ShareItActivityViewModelImpl.STATE_KEY))
            .thenReturn(expectedState);

        final ShareItActivityViewModelImpl shareItActivityViewModel =
            new ShareItActivityViewModelImpl(_savedStateHandleMock, _localErrorDatabaseSourceMock);

        final ShareItActivityState gottenState = shareItActivityViewModel.getState().blockingFirst();

        Mockito.verify(_savedStateHandleMock).contains(ShareItActivityViewModelImpl.STATE_KEY);
        Mockito.verify(_savedStateHandleMock).get(ShareItActivityViewModelImpl.STATE_KEY);

        Assert.assertEquals(expectedState, gottenState);
    }

    @Test
    public void onClearedTest() throws NoSuchFieldException, IllegalAccessException {
        final Field stateControllerFieldReflection = getStateControllerFieldReflection();
        final Field errorSubscriptionFieldReflection = ShareItActivityViewModelImpl.class
            .getDeclaredField("_errorSubscription");

        stateControllerFieldReflection.setAccessible(true);
        errorSubscriptionFieldReflection.setAccessible(true);

        final ShareItError error = new ShareItError(0, "test", false);

        final ShareItActivityState expectedState = new ShareItActivityState(error);

        final ShareItActivityState[] gottenState = new ShareItActivityState[1];

        final Disposable errorSubscriptionMock = Mockito.mock(Disposable.class);

        Mockito.doAnswer((invocation -> {
            gottenState[0] = invocation.getArgument(1);

            return null;
        })).when(
            _savedStateHandleMock
        ).set(Mockito.eq(ShareItActivityViewModelImpl.STATE_KEY), Mockito.any(ShareItActivityState.class));

        final ShareItActivityViewModelImpl shareItActivityViewModel =
            new ShareItActivityViewModelImpl(_savedStateHandleMock, _localErrorDatabaseSourceMock);

        errorSubscriptionFieldReflection.set(shareItActivityViewModel, errorSubscriptionMock);

        final BehaviorSubject<ShareItActivityState> stateController =
            (BehaviorSubject<ShareItActivityState>) stateControllerFieldReflection
                .get(shareItActivityViewModel);

        stateController.onNext(expectedState);
        shareItActivityViewModel.onCleared();

        Mockito.verify(_savedStateHandleMock)
            .set(Mockito.eq(ShareItActivityViewModelImpl.STATE_KEY), Mockito.any());
        Mockito.verify(errorSubscriptionMock).dispose();

        Assert.assertEquals(expectedState, gottenState[0]);
    }

    @Test
    public void retrieveErrorTest() {
        final ErrorReference errorReference = new ErrorReference(0);
        final ShareItError error = new ShareItError(0, "test", false);

        final ShareItActivityState expectedState = new ShareItActivityState(error);

        Mockito.when(_localErrorDatabaseSourceMock.getErrorByReference(Mockito.any()))
            .thenReturn(Single.just(error));

        final ShareItActivityViewModelImpl shareItActivityViewModel =
            new ShareItActivityViewModelImpl(_savedStateHandleMock, _localErrorDatabaseSourceMock);

        shareItActivityViewModel.retrieveError(errorReference);

        final ShareItActivityState gottenState = shareItActivityViewModel.getState().blockingFirst();

        Mockito.verify(_localErrorDatabaseSourceMock).getErrorByReference(Mockito.eq(errorReference));

        Assert.assertEquals(expectedState, gottenState);
    }

    @Test
    public void absorbErrorTest() throws NoSuchFieldException, IllegalAccessException {
        final Field stateControllerFieldReflection = getStateControllerFieldReflection();

        final ShareItError error = new ShareItError(0, "test", false);

        final ShareItActivityState expectedInitState = new ShareItActivityState(error);
        final ShareItActivityState expectedFinalState = new ShareItActivityState(null);

        final ShareItActivityViewModelImpl shareItActivityViewModel =
            new ShareItActivityViewModelImpl(_savedStateHandleMock, _localErrorDatabaseSourceMock);

        final BehaviorSubject<ShareItActivityState> stateController =
            (BehaviorSubject<ShareItActivityState>) stateControllerFieldReflection
                .get(shareItActivityViewModel);

        stateController.onNext(expectedInitState);

        final ShareItActivityState gottenInitState = shareItActivityViewModel.getState()
            .blockingFirst();

        Assert.assertEquals(expectedInitState, gottenInitState);

        shareItActivityViewModel.absorbError();

        final ShareItActivityState gottenFinalState = shareItActivityViewModel.getState()
            .blockingFirst();

        Assert.assertEquals(expectedFinalState, gottenFinalState);
    }

    private Field getStateControllerFieldReflection() throws NoSuchFieldException {
        final Field stateControllerFieldReflection = ShareItActivityViewModelImpl.class
            .getDeclaredField("_stateController");

        stateControllerFieldReflection.setAccessible(true);

        return stateControllerFieldReflection;
    }
}
