package com.qubacy.shareit.application.ui.activity.page.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.qubacy.shareit.R;
import com.qubacy.shareit.application._common.error.ErrorEnum;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.ui._common.validator.Validator;
import com.qubacy.shareit.application.ui.activity.page._common.base.stateful.StatefulFragment;
import com.qubacy.shareit.application.ui.activity.page.auth._common.data.AuthCredentials;
import com.qubacy.shareit.application.ui.activity.page.auth.model._common.AuthViewModel;
import com.qubacy.shareit.application.ui.activity.page.auth.model._common.state.AuthState;
import com.qubacy.shareit.application.ui.activity.page.auth.model._di.module.AuthViewModelFactoryQualifier;
import com.qubacy.shareit.application.ui.activity.page.auth.validator.email._di.EmailValidatorQualifier;
import com.qubacy.shareit.application.ui.activity.page.auth.validator.password._di.PasswordValidatorQualifier;
import com.qubacy.shareit.databinding.FragmentAuthBinding;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AuthFragment extends StatefulFragment<AuthState, AuthViewModel> {
    static String TAG = "AuthFragment";

    @AuthViewModelFactoryQualifier
    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @EmailValidatorQualifier
    @Inject
    public Validator emailValidator;
    @PasswordValidatorQualifier
    @Inject
    public Validator passwordValidator;

    private AuthViewModel _viewModel;
    private FragmentAuthBinding _binding;

    private AnimatedVectorDrawableCompat _animatedHeaderIcon;
    private AnimatedVectorDrawableCompat _animatedHeaderBackground;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupHeader();
        setupControls();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setupHeader() {
        setupHeaderLogo();
        setupHeaderDrawables();
        setupHeaderAnimation(_binding.getRoot());
    }

    private void setupHeaderLogo() {
        _binding.fragmentAuthHeaderLogo.setAlpha(0);
        _binding.fragmentAuthHeaderLogo.setTranslationY(-40);
    }

    private void setupHeaderAnimation(View rootView) {
        rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                launchHeaderAnimation();
                rootView.getViewTreeObserver().removeOnPreDrawListener(this);

                return false;
            }
        });
    }

    private void setupHeaderDrawables() {
        _animatedHeaderIcon = AnimatedVectorDrawableCompat
            .create(requireContext(), R.drawable.header_icon_animated);
        _animatedHeaderBackground = AnimatedVectorDrawableCompat
            .create(requireContext(), R.drawable.header_icon_background_animated);

        _binding.fragmentAuthHeaderIcon.setImageDrawable(_animatedHeaderIcon);
        _binding.fragmentAuthHeader.setBackground(_animatedHeaderBackground);
    }

    private void launchHeaderAnimation() {
        _animatedHeaderBackground.registerAnimationCallback(
            new Animatable2Compat.AnimationCallback() {
                @Override
                public void onAnimationEnd(Drawable drawable) {
                    super.onAnimationEnd(drawable);

                    _animatedHeaderIcon.start();
                    launchHeaderLogoAnimation();
                }
            }
        );
        _animatedHeaderBackground.start();
    }

    private void launchHeaderLogoAnimation() {
        _binding.fragmentAuthHeaderLogo.animate()
            .alpha(1)
            .translationY(0)
            .setDuration(300)
            .setInterpolator(new AccelerateInterpolator())
            .start();
    }

    private void setupControls() {
        _binding.fragmentAuthSignInButton.setOnClickListener((view) -> onSignInClicked());
        _binding.fragmentAuthSignUpButton.setOnClickListener((view) -> onSignUpClicked());
    }

    private void setControlsEnabled(boolean areEnabled) {
        _binding.fragmentAuthEmailInput.setEnabled(areEnabled);
        _binding.fragmentAuthPasswordInput.setEnabled(areEnabled);
        _binding.fragmentAuthSignInButton.setEnabled(areEnabled);
        _binding.fragmentAuthSignUpButton.setEnabled(areEnabled);
    }

    private void onSignInClicked() {
        AuthCredentials credentials = retrieveCredentials();

        if (credentials == null) return;

        _viewModel.signIn(credentials);
    }

    private void onSignUpClicked() {
        AuthCredentials credentials = retrieveCredentials();

        if (credentials == null) return;

        _viewModel.signUp(credentials);
    }

    @Nullable
    private AuthCredentials retrieveCredentials() {
        String email = _binding.fragmentAuthEmailInput.getText().toString();
        String password = _binding.fragmentAuthPasswordInput.getText().toString();

        if (!emailValidator.validate(email) || !passwordValidator.validate(password)) {
            onErrorCaught(new ErrorReference(ErrorEnum.INVALID_EMAIL_OR_PASSWORD.id, null));

            return null;
        }

        return new AuthCredentials(email, password);
    }

    @Override
    protected void processState(@NotNull AuthState state) {
        super.processState(state);

        if (state.isAuthorized) onAuthorized();

        adjustUiWithLoadingState(state.isLoading);
    }

    private void adjustUiWithLoadingState(boolean isLoading) {
        setControlsEnabled(!isLoading);
    }

    private void onAuthorized() {
        Log.d(TAG, "onAuthorized() entered;");

        final NavDirections action =
            AuthFragmentDirections.actionAuthFragmentToIdeaListFragment();

        NavHostFragment.findNavController(this).navigate(action);
    }
}
