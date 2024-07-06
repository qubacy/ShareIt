package com.qubacy.shareit.application.ui.activity.page.auth.model._common.state;

import com.qubacy.shareit.application.ui._common.presentation.error.ErrorPresentation;
import com.qubacy.shareit.application.ui.activity.page._common.stateful.model.state.ShareItState;

public class AuthState extends ShareItState {
    private final boolean _isLoading;
    private final boolean _isAuthorized;

    public AuthState(
        ErrorPresentation error,
        boolean isLoading,
        boolean isAuthorized
    ) {
        super(error);

        _isLoading = isLoading;
        _isAuthorized = isAuthorized;
    }

    boolean getIsLoading() {
        return _isLoading;
    }

    boolean getIsAuthorized() {
        return _isAuthorized;
    }
}
