package com.qubacy.shareit.application.ui.activity.page.auth.model._common.state;

import com.qubacy.shareit.application.ui.activity.page._common.stateful.model.state.ShareItState;

public class AuthState extends ShareItState {
    public final boolean isLoading;

    public AuthState(
        boolean isLoading
    ) {
        this.isLoading = isLoading;
    }
}
