package com.qubacy.shareit.application.ui.activity.page.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qubacy.shareit.application.ui.activity.page._common.base.ShareItFragment;
import com.qubacy.shareit.databinding.FragmentAuthBinding;

public class AuthFragment extends ShareItFragment {
    private FragmentAuthBinding _binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        _binding = FragmentAuthBinding.inflate(inflater, container, false);

        return _binding.getRoot();
    }
}
