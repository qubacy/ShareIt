package com.qubacy.shareit.application.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.qubacy.shareit.R;
import com.qubacy.shareit.application._common.error.handler.ErrorHandler;
import com.qubacy.shareit.application._common.error.model.ShareItError;
import com.qubacy.shareit.application.ui.activity.page._common.base.ShareItFragment;
import com.qubacy.shareit.databinding.ActivityContainerBinding;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShareItActivity extends AppCompatActivity implements ErrorHandler.Callback  {
    static String ERROR_KEY = "error";

    private ActivityContainerBinding _binding;

    private ShareItError _error;
    private AlertDialog _errorDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ErrorHandler(this));

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        splashScreen.setOnExitAnimationListener(splashScreenViewProvider -> {
            splashScreenViewProvider.remove();
            setupEdgeToEdge();
        });

        _binding = ActivityContainerBinding.inflate(getLayoutInflater());

        setContentView(_binding.getRoot());

        if (savedInstanceState != null) restoreState(savedInstanceState);
        if (_error != null) processError(_error);
    }

    private void restoreState(@NotNull Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(ERROR_KEY))
            _error = savedInstanceState.getParcelable(ERROR_KEY);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (_error != null) outState.putParcelable(ERROR_KEY, _error);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        if (_errorDialog != null) _errorDialog.dismiss();

        super.onDestroy();
    }

    private void setupEdgeToEdge() {
        EdgeToEdge.enable(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getWindow().setNavigationBarContrastEnforced(false);
        }
    }

    public void processError(@NotNull ShareItError error) {
        _error = error;
        _errorDialog = new AlertDialog.Builder(this)
            .setTitle(R.string.error_dialog_title)
            .setMessage(error.message)
            .setNeutralButton(R.string.error_dialog_neutral_button_text, (dialog, which) -> {
                dialog.dismiss();
            })
            .setOnDismissListener((dialog) -> onErrorDialogDismissed())
            .show();
    }

    private void onErrorDialogDismissed() {
        ShareItFragment currentFragment = _binding.activityContainerFragmentContainer.getFragment();

        currentFragment.postProcessError(_error);
    }

    @Override
    public void onErrorCaught(@NotNull ShareItError error) {
        processError(error);
    }
}
