package com.qubacy.shareit.application.ui.activity.auth;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.qubacy.shareit.application.ui.activity._common.ShareItActivity;
import com.qubacy.shareit.databinding.ActivityAuthBinding;

public class AuthActivity extends ShareItActivity {
    private ActivityAuthBinding _binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _binding = ActivityAuthBinding.inflate(getLayoutInflater());

        setContentView(_binding.getRoot());
    }
}
