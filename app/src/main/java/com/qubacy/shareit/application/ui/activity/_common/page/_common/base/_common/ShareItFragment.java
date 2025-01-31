package com.qubacy.shareit.application.ui.activity._common.page._common.base._common;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application._common.error.model.ShareItError;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public abstract class ShareItFragment extends Fragment {
    @Inject
    public ErrorBus errorBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        super.onStop();
    }

    protected void onErrorCaught(@NotNull ErrorReference errorReference) {
        errorBus.emitError(errorReference);
    }

    /**
     * Should be override in order to handle the error AFTER the dialog is closed;
     * @param error
     */
    public void postProcessError(@NotNull ShareItError error) { }
}
