package com.qubacy.shareit.application.ui.activity._common.page.auth.model.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.qubacy.shareit.application._common.error.ErrorEnum;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.ui.activity._common.page.auth._common.data.AuthCredentials;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._common.AuthViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._common.state.AuthState;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AuthViewModelImpl extends AuthViewModel {
    static String STATE_KEY = "state";

    @NotNull
    private final BehaviorSubject<AuthState> _stateController;
    @NotNull
    private final SavedStateHandle _store;
    @NotNull
    private final ErrorBus _errorBus;
    @NotNull
    private final FirebaseAuth _auth;

    @NotNull
    private FirebaseAuth.AuthStateListener _authStateListener;

    public AuthViewModelImpl(
        @NotNull SavedStateHandle store,
        @NotNull ErrorBus errorBus,
        @NotNull FirebaseAuth firebaseAuth
    ) {
        super();

        _stateController = BehaviorSubject
            .createDefault(new AuthState(false, false));
        _store = store;
        _errorBus = errorBus;
        _auth = firebaseAuth;

        restoreState();
        setupAuthStateListener();
    }

    @Override
    protected void onCleared() {
        preserveState();

        _auth.removeAuthStateListener(_authStateListener);

        super.onCleared();
    }

    private void restoreState() {
        AuthState state = _store.get(STATE_KEY);

        if (state == null) return;

        _stateController.onNext(state);
    }

    private void preserveState() {
        AuthState lastState = _stateController.getValue();

        _store.set(STATE_KEY, lastState);
    }

    private void setupAuthStateListener() {
        _authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final boolean isSignedIn = firebaseAuth.getCurrentUser() != null;

                _stateController.onNext(new AuthState(isSignedIn, false));
            }
        };

        _auth.addAuthStateListener(_authStateListener);
    }

    @Override
    public void checkAuth() {
        FirebaseUser currentUser = _auth.getCurrentUser();

        if (currentUser != null)
            _stateController.onNext(new AuthState(true, false));
    }

    @Override
    public @NotNull Observable<AuthState> getState() {
        return _stateController;
    }

    @Override
    public void signIn(@NotNull AuthCredentials authCredentials) {
        _stateController.onNext(new AuthState(false, true));

        _auth.signInWithEmailAndPassword(authCredentials.email, authCredentials.password)
            .addOnCompleteListener(this::onSignInComplete);
    }

    @Override
    public void signUp(@NotNull AuthCredentials authCredentials) {
        _stateController.onNext(new AuthState(false, true));

        _auth.createUserWithEmailAndPassword(authCredentials.email, authCredentials.password)
            .addOnCompleteListener(this::onSignUpComplete);
    }

    @Override
    public void logout() {
        _auth.signOut();
    }

    private void onSignInComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) return;

        String failMessage = task.getException().getLocalizedMessage();

        _stateController.onNext(new AuthState(false, false));
        _errorBus.emitError(new ErrorReference(ErrorEnum.SIGN_IN_FAIL.id, failMessage));
    }

    private void onSignUpComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) return;

        String failMessage = task.getException().getLocalizedMessage();

        _stateController.onNext(new AuthState(false, false));
        _errorBus.emitError(new ErrorReference(ErrorEnum.SIGN_UP_FAIL.id, failMessage));
    }
}
