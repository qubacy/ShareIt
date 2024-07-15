package com.qubacy.shareit.application.ui.activity._common.page.idea.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.MaterialElevationScale;
import com.qubacy.shareit.NavGraphDirections;
import com.qubacy.shareit.R;
import com.qubacy.shareit.application.ui.activity._common.page._common.base.stateful.StatefulFragment;
import com.qubacy.shareit.application.ui.activity._common.page._common.util.navigation.NavigationFragmentUtil;
import com.qubacy.shareit.application.ui.activity._common.page._common.util.topbar.TopBarFragmentUtil;
import com.qubacy.shareit.application.ui.activity._common.page.idea._common.presentation.IdeaPresentation;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.IdeaCreateFragment;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.component.list.adapter.IdeaListRecyclerViewAdapter;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._common.IdeaListViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._common.state.IdeaListState;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._di.IdeaListViewModelFactoryQualifier;
import com.qubacy.shareit.application.ui.activity.main.ShareItActivity;
import com.qubacy.shareit.databinding.FragmentIdeaListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IdeaListFragment
    extends StatefulFragment<IdeaListState, IdeaListViewModel>
    implements IdeaListRecyclerViewAdapter.Callback
{
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

    @Nullable
    private View _lastIdeaDetailsView = null;

    private Integer _initAppBarLayoutTopPadding = null;
    private Integer _initListBottomPadding = null;

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
        setupNavDrawer();
        setupList();
        setupControls();
        setupInsetListeners();
    }

    @Override
    public void onResume() {
        super.onResume();

        checkIdeaCreateResult();
        resetLastIdeaDetailsView();

        // todo: does it matter actually? check it twice:
        requireView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                _model.getRecentIdeas();
                requireView().getViewTreeObserver().removeOnPreDrawListener(this);

                return true;
            }
        });
        resetToDetailsTransitions();
    }

    @Override
    public void onPause() {
        _model.pause();

        super.onPause();
    }

    @Override
    public void onDestroy() {
        _backPressedCallback.remove();

        super.onDestroy();
    }

    private void setupNavDrawer() {
        final AppCompatActivity activity = (AppCompatActivity) requireActivity();

        if (!(activity instanceof ShareItActivity shareItActivity)) return;

        final Fragment fragmentRef = this;
        final NavigationView navigationView = shareItActivity.getNavigationDrawer();
        final DrawerLayout drawerLayout = shareItActivity.getNavigationDrawerLayout();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() != R.id.drawer_common_logout) return false;

                final NavGraphDirections.ActionGlobalAuthFragment logoutAction =
                    NavGraphDirections.actionGlobalAuthFragment();

                logoutAction.setLogout(true);

                NavHostFragment.findNavController(fragmentRef).navigate(logoutAction);
                drawerLayout.close();

                return true;
            }
        });
    }

    private void setupToDetailsTransitions() {
        final MaterialElevationScale exitTransition = new MaterialElevationScale(false);
        final MaterialElevationScale reenterTransition = new MaterialElevationScale(true);

        final int transitionDuration = requireContext().getResources()
            .getInteger(R.integer.page_transition_animation_duration);

        exitTransition.setDuration(transitionDuration);
        exitTransition.setInterpolator(new AccelerateDecelerateInterpolator());
        reenterTransition.setDuration(transitionDuration);
        reenterTransition.setInterpolator(new AccelerateDecelerateInterpolator());

        setExitTransition(exitTransition);
        setReenterTransition(reenterTransition);
    }

    private void resetToDetailsTransitions() {
        setExitTransition(null);
        setReenterTransition(null);
    }

    private void setupInsetListeners() {
        ViewCompat.setOnApplyWindowInsetsListener(_binding.fragmentIdeasTopbar, (v, insets) -> {
            if (_initAppBarLayoutTopPadding == null) _initAppBarLayoutTopPadding = v.getPaddingTop();

            final int statusBarTopInset = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
            final int paddingTop = _initAppBarLayoutTopPadding + statusBarTopInset;

            v.setPadding(v.getPaddingLeft(), paddingTop, v.getPaddingRight(), v.getPaddingBottom());

            return insets;
        });
        ViewCompat.setOnApplyWindowInsetsListener(_binding.fragmentIdeasList, (v, insets) -> {
            if (_initListBottomPadding == null) _initListBottomPadding = v.getPaddingTop();

            final int navigationBarsBottomInset = insets
                .getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
            final int paddingBottom = _initListBottomPadding + navigationBarsBottomInset;

            v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), paddingBottom);

            return insets;
        });
    }

    private void resetLastIdeaDetailsView() {
        if (_lastIdeaDetailsView == null) return;

        _lastIdeaDetailsView.setTransitionName(null);
    }

    private void checkIdeaCreateResult() {
        Object isIdeaCreated = NavigationFragmentUtil
            .getPrevDestinationResult(this, IdeaCreateFragment.IS_CREATED_RESULT_KEY);

        if (isIdeaCreated == null || !((boolean) isIdeaCreated)) return;

        Snackbar.make(
            _binding.getRoot(),
            R.string.fragment_idea_list_idea_created_snackbar_text,
            Snackbar.LENGTH_LONG
        ).show();
    }

    private void setupTopAppBar() {
        final AppCompatActivity activity = (AppCompatActivity) requireActivity();

        if (!(activity instanceof ShareItActivity shareItActivity)) return;

        TopBarFragmentUtil.setupTopAppBar(
            shareItActivity,
            this,
            _binding.fragmentIdeasTopbarToolbar
        );
    }

    private void setupList() {
        _listAdapter = new IdeaListRecyclerViewAdapter(this);

        final RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        _binding.fragmentIdeasList.addItemDecoration(
            new MaterialDividerItemDecoration(
                requireContext(), MaterialDividerItemDecoration.VERTICAL
            )
        );
        _binding.fragmentIdeasList.setAdapter(_listAdapter);
        _binding.fragmentIdeasList.setItemAnimator(itemAnimator);
    }

    private void setupControls() {
        _binding.fragmentIdeasListFab.setOnClickListener(v -> onFabClicked());
    }

    private void onFabClicked() {
        final NavDirections action = IdeaListFragmentDirections
            .actionIdeaListFragmentToIdeaCreateFragment();

        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    protected IdeaListViewModel getModel() {
        return _model;
    }

    @Override
    protected void processState(@NotNull IdeaListState state) {
        super.processState(state);

        if (state.ideas != null) pushIdeasToList(state.ideas);

        adjustUiWithLoadingState(state.isLoading);
    }

    private void pushIdeasToList(@NotNull List<IdeaPresentation> ideas) {
        _listAdapter.submitList(ideas, () -> {
            if (_binding.fragmentIdeasList.getIsAtBeginning())
                _binding.fragmentIdeasList.scrollToPosition(0);
        });
    }

    private void adjustUiWithLoadingState(boolean isLoading) {
        _binding.fragmentIdeasTopbarProgressIndicator
            .setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onIdeaClicked(@NotNull IdeaPresentation idea, @NotNull View view) {
        setupToDetailsTransitions();

        final String transitionName = getString(
            R.string.fragment_idea_list_shared_idea_transition_name);

        _lastIdeaDetailsView = view;
        _lastIdeaDetailsView.setTransitionName(transitionName);

        final NavDirections action = IdeaListFragmentDirections
            .actionIdeaListFragmentToIdeaDetailsFragment(idea);
        final FragmentNavigator.Extras extras = new FragmentNavigator.Extras(
            Map.of(view, transitionName)
        );

        NavHostFragment.findNavController(this).navigate(action, extras);
    }
}
