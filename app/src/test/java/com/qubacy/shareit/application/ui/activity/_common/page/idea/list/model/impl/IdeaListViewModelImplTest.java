package com.qubacy.shareit.application.ui.activity._common.page.idea.list.model.impl;

import androidx.lifecycle.SavedStateHandle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.qubacy.shareit.application._common.error.ErrorEnum;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.ui.activity._common.page.idea._common.presentation.IdeaPresentation;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._common.state.IdeaListState;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class IdeaListViewModelImplTest {
    private SavedStateHandle _savedStateHandleMock;
    private ErrorBus _errorBusMock;
    private DatabaseReference _databaseMock;

    @Before
    public void setup() {
        _savedStateHandleMock = mockSavedStateHandle();
        _errorBusMock = mockErrorBus();
        _databaseMock = mockDatabase();
    }

    private SavedStateHandle mockSavedStateHandle() {
        final SavedStateHandle savedStateHandleMock = Mockito.mock(SavedStateHandle.class);

        return savedStateHandleMock;
    }

    private ErrorBus mockErrorBus() {
        final ErrorBus errorBusMock = Mockito.mock(ErrorBus.class);

        return errorBusMock;
    }

    private DatabaseReference mockDatabase() {
        final DatabaseReference databaseMock = Mockito.mock(DatabaseReference.class);

        return databaseMock;
    }

    @Test
    public void onCreatedTest() {
        final IdeaListState expectedState = new IdeaListState(
            List.of(new IdeaPresentation()), false);

        Mockito.when(_savedStateHandleMock.get(Mockito.eq(IdeaListViewModelImpl.STATE_KEY)))
            .thenReturn(expectedState);

        final IdeaListViewModelImpl ideaListViewModel = new IdeaListViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _databaseMock);

        final IdeaListState gottenState = ideaListViewModel.getState().blockingFirst();

        Mockito.verify(_savedStateHandleMock).get(Mockito.eq(IdeaListViewModelImpl.STATE_KEY));

        Assert.assertEquals(expectedState, gottenState);
    }

    @Test
    public void onClearedTest() throws NoSuchFieldException, IllegalAccessException {
        final Field stateControllerFieldReflection = IdeaListViewModelImpl.class
            .getDeclaredField("_stateController");
        final Field recentIdeasListenerFieldReflection = IdeaListViewModelImpl.class
            .getDeclaredField("_recentIdeasListener");

        stateControllerFieldReflection.setAccessible(true);
        recentIdeasListenerFieldReflection.setAccessible(true);

        final IdeaListState expectedState = new IdeaListState(List.of(), false);

        final AtomicReference<IdeaListState> gottenState = new AtomicReference<>();

        Mockito.doAnswer(invocation -> {
            gottenState.set(invocation.getArgument(1));

            return null;
        }).when(_savedStateHandleMock).set(Mockito.eq(IdeaListViewModelImpl.STATE_KEY), Mockito.any());

        final ValueEventListener recentIdeasListenerMock = Mockito.mock(ValueEventListener.class);

        final IdeaListViewModelImpl ideaListViewModel = new IdeaListViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _databaseMock);

        ((BehaviorSubject<IdeaListState>) stateControllerFieldReflection.get(ideaListViewModel))
            .onNext(expectedState);
        recentIdeasListenerFieldReflection.set(ideaListViewModel, recentIdeasListenerMock);

        ideaListViewModel.onCleared();

        Mockito.verify(_savedStateHandleMock)
            .set(Mockito.eq(IdeaListViewModelImpl.STATE_KEY), Mockito.any());
        Mockito.verify(_databaseMock).removeEventListener(Mockito.any(ValueEventListener.class));

        Assert.assertEquals(expectedState, gottenState.get());
    }

    @Test
    public void getRecentIdeasTest() {
        final IdeaListState expectedState = new IdeaListState(null, true);

        final Query queryMock = Mockito.mock(Query.class);

        Mockito.when(_databaseMock.child(Mockito.anyString())).thenReturn(_databaseMock);
        Mockito.when(_databaseMock.limitToLast(Mockito.anyInt())).thenReturn(queryMock);

        final IdeaListViewModelImpl ideaListViewModel = new IdeaListViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _databaseMock);

        ideaListViewModel.getRecentIdeas();

        final IdeaListState gottenState = ideaListViewModel.getState().blockingFirst();

        Mockito.verify(_databaseMock).child(Mockito.anyString());
        Mockito.verify(_databaseMock).limitToLast(Mockito.anyInt());
        Mockito.verify(queryMock).addValueEventListener(Mockito.any());

        Assert.assertEquals(expectedState, gottenState);
    }

    @Test
    public void onIdeasGottenTest()
        throws NoSuchMethodException,
               InvocationTargetException,
               IllegalAccessException
    {
        final Method onIdeasGottenMethodReflection = IdeaListViewModelImpl.class
            .getDeclaredMethod("onIdeasGotten", DataSnapshot.class);

        onIdeasGottenMethodReflection.setAccessible(true);

        final IdeaPresentation receivedIdeaPresentation =
            new IdeaPresentation("test", "test", "test");

        final DataSnapshot ideaDataSnapshotMock = Mockito.mock(DataSnapshot.class);

        Mockito.when(ideaDataSnapshotMock.getValue(Mockito.any(Class.class)))
            .thenReturn(receivedIdeaPresentation);

        final List<DataSnapshot> dataSnapshotIdeas = List.of(ideaDataSnapshotMock);

        final DataSnapshot dataSnapshotMock = Mockito.mock(DataSnapshot.class);

        Mockito.when(dataSnapshotMock.getChildren()).thenReturn(dataSnapshotIdeas);

        final IdeaListState expectedState = new IdeaListState(
            List.of(receivedIdeaPresentation), false);

        final IdeaListViewModelImpl ideaListViewModel = new IdeaListViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _databaseMock);

        onIdeasGottenMethodReflection.invoke(ideaListViewModel, dataSnapshotMock);

        final IdeaListState gottenState = ideaListViewModel.getState().blockingFirst();

        Mockito.verify(dataSnapshotMock).getChildren();
        Mockito.verify(ideaDataSnapshotMock).getValue(Mockito.any(Class.class));

        Assert.assertEquals(expectedState, gottenState);
    }

    @Test
    public void onDatabaseErrorGottenTest()
        throws NoSuchMethodException,
               InvocationTargetException,
               IllegalAccessException
    {
        final Method onDatabaseErrorGottenMethodReflection = IdeaListViewModelImpl.class
            .getDeclaredMethod("onDatabaseErrorGotten", DatabaseError.class);

        onDatabaseErrorGottenMethodReflection.setAccessible(true);

        final int databaseErrorId = 0;
        final String databaseErrorMessage = "test";

        final IdeaListState expectedState = new IdeaListState(null, false);
        final ErrorReference expectedErrorReference = new ErrorReference(
            ErrorEnum.DATABASE_FAIL.id, databaseErrorMessage);

        final DatabaseError databaseErrorMock = Mockito.mock(DatabaseError.class);

        Mockito.when(databaseErrorMock.getCode()).thenReturn(databaseErrorId);
        Mockito.when(databaseErrorMock.getMessage()).thenReturn(databaseErrorMessage);

        final AtomicReference<ErrorReference> gottenErrorReference = new AtomicReference<>();

        Mockito.doAnswer(invocation -> {
            gottenErrorReference.set(invocation.getArgument(0));

            return null;
        }).when(_errorBusMock).emitError(Mockito.any());

        final IdeaListViewModelImpl ideaListViewModel = new IdeaListViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _databaseMock);

        onDatabaseErrorGottenMethodReflection.invoke(ideaListViewModel, databaseErrorMock);

        final IdeaListState gottenState = ideaListViewModel.getState().blockingFirst();

        Mockito.verify(databaseErrorMock).getCode();
        Mockito.verify(databaseErrorMock).getMessage();
        Mockito.verify(_errorBusMock).emitError(Mockito.any());

        Assert.assertEquals(expectedState, gottenState);
        Assert.assertEquals(expectedErrorReference, gottenErrorReference.get());
    }

    @Test
    public void pauseTest() throws NoSuchFieldException, IllegalAccessException {
        final Field recentIdeasListenerFieldReflection = IdeaListViewModelImpl.class
            .getDeclaredField("_recentIdeasListener");

        recentIdeasListenerFieldReflection.setAccessible(true);

        final ValueEventListener recentIdeasListenerMock = Mockito.mock(ValueEventListener.class);

        Mockito.when(_databaseMock.child(Mockito.anyString())).thenReturn(_databaseMock);

        final IdeaListViewModelImpl ideaListViewModel = new IdeaListViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _databaseMock);

        recentIdeasListenerFieldReflection.set(ideaListViewModel, recentIdeasListenerMock);

        ideaListViewModel.pause();

        Mockito.verify(_databaseMock).child(Mockito.anyString());
        Mockito.verify(_databaseMock).removeEventListener(Mockito.any(ValueEventListener.class));
    }
}
