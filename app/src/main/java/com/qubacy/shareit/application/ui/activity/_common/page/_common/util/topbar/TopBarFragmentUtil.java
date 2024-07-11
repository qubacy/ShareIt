package com.qubacy.shareit.application.ui.activity._common.page._common.util.topbar;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
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
        final DrawerLayout drawerLayout = activity.getNavigationDrawerLayout();
        final AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(ShareItActivity.TOP_LEVEL_DESTINATIONS)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupWithNavController(
                toolbar, NavHostFragment.findNavController(fragment), appBarConfiguration);
    }
}
