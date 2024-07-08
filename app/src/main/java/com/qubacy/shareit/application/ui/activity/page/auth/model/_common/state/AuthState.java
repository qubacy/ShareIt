package com.qubacy.shareit.application.ui.activity.page.auth.model._common.state;

import com.qubacy.shareit.application.ui.activity.page._common.stateful.model.state.ShareItState;

public class AuthState extends ShareItState {
    public final boolean isLoading;
    public final boolean isAuthorized;

    public AuthState(
        boolean isLoading,
        boolean isAuthorized
    ) {
        this.isLoading = isLoading;
        this.isAuthorized = isAuthorized;
    }
}
