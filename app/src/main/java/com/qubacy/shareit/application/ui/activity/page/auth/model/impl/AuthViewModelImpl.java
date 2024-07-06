package com.qubacy.shareit.application.ui.activity.page.auth.model.impl;

import androidx.lifecycle.SavedStateHandle;

import com.qubacy.shareit.application.ui.activity.page.auth.model._common.AuthViewModel;
import com.qubacy.shareit.application.ui.activity.page.auth.model._common.data.SignInData;
import com.qubacy.shareit.application.ui.activity.page.auth.model._common.data.SignUpData;
import com.qubacy.shareit.application.ui.activity.page.auth.model._common.state.AuthState;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@HiltViewModel
public class AuthViewModelImpl extends AuthViewModel {
    static String STATE_KEY = "state";

    private BehaviorSubject<AuthState> _stateSubject;
    private final SavedStateHandle _store;

    @Inject
    public AuthViewModelImpl(
        SavedStateHandle store
    ) {
        super();

        _stateSubject = BehaviorSubject.createDefault(
            new AuthState(null, false, false)
        );
        _store = store;

        restoreState();
    }

    @Override
    protected void onCleared() {
        preserveState();

        super.onCleared();
    }

    private void restoreState() {
        AuthState state = _store.get(STATE_KEY);

        if (state == null) return;

        _stateSubject.onNext(state);
    }

    private void preserveState() {
        AuthState lastState = _stateSubject.getValue();

        _store.set(STATE_KEY, lastState);
    }

    @Override
    public void signIn(@NotNull SignInData data) {
        // todo: implement..


    }

    @Override
    public void signUp(@NotNull SignUpData data) {
        // todo: implement..


    }

    @Override
    public @NotNull Observable<AuthState> getState() {
        return _stateSubject;
    }
}