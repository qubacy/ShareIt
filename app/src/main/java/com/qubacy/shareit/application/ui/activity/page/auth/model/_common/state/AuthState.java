package com.qubacy.shareit.application.ui.activity.page.auth.model._common.state;

import com.qubacy.shareit.application._common.error.model.ShareItError;
import com.qubacy.shareit.application.ui.activity.page._common.stateful.model.state.ShareItState;

import org.jetbrains.annotations.Nullable;

public class AuthState extends ShareItState {
    public final boolean isLoading;
    public final boolean isAuthorized;

    public AuthState(
        @Nullable ShareItError error,
        boolean isLoading,
        boolean isAuthorized
    ) {
        super(error);

        this.isLoading = isLoading;
        this.isAuthorized = isAuthorized;
    }
}
