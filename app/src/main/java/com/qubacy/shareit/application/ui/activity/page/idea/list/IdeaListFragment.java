package com.qubacy.shareit.application.ui.activity.page.idea.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.qubacy.shareit.application.ui.activity.ShareItActivity;
import com.qubacy.shareit.application.ui.activity.page._common.stateful.StatefulFragment;
import com.qubacy.shareit.application.ui.activity.page.idea._common.presentation.IdeaPresentation;
import com.qubacy.shareit.application.ui.activity.page.idea.list.component.list.adapter.IdeaListRecyclerViewAdapter;
import com.qubacy.shareit.application.ui.activity.page.idea.list.model._common.IdeaListViewModel;
import com.qubacy.shareit.application.ui.activity.page.idea.list.model._common.state.IdeaListState;
import com.qubacy.shareit.application.ui.activity.page.idea.list.model._di.IdeaListViewModelFactoryQualifier;
import com.qubacy.shareit.databinding.FragmentIdeaListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IdeaListFragment extends StatefulFragment<IdeaListState, IdeaListViewModel> {
    @IdeaListViewModelFactoryQualifier
    @Inject
    public ViewModelProvider.Factory modelFactory;
    private IdeaListViewModel _model;

    @NotNull
    private FragmentIdeaListBinding _binding;
    @NotNull
    private OnBackPressedCallback _backPressedCallback;

    @NotNull
    private IdeaListRecyclerViewAdapter _listAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _model = new ViewModelProvider(this, modelFactory).get(IdeaListViewModel.class);
        _backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {}
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(_backPressedCallback);
    }

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        _binding = FragmentIdeaListBinding.inflate(inflater, container, false);

        return _binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupTopAppBar();
        setupList();
    }

    @Override
    public void onDestroy() {
        _backPressedCallback.remove();

        super.onDestroy();
    }

    private void setupTopAppBar() {
        final Toolbar toolbar = _binding.fragmentIdeasTopbarToolbar;
        final DrawerLayout drawerLayout = ((ShareItActivity) requireActivity())
            .getNavigationDrawerLayout();
        final AppBarConfiguration appBarConfiguration = new AppBarConfiguration
            .Builder(ShareItActivity.TOP_LEVEL_DESTINATIONS)
            .setOpenableLayout(drawerLayout)
            .build();

        NavigationUI.setupWithNavController(
            toolbar, NavHostFragment.findNavController(this), appBarConfiguration);
    }

    private void setupList() {
        _listAdapter = new IdeaListRecyclerViewAdapter();

        _binding.fragmentIdeasList.setAdapter(_listAdapter);
    }

    @Override
    protected IdeaListViewModel getModel() {
        return _model;
    }

    @Override
    protected void processState(@NotNull IdeaListState state) {
        super.processState(state);

        if (state.ideas != null) _listAdapter.submitList(state.ideas);

        adjustUiWithLoadingState(state.isLoading);
    }

    private void adjustUiWithLoadingState(boolean isLoading) {
        _binding.fragmentIdeasTopbarProgressIndicator
            .setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }
}
