package com.qubacy.shareit.application.ui.activity._test.util;

import android.view.View;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.jetbrains.annotations.NotNull;

public class ActivityTestUtils {
    @UiThread
    public static void attachNavController(
        @NotNull AppCompatActivity activity,
        @NotNull NavController navController
    ) {
        final View rootView = activity.findViewById(android.R.id.content);

        Navigation.setViewNavController(rootView, navController);
    }
}
