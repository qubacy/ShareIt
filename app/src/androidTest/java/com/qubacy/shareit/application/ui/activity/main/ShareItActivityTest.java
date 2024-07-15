package com.qubacy.shareit.application.ui.activity.main;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qubacy.shareit.R;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.bus._di.ErrorBusModule;
import com.qubacy.shareit.application._common.error.bus._di.TestErrorBusModule;
import com.qubacy.shareit.application._common.error.model.ShareItError;
import com.qubacy.shareit.application.ui.activity.main.model._common.ShareItActivityViewModel;
import com.qubacy.shareit.application.ui.activity.main.model._common.state.ShareItActivityState;
import com.qubacy.shareit.application.ui.activity.main.model._di.ShareItActivityViewModelFactoryModule;
import com.qubacy.shareit.application.ui.activity.main.model._di.TestShareItActivityViewModelModule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@HiltAndroidTest
@UninstallModules({ShareItActivityViewModelFactoryModule.class, ErrorBusModule.class})
@RunWith(AndroidJUnit4.class)
public class ShareItActivityTest {
    @Rule
    public final HiltAndroidRule hiltRule = new HiltAndroidRule(this);
    @Rule
    public final ActivityScenarioRule<ShareItActivity> activityScenarioRule =
        new ActivityScenarioRule(ShareItActivity.class);

    private ActivityScenario<ShareItActivity> _activityScenario;

    @Before
    public void setup() {
        _activityScenario = activityScenarioRule.getScenario();
    }

    @Test
    public void errorBusSetUpOnCreationTest() {
        final ErrorBus errorBusMock = retrieveErrorBus();

        _activityScenario.moveToState(Lifecycle.State.CREATED);

        Mockito.verify(errorBusMock).setup(Mockito.any());
    }

    @Test
    public void errorBusDisposedOnDestructionTest() {
        final ErrorBus errorBusMock = retrieveErrorBus();

        _activityScenario.moveToState(Lifecycle.State.DESTROYED);

        Mockito.verify(errorBusMock).dispose();
    }

    @Test
    public void errorDialogShownOnErrorStateTest() {
        final BehaviorSubject<ShareItActivityState> stateController = retrieveStateController();

        final ShareItError error = new ShareItError(0, "test", false);
        final ShareItActivityState state = new ShareItActivityState(error);

        _activityScenario.moveToState(Lifecycle.State.RESUMED);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            stateController.onNext(state);
        });

        Espresso.onView(withText(error.message))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void appShutdownOnCriticalErrorDismissedTest() throws InterruptedException {
        final BehaviorSubject<ShareItActivityState> stateController = retrieveStateController();

        final ShareItError error = new ShareItError(0, "test", true);
        final ShareItActivityState state = new ShareItActivityState(error);

        _activityScenario.moveToState(Lifecycle.State.RESUMED);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            stateController.onNext(state);
        });

        Espresso.onView(withText(R.string.error_dialog_neutral_button_text))
            .perform(ViewActions.click());

        Thread.sleep(1000); // todo: come up with a better idea;

        Assert.assertEquals(Lifecycle.State.DESTROYED, _activityScenario.getState());
    }

    private ShareItActivityViewModel retrieveViewModelMock() {
        return TestShareItActivityViewModelModule.instance;
    }

    private BehaviorSubject<ShareItActivityState> retrieveStateController() {
        return TestShareItActivityViewModelModule.stateController;
    }

    private ErrorBus retrieveErrorBus() {
        return TestErrorBusModule.instance;
    }
}
