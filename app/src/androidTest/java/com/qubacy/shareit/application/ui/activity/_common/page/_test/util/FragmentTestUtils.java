package com.qubacy.shareit.application.ui.activity._common.page._test.util;

import android.view.View;

import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.jetbrains.annotations.NotNull;

public class FragmentTestUtils {
    @UiThread
    public static void attachNavController(
        @NotNull Fragment fragment,
        @NotNull NavController navController
    ) {
        final View fragmentView = fragment.getView();

        Navigation.setViewNavController(fragmentView, navController);
    }
}
