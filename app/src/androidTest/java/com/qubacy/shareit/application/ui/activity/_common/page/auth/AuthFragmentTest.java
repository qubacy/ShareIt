package com.qubacy.shareit.application.ui.activity._common.page.auth;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.Bundle;

import androidx.navigation.testing.TestNavHostController;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qubacy.shareit.R;
import com.qubacy.shareit._test.util.TestUtils;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.bus._di.ErrorBusModule;
import com.qubacy.shareit.application._common.error.bus._di.TestErrorBusModule;
import com.qubacy.shareit.application.ui._common.validator.Validator;
import com.qubacy.shareit.application.ui.activity._common.page._test.util.FragmentTestUtils;
import com.qubacy.shareit.application.ui.activity._common.page.auth._common.data.AuthCredentials;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._common.AuthViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._common.state.AuthState;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._di.TestAuthViewModelModule;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._di.module.AuthViewModelFactoryModule;
import com.qubacy.shareit.application.ui.activity._common.page.auth.validator.email._di.EmailValidatorModule;
import com.qubacy.shareit.application.ui.activity._common.page.auth.validator.email._di.TestEmailValidatorModule;
import com.qubacy.shareit.application.ui.activity._common.page.auth.validator.password._di.PasswordValidatorModule;
import com.qubacy.shareit.application.ui.activity._common.page.auth.validator.password._di.TestPasswordValidatorModule;

import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicReference;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@RunWith(AndroidJUnit4.class)
@HiltAndroidTest
@UninstallModules({
    AuthViewModelFactoryModule.class,
    EmailValidatorModule.class,
    PasswordValidatorModule.class,
    ErrorBusModule.class
})
public class AuthFragmentTest {
    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    private TestNavHostController _testNavController;

    @Before
    public void setup() {
        hiltRule.inject();

        setupNavController();
    }

