package com.qubacy.shareit.application.ui.activity._common.page.idea.create;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qubacy.shareit.R;
import com.qubacy.shareit._test.util.TestUtils;
import com.qubacy.shareit.application._common.error.ErrorEnum;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.bus._di.ErrorBusModule;
import com.qubacy.shareit.application._common.error.bus._di.TestErrorBusModule;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.ui._common.validator.Validator;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create._common.data.IdeaSketch;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.IdeaCreateViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.state.IdeaCreateState;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._di.IdeaCreateViewModelFactoryModule;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._di.TestIdeaCreateViewModelModule;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.content._di.IdeaContentValidatorModule;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.content._di.TestIdeaContentValidatorModule;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.title._di.IdeaTitleValidatorModule;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.title._di.TestIdeaTitleValidatorModule;
import com.qubacy.shareit.application.ui.activity._test.util.ActivityTestUtils;

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

@HiltAndroidTest
@UninstallModules({
    IdeaCreateViewModelFactoryModule.class,
    IdeaTitleValidatorModule.class,
    IdeaContentValidatorModule.class,
    ErrorBusModule.class
})
@RunWith(AndroidJUnit4.class)
public class IdeaCreateFragmentTest {
    @Rule
    public final HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    private TestNavHostController _testNavController;

    @Before
    public void setup() {
        hiltRule.inject();

        setupNavController();
    }

