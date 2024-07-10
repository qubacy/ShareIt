package com.qubacy.shareit.application.ui.activity.page.auth.model.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.qubacy.shareit.application._common.error.ErrorEnum;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.ui.activity.page.auth._common.data.AuthCredentials;
import com.qubacy.shareit.application.ui.activity.page.auth.model._common.AuthViewModel;
import com.qubacy.shareit.application.ui.activity.page.auth.model._common.state.AuthState;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AuthViewModelImpl extends AuthViewModel {
    static String STATE_KEY = "state";

    private final BehaviorSubject<AuthState> _stateController;
    private final SavedStateHandle _store;
    private final ErrorBus _errorBus;

    private final FirebaseAuth _auth;

    public AuthViewModelImpl(
        @NotNull SavedStateHandle store,
        @NotNull ErrorBus errorBus
    ) {
        super();

        _stateController = BehaviorSubject
            .createDefault(new AuthState(false, false));
        _store = store;
        _errorBus = errorBus;

        _auth = FirebaseAuth.getInstance();

        restoreState();
        checkAuth();
    }

    @Override
    protected void onCleared() {
        preserveState();

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

    private void checkAuth() {
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
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    boolean isAuthorized = false;

                    if (task.isSuccessful()) {
                        isAuthorized = true;
                    } else {
                        String failMessage = task.getException().getLocalizedMessage();

                        _errorBus.emitError(new ErrorReference(ErrorEnum.SIGN_UP_FAIL.id, failMessage));
                    }

                    _stateController.onNext(new AuthState(isAuthorized, false));
                }
            });
    }

    @Override
    public void signUp(@NotNull AuthCredentials authCredentials) {
        _stateController.onNext(new AuthState(false, true));

        _auth.createUserWithEmailAndPassword(authCredentials.email, authCredentials.password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    boolean isAuthorized = false;

                    if (task.isSuccessful()) {
                        isAuthorized = true;
                    } else {
                        String failMessage = task.getException().getLocalizedMessage();

                        _errorBus.emitError(new ErrorReference(ErrorEnum.SIGN_UP_FAIL.id, failMessage));
                    }

                    _stateController.onNext(new AuthState(isAuthorized, false));
                }
            });
    }
}