    private void setupNavController() {
        _testNavController = new TestNavHostController(
            InstrumentationRegistry.getInstrumentation().getTargetContext());

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
           _testNavController.setGraph(R.navigation.nav_graph);
           _testNavController.setCurrentDestination(R.id.authFragment);
        });
    }

    @Test
    public void allElementsVisibleTest() {
        launchFragment(null);

        Espresso.onView(withId(R.id.fragment_auth_header_logo))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.fragment_auth_email_input))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.fragment_auth_password_input))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.fragment_auth_sign_in_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.fragment_auth_sign_up_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void controlsBlockedOnLoadingTest() throws InterruptedException {
        final AuthState state = new AuthState(false, true);

        launchFragment(null);

        final BehaviorSubject<AuthState> stateController = retrieveStateController();

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            stateController.onNext(state);
        });

        Espresso.onView(withId(R.id.fragment_auth_email_input))
                .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()));
        Espresso.onView(withId(R.id.fragment_auth_password_input))
                .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()));
        Espresso.onView(withId(R.id.fragment_auth_sign_in_button))
                .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()));
        Espresso.onView(withId(R.id.fragment_auth_sign_up_button))
                .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()));
    }

    @Test
    public void authStatusIsCheckedOnFragmentLaunchingTest() {
        launchFragment(null);

        final AuthViewModel authViewModelMock = retrieveViewModelMock();

        Mockito.verify(authViewModelMock).checkAuth();
    }

    @Test
    public void wrongEmailDuringSigningInLeadsToErrorRetrievalTest() {
        launchFragment(null);

        final Validator emailValidatorMock = retrieveEmailValidator();
        final ErrorBus errorBusMock = retrieveErrorBus();

        Mockito.when(emailValidatorMock.validate(Mockito.anyString())).thenReturn(false);

        Espresso.onView(withId(R.id.fragment_auth_sign_in_button)).perform(ViewActions.click());

        Mockito.verify(errorBusMock).emitError(Mockito.any());
    }

    @Test
    public void clickingSignInWithValidDataLeadsToCallingViewModelMethodTest() {
        launchFragment(null);

        final AuthViewModel authViewModelMock = retrieveViewModelMock();

        final AtomicReference<AuthCredentials> gottenCredentials = new AtomicReference<>();

        Mockito.doAnswer(invocation -> {
            gottenCredentials.set(invocation.getArgument(0));

            return null;
        }).when(authViewModelMock).signIn(Mockito.any());

        final Validator emailValidatorMock = retrieveEmailValidator();
        final Validator passwordValidatorMock = retrievePasswordValidator();

        Mockito.when(emailValidatorMock.validate(Mockito.anyString())).thenReturn(true);
        Mockito.when(passwordValidatorMock.validate(Mockito.anyString())).thenReturn(true);

        final AuthCredentials expectedCredentials = new AuthCredentials("test", "test");

        Espresso.onView(withId(R.id.fragment_auth_email_input))
            .perform(ViewActions.typeText(expectedCredentials.email), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.fragment_auth_password_input))
            .perform(ViewActions.typeText(expectedCredentials.password), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.fragment_auth_sign_in_button)).perform(ViewActions.click());

        Mockito.verify(authViewModelMock).signIn(Mockito.any());

        Assert.assertEquals(expectedCredentials, gottenCredentials.get());
    }

    @Test
    public void clickingSignUpWithValidDataLeadsToCallingViewModelMethodTest() {
        launchFragment(null);

        final AuthViewModel authViewModelMock = retrieveViewModelMock();

        final AtomicReference<AuthCredentials> gottenCredentials = new AtomicReference<>();

        Mockito.doAnswer(invocation -> {
            gottenCredentials.set(invocation.getArgument(0));

            return null;
        }).when(authViewModelMock).signUp(Mockito.any());

        final Validator emailValidatorMock = retrieveEmailValidator();
        final Validator passwordValidatorMock = retrievePasswordValidator();

        Mockito.when(emailValidatorMock.validate(Mockito.anyString())).thenReturn(true);
        Mockito.when(passwordValidatorMock.validate(Mockito.anyString())).thenReturn(true);

        final AuthCredentials expectedCredentials = new AuthCredentials("test", "test");

        Espresso.onView(withId(R.id.fragment_auth_email_input))
                .perform(ViewActions.typeText(expectedCredentials.email), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.fragment_auth_password_input))
                .perform(ViewActions.typeText(expectedCredentials.password), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.fragment_auth_sign_up_button)).perform(ViewActions.click());

        Mockito.verify(authViewModelMock).signUp(Mockito.any());

        Assert.assertEquals(expectedCredentials, gottenCredentials.get());
    }

    @Test
    public void enteringFragmentWithLogoutFlagLeadsToCallingViewModelMethodTest() {
        final Bundle args = new Bundle();

        args.putBoolean("logout", true);

        launchFragment(args);

        final AuthViewModel authViewModelMock = retrieveViewModelMock();

        Mockito.verify(authViewModelMock).logout();
    }

    @Test
    public void successfulAuthorizationLeadsToChangingDestinationTest() {
        final AuthState state = new AuthState(true, false);

        final int expectedDestination = R.id.ideaListFragment;

        launchFragment(null);

        final BehaviorSubject<AuthState> stateController = retrieveStateController();

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            stateController.onNext(state);
        });

        final int gottenDestination = _testNavController.getCurrentDestination().getId();

        Assert.assertEquals(expectedDestination, gottenDestination);
    }

    private AuthViewModel retrieveViewModelMock() {
        return TestAuthViewModelModule.instance;
    }

    private BehaviorSubject<AuthState> retrieveStateController() {
        return TestAuthViewModelModule.stateController;
    }

    private Validator retrieveEmailValidator() {
        return TestEmailValidatorModule.instance;
    }

    private Validator retrievePasswordValidator() {
        return TestPasswordValidatorModule.instance;
    }

    private ErrorBus retrieveErrorBus() {
        return TestErrorBusModule.instance;
    }

    private void launchFragment(@Nullable Bundle args) {
        final Bundle finalArgs = args == null ? new Bundle() : args;

        TestUtils.launchFragmentInHiltContainer(
            AuthFragment.class, finalArgs, R.style.Theme_ShareIt, (fragment) -> {
                FragmentTestUtils.attachNavController(fragment, _testNavController);

                return null;
            }, null);
    }
}
