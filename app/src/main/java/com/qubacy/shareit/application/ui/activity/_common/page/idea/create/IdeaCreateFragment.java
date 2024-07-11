package com.qubacy.shareit.application.ui.activity._common.page.idea.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.qubacy.shareit.application.ui.activity._common.page._common.base.stateful.StatefulFragment;
import com.qubacy.shareit.application.ui.activity._common.page._common.util.topbar.TopBarFragmentUtil;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.IdeaCreateViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.state.IdeaCreateState;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._di.IdeaCreateViewModelFactoryQualifier;
import com.qubacy.shareit.application.ui.activity.main.ShareItActivity;
import com.qubacy.shareit.databinding.FragmentIdeaCreateBinding;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IdeaCreateFragment extends StatefulFragment<IdeaCreateState, IdeaCreateViewModel> {
    @IdeaCreateViewModelFactoryQualifier
    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    @NotNull
    private IdeaCreateViewModel _model;

    @NotNull
    private FragmentIdeaCreateBinding _binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _model = new ViewModelProvider(this, viewModelFactory).get(IdeaCreateViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        _binding = FragmentIdeaCreateBinding.inflate(inflater, container, false);

        return _binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupTopAppBar();
    }

    @Override
    protected IdeaCreateViewModel getModel() {
        return _model;
    }

    private void setupTopAppBar() {
        TopBarFragmentUtil.setupTopAppBar(
            (ShareItActivity) requireActivity(),
            this,
            _binding.fragmentIdeaCreateTopbarToolbar
        );
    }
}
