package com.qubacy.shareit.application.ui.activity.main;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.qubacy.shareit.R;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application._common.error.model.ShareItError;
import com.qubacy.shareit.application.ui.activity._common.page._common.base._common.ShareItFragment;
import com.qubacy.shareit.application.ui.activity.main.model._common.ShareItActivityViewModel;
import com.qubacy.shareit.application.ui.activity.main.model._common.state.ShareItActivityState;
import com.qubacy.shareit.application.ui.activity.main.model._di.ShareItActivityViewModelFactoryQualifier;
import com.qubacy.shareit.databinding.ActivityContainerBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class ShareItActivity extends AppCompatActivity implements ErrorBus.Listener  {
    public static final Set<Integer> TOP_LEVEL_DESTINATIONS = Set.of(
        R.id.ideaListFragment
    );

    @Inject
    @ShareItActivityViewModelFactoryQualifier
    public ViewModelProvider.Factory modelFactory;
    @NotNull
    private ShareItActivityViewModel _model;

    @Inject
    public ErrorBus errorBus;

    @NotNull
    private ActivityContainerBinding _binding;
    @Nullable
    private Disposable _stateSubscription;

    @Nullable
    private AlertDialog _errorDialog;

    private boolean _isDestroying = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        splashScreen.setOnExitAnimationListener(splashScreenViewProvider -> {
            splashScreenViewProvider.remove();
            setupEdgeToEdge();
        });

        _binding = ActivityContainerBinding.inflate(getLayoutInflater());

        setContentView(_binding.getRoot());

        _model = new ViewModelProvider(this, modelFactory).get(ShareItActivityViewModel.class);

        errorBus.setup(this);

        setupNavigationDrawer();
    }

    @Override
    protected void onStart() {
        super.onStart();

        _stateSubscription = _model.getState()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::processState);
    }

    @Override
    public void onDestroy() {
        _isDestroying = true;

        if (_errorDialog != null) _errorDialog.dismiss();
        if (_stateSubscription != null) _stateSubscription.dispose();

        errorBus.dispose();

        super.onDestroy();
    }

    public DrawerLayout getNavigationDrawerLayout() {
        return _binding.getRoot();
    }

    private void setupNavigationDrawer() {
        final NavHostFragment navHostFragment = _binding
            .activityContainerFragmentContainer.getFragment();

        NavigationUI.setupWithNavController(
            _binding.activityContainerDrawer, navHostFragment.getNavController());
    }

    private void processState(@NotNull ShareItActivityState state) {
        if (state.error != null) processError(state.error);
    }

    private void setupEdgeToEdge() {
        EdgeToEdge.enable(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getWindow().setNavigationBarContrastEnforced(false);
        }
    }

    public void processError(@NotNull ShareItError error) {
        _errorDialog = new MaterialAlertDialogBuilder(this)
            .setTitle(R.string.error_dialog_title)
            .setMessage(error.message)
            .setNeutralButton(R.string.error_dialog_neutral_button_text, (dialog, which) -> {
                dialog.dismiss();
            })
            .setOnDismissListener((dialog) -> onErrorDialogDismissed(error))
            .show();
    }

    private void onErrorDialogDismissed(@NotNull ShareItError error) {
        if (_isDestroying) return;
        if (error.isCritical) {
            finishAndRemoveTask();

            return;
        }

        postProcessError(error);
    }

    private void postProcessError(@NotNull ShareItError error) {
        _model.absorbError();

        final NavHostFragment navHostFragment = _binding.activityContainerFragmentContainer
                .getFragment();
        final ShareItFragment currentFragment = (ShareItFragment) navHostFragment
                .getChildFragmentManager().getFragments().get(0);

        currentFragment.postProcessError(error);
    }

    public void onErrorCaught(@NotNull ErrorReference errorReference) {
        _model.retrieveError(errorReference);
    }

    @Override
    public void onErrorGotten(@NotNull ErrorReference errorReference) {
        onErrorCaught(errorReference);
    }
}
