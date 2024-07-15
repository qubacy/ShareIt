package com.qubacy.shareit.application.ui.activity._common.page.idea.list;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qubacy.shareit.R;
import com.qubacy.shareit._test.util.TestUtils;
import com.qubacy.shareit.application.ui.activity._common.page.idea._common.presentation.IdeaPresentation;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.IdeaCreateFragment;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._common.IdeaListViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._common.state.IdeaListState;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._di.IdeaListViewModelFactoryModule;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._di.TestIdeaListViewModelModule;
import com.qubacy.shareit.application.ui.activity._test.util.ActivityTestUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.List;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@HiltAndroidTest
@UninstallModules({IdeaListViewModelFactoryModule.class})
@RunWith(AndroidJUnit4.class)
public class IdeaListFragmentTest {
    @Rule
    public final HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    private TestNavHostController _testNavController;
    private ViewModelStore _navViewModelStore;
    private SavedStateHandle _navSavedStateHandle;

    @Before
    public void setup() {
        hiltRule.inject();

        setupNavController();
    }

    private void setupNavController() {
        _testNavController = new TestNavHostController(
            InstrumentationRegistry.getInstrumentation().getTargetContext());
        _navViewModelStore = new ViewModelStore();

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            _testNavController.setViewModelStore(_navViewModelStore);
            _testNavController.setGraph(R.navigation.nav_graph);
            _testNavController.setCurrentDestination(R.id.ideaListFragment);

            _navSavedStateHandle = _testNavController.getCurrentBackStackEntry()
                .getSavedStateHandle();
        });
    }

    @Test
    public void allElementsVisibleTest() {
        launchFragment();

        Espresso.onView(withId(R.id.fragment_ideas_topbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.fragment_ideas_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.fragment_ideas_list_fab))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void enteringFragmentWithCreatedFlagLeadsToShowingSnackbarTest() {
        _navSavedStateHandle.set(IdeaCreateFragment.IS_CREATED_RESULT_KEY, true);

        launchFragment();

        Espresso.onView(withText(
            R.string.fragment_idea_list_idea_created_snackbar_text
        )).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void gettingRecentIdeasOnPreDrawTest() {
        launchFragment();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        final IdeaListViewModel ideaListViewModelMock = retrieveViewModelMock();

        Mockito.verify(ideaListViewModelMock).getRecentIdeas();
    }

    @Test
    public void enteringPausedStateLeadsToCallingViewModelMethodTest() {
        final ActivityScenario activityScenario = launchFragment();

        final IdeaListViewModel ideaListViewModelMock = retrieveViewModelMock();

        activityScenario.moveToState(Lifecycle.State.DESTROYED);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        Mockito.verify(ideaListViewModelMock).pause();
    }

    @Test
    public void clickingFabLeadsToNewDestinationTest() {
        launchFragment();

        final int expectedDestination = R.id.ideaCreateFragment;

        Espresso.onView(withId(R.id.fragment_ideas_list_fab)).perform(ViewActions.click());

        final int gottenDestination = _testNavController.getCurrentDestination().getId();

        Assert.assertEquals(expectedDestination, gottenDestination);
    }

    @Test
    public void clickingIdeaLeadsToNewDestinationTest() {
        final IdeaPresentation ideaPresentation = new IdeaPresentation(
            "test", "test title", "test content");
        final List<IdeaPresentation> ideaPresentationList = List.of(ideaPresentation);
        final IdeaListState ideaListState = new IdeaListState(ideaPresentationList, false);

        final int expectedDestination = R.id.ideaDetailsFragment;

        launchFragment();

        final BehaviorSubject<IdeaListState> stateController = retrieveStateController();

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            stateController.onNext(ideaListState);
        });

        Espresso.onView(withText(ideaPresentation.title)).perform(ViewActions.click());

        final int gottenDestination = _testNavController.getCurrentDestination().getId();

        Assert.assertEquals(expectedDestination, gottenDestination);
    }

    private IdeaListViewModel retrieveViewModelMock() {
        return TestIdeaListViewModelModule.instance;
    }

    private BehaviorSubject<IdeaListState> retrieveStateController() {
        return TestIdeaListViewModelModule.stateController;
    }

    private ActivityScenario<AppCompatActivity> launchFragment() {
        final Bundle args = new Bundle();

        return TestUtils.launchFragmentInHiltContainer(
            IdeaListFragment.class, args, R.style.Theme_ShareIt, null, (activity) -> {
                ActivityTestUtils.attachNavController(activity, _testNavController);

                return null;
            });
    }
}
