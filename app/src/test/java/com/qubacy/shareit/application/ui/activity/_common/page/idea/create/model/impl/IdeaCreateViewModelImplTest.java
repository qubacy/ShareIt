package com.qubacy.shareit.application.ui.activity._common.page.idea.create.model.impl;

import androidx.lifecycle.SavedStateHandle;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.qubacy.shareit.application._common.error.ErrorEnum;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create._common.data.IdeaSketch;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.state.IdeaCreateState;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class IdeaCreateViewModelImplTest {
    private SavedStateHandle _savedStateHandleMock;
    private ErrorBus _errorBusMock;
    private DatabaseReference _databaseMock;

    @Before
    public void setup() {
        _savedStateHandleMock = mockSavedStateHandle();
        _errorBusMock = mockErrorBus();
        _databaseMock = mockDatabaseReference();
    }

    private SavedStateHandle mockSavedStateHandle() {
        final SavedStateHandle savedStateHandleMock = Mockito.mock(SavedStateHandle.class);

        return savedStateHandleMock;
    }

    private ErrorBus mockErrorBus() {
        final ErrorBus errorBusMock = Mockito.mock(ErrorBus.class);

        return errorBusMock;
    }

    private DatabaseReference mockDatabaseReference() {
        final DatabaseReference databaseReferenceMock = Mockito.mock(DatabaseReference.class);

        return databaseReferenceMock;
    }

    @Test
    public void onCreatedTest() {
        final IdeaCreateState expectedState = new IdeaCreateState(false, true);

        Mockito.when(_savedStateHandleMock.get(Mockito.eq(IdeaCreateViewModelImpl.STATE_KEY)))
            .thenReturn(expectedState);

        final IdeaCreateViewModelImpl ideaCreateViewModel = new IdeaCreateViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _databaseMock);

        final IdeaCreateState gottenState = ideaCreateViewModel.getState().blockingFirst();

        Mockito.verify(_savedStateHandleMock).get(Mockito.eq(IdeaCreateViewModelImpl.STATE_KEY));

        Assert.assertEquals(expectedState, gottenState);
    }

    @Test
    public void onClearedTest() throws NoSuchFieldException, IllegalAccessException {
        final Field stateControllerFieldReflection = IdeaCreateViewModelImpl.class
            .getDeclaredField("_stateController");

        stateControllerFieldReflection.setAccessible(true);

        final IdeaCreateState expectedState = new IdeaCreateState(false, true);

        final AtomicReference<IdeaCreateState> gottenState = new AtomicReference<>();

        Mockito.doAnswer((invocation -> {
            gottenState.set(invocation.getArgument(1));

            return null;
        })).when(
            _savedStateHandleMock
        ).set(Mockito.eq(IdeaCreateViewModelImpl.STATE_KEY), Mockito.any());

        final IdeaCreateViewModelImpl ideaCreateViewModel = new IdeaCreateViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _databaseMock);

        ((BehaviorSubject<IdeaCreateState>) stateControllerFieldReflection.get(ideaCreateViewModel))
            .onNext(expectedState);

        ideaCreateViewModel.onCleared();

        Mockito.verify(_savedStateHandleMock)
            .set(Mockito.eq(IdeaCreateViewModelImpl.STATE_KEY), Mockito.any());

        Assert.assertEquals(expectedState, gottenState.get());
    }

    @Test
    public void createIdeaTest() {
        final IdeaSketch ideaSketch = new IdeaSketch("test", "test");
        final String uid = "test";

        final IdeaCreateState expectedState = new IdeaCreateState(true, false);

        final DatabaseReference databaseReferenceMock = Mockito.mock(DatabaseReference.class);

        Mockito.when(databaseReferenceMock.getKey()).thenReturn(uid);

        final Task<Void> taskMock = Mockito.mock(Task.class);

        Mockito.when(taskMock.addOnSuccessListener(Mockito.any())).thenReturn(taskMock);

        Mockito.when(_databaseMock.push()).thenReturn(databaseReferenceMock);
        Mockito.when(_databaseMock.updateChildren(Mockito.any())).thenReturn(taskMock);

        final IdeaCreateViewModelImpl ideaCreateViewModel = new IdeaCreateViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _databaseMock);

        ideaCreateViewModel.createIdea(ideaSketch);

        final IdeaCreateState gottenState = ideaCreateViewModel.getState().blockingFirst();

        Mockito.verify(_databaseMock).push();
        Mockito.verify(_databaseMock).updateChildren(Mockito.any());
        Mockito.verify(taskMock).addOnSuccessListener(Mockito.any());
        Mockito.verify(taskMock).addOnFailureListener(Mockito.any());

        Assert.assertEquals(expectedState, gottenState);
    }

    @Test
    public void onIdeaCreatedTest()
        throws NoSuchMethodException,
               InvocationTargetException,
               IllegalAccessException
    {
        final Method onIdeaCreatedMethodReflection = IdeaCreateViewModelImpl.class
            .getDeclaredMethod("onIdeaCreated");

        onIdeaCreatedMethodReflection.setAccessible(true);

        final IdeaCreateState expectedState = new IdeaCreateState(false, true);

        final IdeaCreateViewModelImpl ideaCreateViewModel = new IdeaCreateViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _databaseMock);

        onIdeaCreatedMethodReflection.invoke(ideaCreateViewModel);

        final IdeaCreateState gottenState = ideaCreateViewModel.getState().blockingFirst();

        Assert.assertEquals(expectedState, gottenState);
    }

    @Test
    public void onIdeaCreatingFailedTest()
        throws NoSuchMethodException,
               InvocationTargetException,
               IllegalAccessException
    {
        final Method onIdeaCreatingFailedMethodReflection = IdeaCreateViewModelImpl.class
            .getDeclaredMethod("onIdeaCreatingFailed", Exception.class);

        onIdeaCreatingFailedMethodReflection.setAccessible(true);

        final Exception exception = new Exception("test");

        final ErrorReference expectedErrorReference = new ErrorReference(
            ErrorEnum.DATABASE_FAIL.id, exception.getMessage());
        final IdeaCreateState expectedState = new IdeaCreateState(false, false);

        final AtomicReference<ErrorReference> gottenErrorReference = new AtomicReference<>();

        Mockito.doAnswer((invocation -> {
            gottenErrorReference.set(invocation.getArgument(0));

            return null;
        })).when(_errorBusMock).emitError(Mockito.any());

        final IdeaCreateViewModelImpl ideaCreateViewModel = new IdeaCreateViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _databaseMock);

        onIdeaCreatingFailedMethodReflection.invoke(ideaCreateViewModel, exception);

        final IdeaCreateState gottenState = ideaCreateViewModel.getState().blockingFirst();

        Mockito.verify(_errorBusMock).emitError(Mockito.any());

        Assert.assertEquals(expectedErrorReference, gottenErrorReference.get());
        Assert.assertEquals(expectedState, gottenState);
    }
}

