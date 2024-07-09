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
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.qubacy.shareit.R;
import com.qubacy.shareit.application._common.error.ErrorEnum;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.ui._common.validator.Validator;
import com.qubacy.shareit.application.ui.activity.page._common.stateful.StatefulFragment;
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
    class Credentials {
        final String email;
        final String password;

        public Credentials(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    static String TAG = "AuthFragment";

    private FirebaseAuth _auth;

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

        _auth = FirebaseAuth.getInstance();
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

        FirebaseUser currentUser = _auth.getCurrentUser();

        if (currentUser != null) onAuthorized();
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

    private void onSignInClicked() {
        Credentials credentials = retrieveCredentials();

        if (credentials == null) return;

        launchSignIn(credentials);
    }

    private void onSignUpClicked() {
        Credentials credentials = retrieveCredentials();

        if (credentials == null) return;

        launchSignUp(credentials);
    }

    @Nullable
    private Credentials retrieveCredentials() {
        String email = _binding.fragmentAuthEmailInput.getText().toString();
        String password = _binding.fragmentAuthPasswordInput.getText().toString();

        if (!emailValidator.validate(email) || !passwordValidator.validate(password)) {
            onErrorCaught(new ErrorReference(ErrorEnum.INVALID_EMAIL_OR_PASSWORD.id, null));

            return null;
        }

        return new Credentials(email, password);
    }

    private void launchSignUp(@NotNull Credentials credentials) {
        _auth.createUserWithEmailAndPassword(credentials.email, credentials.password)
            .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        onAuthorized();
                    } else {
                        String failMessage = task.getException().getLocalizedMessage();

                        onErrorCaught(new ErrorReference(ErrorEnum.SIGN_UP_FAIL.id, failMessage));
                    }
                }
            });
    }

    private void launchSignIn(@NotNull Credentials credentials) {
        _auth.signInWithEmailAndPassword(credentials.email, credentials.password)
            .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        onAuthorized();
                    } else {
                        String failMessage = task.getException().getLocalizedMessage();

                        onErrorCaught(new ErrorReference(ErrorEnum.SIGN_IN_FAIL.id, failMessage));
                    }
                }
            });
    }

    private void onAuthorized() {
        // todo: redirect to the Ideas fragment..

        Log.d(TAG, "onAuthorized() entered;");
    }
}