    private void setupNavController() {
        _testNavController = new TestNavHostController(
            InstrumentationRegistry.getInstrumentation().getTargetContext());
        final ViewModelStore navViewModelStore = new ViewModelStore();

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            _testNavController.setViewModelStore(navViewModelStore);
            _testNavController.setGraph(R.navigation.nav_graph);
            _testNavController.setCurrentDestination(R.id.ideaCreateFragment);
        });
    }

    @Test
    public void allElementsVisibleTest() {
        launchFragment();

        Espresso.onView(withId(R.id.fragment_idea_create_topbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.fragment_idea_create_title_input))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.fragment_idea_create_content_input))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.fragment_idea_create_save_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.fragment_idea_create_cancel_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void clickingCancelLeadsToNavigatingUpTest() {
        launchFragment();

        final int expectedDestination = R.id.ideaListFragment;

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            _testNavController.setCurrentDestination(expectedDestination);
            _testNavController.navigate(R.id.action_ideaListFragment_to_ideaCreateFragment);
        });

        Espresso.onView(withId(R.id.fragment_idea_create_cancel_button))
            .perform(ViewActions.click());

        final int gottenDestination = _testNavController.getCurrentDestination().getId();

        Assert.assertEquals(expectedDestination, gottenDestination);
    }

    @Test
    public void providingWrongIdeaTitleLeadsToEmittingErrorTest() {
        launchFragment();

        final ErrorReference expectedErrorReference = new ErrorReference(
            ErrorEnum.INVALID_IDEA_DATA.id);

        final Validator ideaTitleValidator = retrieveTitleValidator();

        final ErrorBus errorBusMock = retrieveErrorBus();

        final AtomicReference<ErrorReference> gottenErrorReference = new AtomicReference<>();

        Mockito.doAnswer(invocation -> {
            gottenErrorReference.set(invocation.getArgument(0));

            return null;
        }).when(errorBusMock).emitError(Mockito.any());

        Mockito.when(ideaTitleValidator.validate(Mockito.any())).thenReturn(false);

        Espresso.onView(withId(R.id.fragment_idea_create_save_button)).perform(ViewActions.click());

        Mockito.verify(errorBusMock).emitError(Mockito.any());

        Assert.assertEquals(expectedErrorReference, gottenErrorReference.get());
    }

    @Test
    public void clickingCreateButtonWithCorrectDataLeadsToCallingViewModelMethodTest() {
        launchFragment();

        final IdeaSketch expectedIdeaSketch = new IdeaSketch("test", "test");

        final IdeaCreateViewModel ideaCreateViewModelMock = retrieveViewModelMock();

        final AtomicReference<IdeaSketch> gottenIdeaSketch = new AtomicReference<>();

        Mockito.doAnswer(invocation -> {
            gottenIdeaSketch.set(invocation.getArgument(0));

            return null;
        }).when(ideaCreateViewModelMock).createIdea(Mockito.any());

        final Validator ideaTitleValidator = retrieveTitleValidator();
        final Validator ideaContentValidator = retrieveContentValidator();

        Mockito.when(ideaTitleValidator.validate(Mockito.any())).thenReturn(true);
        Mockito.when(ideaContentValidator.validate(Mockito.any())).thenReturn(true);

        Espresso.onView(withId(R.id.fragment_idea_create_title_input))
            .perform(ViewActions.typeText(expectedIdeaSketch.title), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.fragment_idea_create_content_input))
            .perform(ViewActions.typeText(expectedIdeaSketch.content), ViewActions.closeSoftKeyboard());

        Espresso.onView(withId(R.id.fragment_idea_create_save_button)).perform(ViewActions.click());

        Mockito.verify(ideaCreateViewModelMock).createIdea(Mockito.any());

        Assert.assertEquals(expectedIdeaSketch, gottenIdeaSketch.get());
    }

    @Test
    public void settingIsCreatedStateFlagLeadsToChangingDestinationTest() {
        launchFragment();

        final int expectedDestination = R.id.ideaListFragment;
        final boolean expectedIsCreatedResult = true;

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            _testNavController.setCurrentDestination(expectedDestination);
            _testNavController.navigate(R.id.action_ideaListFragment_to_ideaCreateFragment);
        });

        final IdeaCreateState state = new IdeaCreateState(false, true);

        final BehaviorSubject<IdeaCreateState> stateController = retrieveStateController();
        final AtomicReference<SavedStateHandle> prevSavedStateHandle = new AtomicReference<>();

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            prevSavedStateHandle.set(_testNavController
                .getPreviousBackStackEntry().getSavedStateHandle());

            stateController.onNext(state);
        });

        final int gottenDestination = _testNavController.getCurrentDestination().getId();
        final boolean gottenIsCreatedResult = prevSavedStateHandle.get()
            .get(IdeaCreateFragment.IS_CREATED_RESULT_KEY);

        Assert.assertEquals(expectedDestination, gottenDestination);
        Assert.assertEquals(expectedIsCreatedResult, gottenIsCreatedResult);
    }

    @Test
    public void adjustUiWithLoadingStateTest() {
        launchFragment();

        final IdeaCreateState state = new IdeaCreateState(true, false);

        final BehaviorSubject<IdeaCreateState> stateController = retrieveStateController();

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            stateController.onNext(state);
        });

        Espresso.onView(withId(R.id.fragment_idea_create_topbar_progress_indicator))
            .check(ViewAssertions.matches(
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(withId(R.id.fragment_idea_create_title_input))
            .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()));
        Espresso.onView(withId(R.id.fragment_idea_create_content_input))
            .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()));
        Espresso.onView(withId(R.id.fragment_idea_create_save_button))
            .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()));
        Espresso.onView(withId(R.id.fragment_idea_create_cancel_button))
            .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()));
    }

    private IdeaCreateViewModel retrieveViewModelMock() {
        return TestIdeaCreateViewModelModule.instance;
    }

    private BehaviorSubject<IdeaCreateState> retrieveStateController() {
        return TestIdeaCreateViewModelModule.stateController;
    }

    private Validator retrieveTitleValidator() {
        return TestIdeaTitleValidatorModule.instance;
    }

    private Validator retrieveContentValidator() {
        return TestIdeaContentValidatorModule.instance;
    }

    private ErrorBus retrieveErrorBus() {
        return TestErrorBusModule.instance;
    }

    private void launchFragment() {
        TestUtils.launchFragmentInHiltContainer(
            IdeaCreateFragment.class, null, R.style.Theme_ShareIt, null, (activity) -> {
                ActivityTestUtils.attachNavController(activity, _testNavController);

                return null;
            });
    }
}
