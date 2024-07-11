package com.qubacy.shareit.application.ui.activity._common.page._common.util.navigation;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NavigationFragmentUtil {
    @Nullable
    public static <T> T getPrevDestinationResult(
        @NotNull Fragment fragment,
        @NotNull String key
    ) {
        final SavedStateHandle savedStateHandle = NavHostFragment.findNavController(fragment)
                .getCurrentBackStackEntry().getSavedStateHandle();

        if (!savedStateHandle.contains(key)) return null;

        final T result = savedStateHandle.get(key);

        savedStateHandle.remove(key); // todo: is it ok?

        return result;
    }

    public static <T> void setDestinationResult(
        @NotNull Fragment fragment,
        @NotNull String key,
        @NotNull T value
    ) {
        NavHostFragment.findNavController(fragment).getPreviousBackStackEntry()
            .getSavedStateHandle().set(key, value);
    }
}
