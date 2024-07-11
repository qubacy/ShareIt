package com.qubacy.shareit.application.ui.activity._common.page.idea.list.component.fab.behavior;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.component.fab.IdeaListFloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IdeaListFloatingActionButtonBehavior extends CoordinatorLayout.Behavior<IdeaListFloatingActionButton> {
    public IdeaListFloatingActionButtonBehavior(
        @NotNull Context context,
        @Nullable AttributeSet attributeSet
    ) {
        super(context, attributeSet);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean layoutDependsOn(
        @NonNull CoordinatorLayout parent,
        @NonNull IdeaListFloatingActionButton child,
        @NonNull View dependency
    ) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onDependentViewChanged(
        @NonNull CoordinatorLayout parent,
        @NonNull IdeaListFloatingActionButton child,
        @NonNull View dependency
    ) {
        if (!(dependency instanceof Snackbar.SnackbarLayout)) return false;

        final int snackbarHeight = dependency.getMeasuredHeight();

        child.shiftBottomMargin(snackbarHeight);

        return false;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onDependentViewRemoved(
        @NonNull CoordinatorLayout parent,
        @NonNull IdeaListFloatingActionButton child,
        @NonNull View dependency
    ) {
        if (!(dependency instanceof Snackbar.SnackbarLayout)) return;

        child.resetBottomMarginShift();
    }
}
