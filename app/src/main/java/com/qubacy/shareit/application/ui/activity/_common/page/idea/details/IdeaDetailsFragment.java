package com.qubacy.shareit.application.ui.activity._common.page.idea.details;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.transition.MaterialContainerTransform;
import com.qubacy.shareit.R;
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
        setupTransitions();
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

    private void setupTransitions() {
        final MaterialContainerTransform materialContainerTransform =
            new MaterialContainerTransform(requireContext(), true);

        final int transitionDuration = requireContext().getResources()
            .getInteger(R.integer.page_transition_animation_duration);

        materialContainerTransform.setScrimColor(Color.TRANSPARENT);
        materialContainerTransform.setDuration(transitionDuration);
        materialContainerTransform.setInterpolator(new AccelerateDecelerateInterpolator());

        setSharedElementEnterTransition(materialContainerTransform);
    }

    private void setupTopAppBar() {
        final AppCompatActivity activity = (AppCompatActivity) requireActivity();

        if (!(activity instanceof ShareItActivity shareItActivity)) return;

        TopBarFragmentUtil.setupTopAppBar(
            shareItActivity,
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
