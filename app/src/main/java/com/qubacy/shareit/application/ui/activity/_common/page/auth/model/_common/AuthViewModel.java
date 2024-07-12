package com.qubacy.shareit.application.ui.activity._common.page.auth.model._common;

import com.qubacy.shareit.application.ui.activity._common.model.base.ShareItViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.auth._common.data.AuthCredentials;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._common.state.AuthState;

import org.jetbrains.annotations.NotNull;

public abstract class AuthViewModel extends ShareItViewModel<AuthState> {
    public abstract void checkAuth();
    public abstract void signIn(@NotNull AuthCredentials authCredentials);
    public abstract void signUp(@NotNull AuthCredentials authCredentials);
    public abstract void logout();
}
