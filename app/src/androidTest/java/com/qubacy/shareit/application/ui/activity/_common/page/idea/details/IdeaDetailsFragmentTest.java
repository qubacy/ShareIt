package com.qubacy.shareit.application.ui.activity._common.page.idea.details;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.qubacy.shareit.R;
import com.qubacy.shareit._test.util.TestUtils;
import com.qubacy.shareit.application.ui.activity._common.page.idea._common.presentation.IdeaPresentation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
public class IdeaDetailsFragmentTest {
    @Rule
    public final HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Before
    public void setup() {
        hiltRule.inject();
    }

    @Test
    public void allElementsVisibleTest() {
        final IdeaPresentation ideaPresentation = new IdeaPresentation(
            "test", "test title", "test content");

        launchFragment(ideaPresentation);

        Espresso.onView(withId(R.id.fragment_idea_details_topbar_toolbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.fragment_idea_details_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.fragment_idea_details_content))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Should be reconsidered and supplemented:
     */
    @Test
    public void largeIdeaScrollableTest() {
        final IdeaPresentation ideaPresentation = new IdeaPresentation(
        "test", "test title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Pellentesque nec nam aliquam sem. Arcu ac tortor dignissim convallis aenean et. Diam maecenas ultricies mi eget. Massa tincidunt dui ut ornare lectus sit amet est placerat. Aliquam sem et tortor consequat id porta nibh. Odio morbi quis commodo odio aenean sed adipiscing. In est ante in nibh. Condimentum vitae sapien pellentesque habitant morbi tristique senectus et netus. Placerat vestibulum lectus mauris ultrices eros in cursus turpis massa.\n" +
            "\n" +
            "At tempor commodo ullamcorper a. Tellus in hac habitasse platea dictumst vestibulum rhoncus est pellentesque. Urna nec tincidunt praesent semper feugiat. Euismod quis viverra nibh cras pulvinar mattis nunc sed. Nibh praesent tristique magna sit amet purus gravida quis. Tristique risus nec feugiat in fermentum posuere urna. Non consectetur a erat nam at lectus urna duis. Libero justo laoreet sit amet cursus sit amet dictum. Donec enim diam vulputate ut pharetra. Laoreet non curabitur gravida arcu. Penatibus et magnis dis parturient montes. Adipiscing elit ut aliquam purus sit amet luctus venenatis.\n" +
            "\n" +
            "Neque sodales ut etiam sit. Lectus sit amet est placerat in. Risus quis varius quam quisque id diam vel quam elementum. Egestas integer eget aliquet nibh praesent tristique magna. Diam in arcu cursus euismod quis viverra nibh. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Elementum nibh tellus molestie nunc non blandit massa enim. Et molestie ac feugiat sed lectus vestibulum mattis ullamcorper velit. Sit amet dictum sit amet. Mattis aliquam faucibus purus in massa tempor nec feugiat. Neque sodales ut etiam sit. Arcu non sodales neque sodales ut. Faucibus vitae aliquet nec ullamcorper sit amet. Hac habitasse platea dictumst vestibulum rhoncus est pellentesque elit ullamcorper. Eu non diam phasellus vestibulum lorem sed risus ultricies. Scelerisque in dictum non consectetur a erat nam at.\n" +
            "\n" +
            "Scelerisque varius morbi enim nunc faucibus a pellentesque sit amet. Suspendisse ultrices gravida dictum fusce ut placerat orci. Commodo ullamcorper a lacus vestibulum sed arcu. Ultrices tincidunt arcu non sodales. Pellentesque sit amet porttitor eget dolor. Magna fringilla urna porttitor rhoncus. Ac tortor vitae purus faucibus ornare suspendisse sed. In nulla posuere sollicitudin aliquam ultrices sagittis orci. Turpis massa sed elementum tempus. Velit aliquet sagittis id consectetur purus. In fermentum et sollicitudin ac orci phasellus. Netus et malesuada fames ac. Aenean euismod elementum nisi quis eleifend quam. Suscipit adipiscing bibendum est ultricies integer quis. Morbi blandit cursus risus at ultrices mi tempus. Dui accumsan sit amet nulla facilisi morbi tempus iaculis. Arcu ac tortor dignissim convallis aenean. Ornare arcu dui vivamus arcu felis bibendum. Semper auctor neque vitae tempus quam pellentesque nec nam.\n" +
            "\n" +
            "Quis risus sed vulputate odio ut enim blandit. A cras semper auctor neque vitae. Pellentesque habitant morbi tristique senectus et. Porttitor rhoncus dolor purus non enim praesent. Sed risus pretium quam vulputate dignissim suspendisse in. Penatibus et magnis dis parturient montes nascetur ridiculus. Ultricies integer quis auctor elit sed. Pulvinar elementum integer enim neque volutpat ac tincidunt. Mattis molestie a iaculis at erat pellentesque adipiscing commodo elit. Magna sit amet purus gravida quis blandit turpis cursus in. At elementum eu facilisis sed odio morbi. Donec ultrices tincidunt arcu non sodales neque. At in tellus integer feugiat scelerisque varius. Lacus sed viverra tellus in hac habitasse. Mi sit amet mauris commodo. Sed lectus vestibulum mattis ullamcorper velit sed. Nibh tellus molestie nunc non blandit massa. Nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet. Elementum integer enim neque volutpat ac tincidunt vitae semper.\n" +
            "\n" +
            "Rhoncus dolor purus non enim praesent elementum facilisis. Massa ultricies mi quis hendrerit dolor. Amet mattis vulputate enim nulla aliquet porttitor lacus luctus. Facilisis gravida neque convallis a cras. Eleifend mi in nulla posuere sollicitudin aliquam ultrices. Suspendisse in est ante in. Amet risus nullam eget felis eget. Aliquam purus sit amet luctus venenatis lectus magna fringilla urna. Ullamcorper eget nulla facilisi etiam dignissim. Porta nibh venenatis cras sed felis eget velit. Tempor orci eu lobortis elementum nibh tellus molestie.\n" +
            "\n" +
            "Ultrices dui sapien eget mi. Netus et malesuada fames ac turpis egestas integer eget aliquet. At consectetur lorem donec massa sapien faucibus et. Laoreet non curabitur gravida arcu ac tortor dignissim. Vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus. Montes nascetur ridiculus mus mauris vitae ultricies. Nunc mi ipsum faucibus vitae aliquet nec. Nisl rhoncus mattis rhoncus urna neque viverra justo. Fringilla ut morbi tincidunt augue interdum velit euismod in pellentesque. Augue neque gravida in fermentum et sollicitudin. Risus feugiat in ante metus dictum at tempor. In eu mi bibendum neque egestas congue quisque egestas diam. Ultrices eros in cursus turpis. Augue ut lectus arcu bibendum. Dolor purus non enim praesent elementum facilisis leo vel. Porta nibh venenatis cras sed felis. Ut aliquam purus sit amet luctus venenatis. Aliquet eget sit amet tellus cras adipiscing. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et netus. Faucibus scelerisque eleifend donec pretium.\n" +
            "\n" +
            "Rhoncus mattis rhoncus urna neque viverra justo. Vitae semper quis lectus nulla at volutpat diam. Morbi leo urna molestie at elementum eu. Est ullamcorper eget nulla facilisi etiam dignissim diam quis. Cursus eget nunc scelerisque viverra mauris in. Consectetur adipiscing elit duis tristique sollicitudin nibh sit amet. Dictum at tempor commodo ullamcorper. Sed viverra tellus in hac habitasse platea dictumst. Urna et pharetra pharetra massa massa ultricies mi. Nam aliquam sem et tortor. Ipsum dolor sit amet consectetur adipiscing elit pellentesque habitant. Bibendum at varius vel pharetra vel turpis nunc eget lorem. Bibendum enim facilisis gravida neque convallis a cras. Id consectetur purus ut faucibus pulvinar elementum. Elementum nibh tellus molestie nunc. Donec ultrices tincidunt arcu non sodales neque sodales ut etiam.\n" +
            "\n" +
            "Vulputate sapien nec sagittis aliquam malesuada bibendum arcu vitae. Faucibus vitae aliquet nec ullamcorper. Egestas quis ipsum suspendisse ultrices gravida dictum fusce ut. Dignissim convallis aenean et tortor at risus. In nibh mauris cursus mattis molestie a iaculis. Felis donec et odio pellentesque diam volutpat commodo sed. Massa massa ultricies mi quis. Placerat in egestas erat imperdiet. Tellus pellentesque eu tincidunt tortor aliquam. Turpis egestas sed tempus urna et pharetra pharetra massa massa. Cras sed felis eget velit. Magna ac placerat vestibulum lectus mauris ultrices. Fringilla est ullamcorper eget nulla facilisi etiam dignissim diam. Non arcu risus quis varius quam quisque id diam vel. Lobortis mattis aliquam faucibus purus in massa tempor. Lobortis elementum nibh tellus molestie nunc non blandit massa. Pharetra sit amet aliquam id diam. Eu facilisis sed odio morbi quis commodo. Tincidunt arcu non sodales neque sodales ut etiam. Pulvinar mattis nunc sed blandit libero volutpat.\n" +
            "\n" +
            "Fermentum odio eu feugiat pretium nibh ipsum consequat. Non diam phasellus vestibulum lorem sed risus ultricies tristique. Sollicitudin ac orci phasellus egestas tellus rutrum. Porta nibh venenatis cras sed felis eget. Bibendum at varius vel pharetra vel turpis. Ultrices in iaculis nunc sed augue lacus. Cursus in hac habitasse platea dictumst quisque sagittis. Ante in nibh mauris cursus mattis molestie. Ipsum dolor sit amet consectetur. Nunc lobortis mattis aliquam faucibus purus. Nec feugiat nisl pretium fusce id. Consequat nisl vel pretium lectus. Ornare massa eget egestas purus viverra accumsan in nisl nisi. Odio pellentesque diam volutpat commodo sed egestas egestas fringilla. Nisi est sit amet facilisis magna etiam tempor. Nulla facilisi cras fermentum odio eu."
        );

        launchFragment(ideaPresentation);

        Espresso.onView(withId(R.id.fragment_idea_details_content))
            .perform(ViewActions.scrollTo());
    }

    private ActivityScenario<AppCompatActivity> launchFragment(final IdeaPresentation idea) {
        final Bundle args = new Bundle();

        args.putParcelable("idea", idea);

        return TestUtils.launchFragmentInHiltContainer(IdeaDetailsFragment.class, args,
            R.style.Theme_ShareIt, null, null);
    }
}
