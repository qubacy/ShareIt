package com.qubacy.shareit.application.ui.activity.page.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.qubacy.shareit.application.ui.activity.page._common.stateful.StatefulFragment;
import com.qubacy.shareit.application.ui.activity.page.auth.model._common.AuthViewModel;
import com.qubacy.shareit.application.ui.activity.page.auth.model._common.state.AuthState;
import com.qubacy.shareit.application.ui.activity.page.auth.model._di.module.AuthViewModelFactoryQualifier;
import com.qubacy.shareit.databinding.FragmentAuthBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AuthFragment extends StatefulFragment<AuthState, AuthViewModel> {
    @AuthViewModelFactoryQualifier
    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    private AuthViewModel _viewModel;
    private FragmentAuthBinding _binding;

    @Override
    protected AuthViewModel getModel() {
        return _viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _viewModel = new ViewModelProvider(this, viewModelFactory)
            .get(AuthViewModel.class);
    }

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
