package com.qubacy.shareit.application.ui.activity._common.page._common.util.topbar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.qubacy.shareit.application.ui.activity._common.page._common.base._common.ShareItFragment;
import com.qubacy.shareit.application.ui.activity.main.ShareItActivity;

import org.jetbrains.annotations.NotNull;

public class TopBarFragmentUtil {
    public static void setupTopAppBar(
        @NotNull ShareItActivity activity,
        @NotNull ShareItFragment fragment,
        @NotNull Toolbar toolbar
    ) {
        final NavController navController = NavHostFragment.findNavController(fragment);

        final DrawerLayout drawerLayout = activity.getNavigationDrawerLayout();
        final AppBarConfiguration appBarConfiguration = new AppBarConfiguration
            .Builder(ShareItActivity.TOP_LEVEL_DESTINATIONS)
            .setOpenableLayout(drawerLayout)
            .build();

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        // todo: i didn't have a choice..:
        final ToolbarTitlePreserverListener listener =
            new ToolbarTitlePreserverListener(navController.getCurrentDestination(), toolbar);

        navController.addOnDestinationChangedListener(listener);
    }

    private static class ToolbarTitlePreserverListener
        implements NavController.OnDestinationChangedListener
    {
        private final NavDestination _originalDestination;
        private final Toolbar _toolbar;

        public ToolbarTitlePreserverListener(
            @NotNull NavDestination originalDestination,
            @NotNull Toolbar toolbar
        ) {
            _originalDestination = originalDestination;
            _toolbar = toolbar;
        }

        @Override
        public void onDestinationChanged(
            @NonNull NavController navController,
            @NonNull NavDestination navDestination,
            @Nullable Bundle bundle
        ) {
            if (navDestination == _originalDestination) return;

            _toolbar.setTitle(_originalDestination.getLabel());
            navController.removeOnDestinationChangedListener(this);
        }
    }
}
