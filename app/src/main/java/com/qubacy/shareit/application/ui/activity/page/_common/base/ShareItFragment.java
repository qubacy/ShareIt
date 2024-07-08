package com.qubacy.shareit.application.ui.activity.page._common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qubacy.shareit.application._common.error.model.ShareItError;

import org.jetbrains.annotations.NotNull;

public abstract class ShareItFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Should be override in order to handle the error AFTER the dialog is closed;
     * @param error
     */
    public void postProcessError(@NotNull ShareItError error) { }
}
