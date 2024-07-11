package com.qubacy.shareit.application.ui.activity._common.page.idea.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qubacy.shareit.application.ui.activity._common.page._common.base._common.ShareItFragment;
import com.qubacy.shareit.application.ui.activity._common.page._common.util.topbar.TopBarFragmentUtil;
import com.qubacy.shareit.application.ui.activity._common.page.idea._common.presentation.IdeaPresentation;
import com.qubacy.shareit.application.ui.activity.main.ShareItActivity;
import com.qubacy.shareit.databinding.FragmentIdeaDetailsBinding;

import org.jetbrains.annotations.NotNull;

public class IdeaDetailsFragment extends ShareItFragment {
    private FragmentIdeaDetailsBinding _binding;
    private IdeaDetailsFragmentArgs _navArgs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrieveNavArgs();
    }

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        _binding = FragmentIdeaDetailsBinding.inflate(inflater, container, false);

        return _binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupTopAppBar();
        adjustUiWithIdea(_navArgs.getIdea());
    }

    private void setupTopAppBar() {
        TopBarFragmentUtil.setupTopAppBar(
            (ShareItActivity) requireActivity(),
            this,
            _binding.fragmentIdeaDetailsTopbarToolbar
        );
    }

    private void retrieveNavArgs() {
        _navArgs = IdeaDetailsFragmentArgs.fromBundle(getArguments());
    }

    private void adjustUiWithIdea(@NotNull IdeaPresentation idea) {
        _binding.fragmentIdeaDetailsTitle.setText(idea.title);
        _binding.fragmentIdeaDetailsContent.setText(idea.content);
    }
}
