package com.qubacy.shareit._test.util;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Preconditions;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qubacy.shareit.application.ui.activity.hilt.HiltActivity;

import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class TestUtils {
    public static <T extends Fragment> ActivityScenario<AppCompatActivity> launchFragmentInHiltContainer(
        Class<T> fragmentClass,
        @Nullable Bundle fragmentArgs,
        @StyleRes int themeResId,
        @Nullable Function<Fragment, Void> postFragmentAction,
        @Nullable Function<AppCompatActivity, Void> preActivityAction
    ) {
        final Intent startActivityIntent = Intent.makeMainActivity(
            new ComponentName(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                HiltActivity.class
            )
        ).putExtra(
            "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
            themeResId
        );

        final ActivityScenario<AppCompatActivity> activityScenario =
            ActivityScenario.launch(startActivityIntent);

        activityScenario.onActivity(activity -> {
            if (preActivityAction != null) preActivityAction.apply(activity);

            final FragmentManager fragmentManager = activity.getSupportFragmentManager();

            final Fragment fragment = fragmentManager
                .getFragmentFactory().instantiate(
                    Preconditions.checkNotNull(fragmentClass.getClassLoader()),
                    fragmentClass.getName()
                );

            fragment.setArguments(fragmentArgs);
            fragmentManager
                .beginTransaction()
                .add(android.R.id.content, fragment, "")
                .commitNow();

            if (postFragmentAction != null) postFragmentAction.apply(fragment);
        });

        return activityScenario;
    }
}
