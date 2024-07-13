package com.qubacy.shareit.application.ui.activity._common.page.auth.model.impl;

import androidx.lifecycle.SavedStateHandle;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.qubacy.shareit.application._common.error.ErrorEnum;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.ui.activity._common.page.auth._common.data.AuthCredentials;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._common.state.AuthState;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AuthViewModelImplTest {
    private SavedStateHandle _savedStateHandleMock;
    private ErrorBus _errorBusMock;
    private FirebaseAuth _firebaseAuthMock;

    @Before
    public void setup() {
        _savedStateHandleMock = mockSavedStateHandle();
        _errorBusMock = mockErrorBus();
        _firebaseAuthMock = mockFirebaseAuth();
    }

    private SavedStateHandle mockSavedStateHandle() {
        final SavedStateHandle savedStateHandleMock = Mockito.mock(SavedStateHandle.class);

        return savedStateHandleMock;
    }

    private ErrorBus mockErrorBus() {
        final ErrorBus errorBusMock = Mockito.mock(ErrorBus.class);

        return errorBusMock;
    }

    private FirebaseAuth mockFirebaseAuth() {
        final FirebaseAuth firebaseAuthMock = Mockito.mock(FirebaseAuth.class);

        return firebaseAuthMock;
    }

    @Test
    public void onCreateTest() {
        final AuthState expectedState = new AuthState(true, false);

        Mockito.when(_savedStateHandleMock.get(Mockito.eq(AuthViewModelImpl.STATE_KEY)))
            .thenReturn(expectedState);

        final AuthViewModelImpl authViewModel = new AuthViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _firebaseAuthMock);

        final AuthState gottenState = authViewModel.getState().blockingFirst();

        Mockito.verify(_savedStateHandleMock).get(Mockito.eq(AuthViewModelImpl.STATE_KEY));
        Mockito.verify(_firebaseAuthMock).addAuthStateListener(Mockito.any());

        Assert.assertEquals(expectedState, gottenState);
    }

    @Test
    public void onClearedTest() throws NoSuchFieldException, IllegalAccessException {
        final Field stateControllerReflectionField = AuthViewModelImpl.class
            .getDeclaredField("_stateController");
        final Field authStateListenerReflectionField = AuthViewModelImpl.class
            .getDeclaredField("_authStateListener");

        stateControllerReflectionField.setAccessible(true);
        authStateListenerReflectionField.setAccessible(true);

        final AuthState expectedState = new AuthState(true, false);

        AtomicReference<AuthState> gottenState = new AtomicReference<>();

        Mockito.doAnswer((invocation) -> {
            gottenState.set(invocation.getArgument(1));

            return null;
        }).when(_savedStateHandleMock).set(Mockito.eq(AuthViewModelImpl.STATE_KEY), Mockito.any());

        final FirebaseAuth.AuthStateListener authStateListenerMock =
            Mockito.mock(FirebaseAuth.AuthStateListener.class);

        final AuthViewModelImpl authViewModel = new AuthViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _firebaseAuthMock);

        ((BehaviorSubject<AuthState>) stateControllerReflectionField.get(authViewModel))
            .onNext(expectedState);
        authStateListenerReflectionField.set(authViewModel, authStateListenerMock);

        authViewModel.onCleared();

        Mockito.verify(_savedStateHandleMock)
            .set(Mockito.eq(AuthViewModelImpl.STATE_KEY), Mockito.any());
        Mockito.verify(_firebaseAuthMock).removeAuthStateListener(Mockito.any());

        Assert.assertEquals(expectedState, gottenState.get());
    }

    @Test
    public void checkAuthTest() {
        final AuthState expectedState = new AuthState(true, false);

        final FirebaseUser firebaseUserMock = Mockito.mock(FirebaseUser.class);

        Mockito.when(_firebaseAuthMock.getCurrentUser()).thenReturn(firebaseUserMock);

        final AuthViewModelImpl authViewModel = new AuthViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _firebaseAuthMock);

        authViewModel.checkAuth();

        final AuthState gottenState = authViewModel.getState().blockingFirst();

        Mockito.verify(_firebaseAuthMock).getCurrentUser();

        Assert.assertEquals(expectedState, gottenState);
    }

    @Test
    public void signInTest() {
        final AuthCredentials authCredentials = new AuthCredentials("test", "test");

        final AuthState expectedState = new AuthState(false, true);

        final Task<AuthResult> taskMock = Mockito.mock(Task.class);

        Mockito.when(_firebaseAuthMock.signInWithEmailAndPassword(
            Mockito.anyString(), Mockito.anyString())
        ).thenReturn(taskMock);

        final AuthViewModelImpl authViewModel = new AuthViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _firebaseAuthMock);

        authViewModel.signIn(authCredentials);

        final AuthState gottenState = authViewModel.getState().blockingFirst();

        Mockito.verify(_firebaseAuthMock).signInWithEmailAndPassword(
            Mockito.anyString(), Mockito.anyString());

        Assert.assertEquals(expectedState, gottenState);
    }

    @Test
    public void onSignInCompleteTest()
        throws NoSuchMethodException,
               InvocationTargetException,
               IllegalAccessException
    {
        final Method onSignInCompleteMethodReflection = AuthViewModelImpl.class
            .getDeclaredMethod("onSignInComplete", Task.class);

        onSignInCompleteMethodReflection.setAccessible(true);

        final String failMessage = "test";

        final AuthState expectedState = new AuthState(false, false);
        final ErrorReference expectedErrorReference =
            new ErrorReference(ErrorEnum.SIGN_IN_FAIL.id, failMessage);

        final Exception authExceptionMock = Mockito.mock(Exception.class);

        Mockito.when(authExceptionMock.getLocalizedMessage()).thenReturn(failMessage);

        final Task<AuthResult> authTaskMock = Mockito.mock(Task.class);

        Mockito.when(authTaskMock.isSuccessful()).thenReturn(false);
        Mockito.when(authTaskMock.getException()).thenReturn(authExceptionMock);

        AtomicReference<ErrorReference> gottenErrorReference = new AtomicReference<>();

        Mockito.doAnswer((invocation -> {
            gottenErrorReference.set(invocation.getArgument(0));

            return null;
        })).when(_errorBusMock).emitError(Mockito.any());

        final AuthViewModelImpl authViewModel = new AuthViewModelImpl(
            _savedStateHandleMock, _errorBusMock, _firebaseAuthMock);

        onSignInCompleteMethodReflection.invoke(authViewModel, authTaskMock);

        final AuthState gottenState = authViewModel.getState().blockingFirst();

        Mockito.verify(authTaskMock).getException();
        Mockito.verify(authExceptionMock).getLocalizedMessage();

        Assert.assertEquals(expectedState, gottenState);
        Assert.assertEquals(expectedErrorReference, gottenErrorReference.get());
    }

    @Test
    public void signUpTest() {

    }

    @Test
    public void onSignUpCompleteTest() {

    }

    @Test
    public void logoutTest() {

    }
}
