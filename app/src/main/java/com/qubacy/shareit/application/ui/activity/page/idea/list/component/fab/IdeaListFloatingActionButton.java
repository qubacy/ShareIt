package com.qubacy.shareit.application.ui.activity.page.idea.list.component.fab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowInsets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IdeaListFloatingActionButton extends FloatingActionButton {
    @Nullable
    private Integer _initBottomMargin = null;

    public IdeaListFloatingActionButton(
        @NonNull Context context,
        @Nullable AttributeSet attrs
    ) {
        super(context, attrs);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        final CoordinatorLayout.LayoutParams layoutParams =
            ((CoordinatorLayout.LayoutParams) getLayoutParams());

        if (_initBottomMargin == null) _initBottomMargin = layoutParams.bottomMargin;

        final int finalBottomMargin = _initBottomMargin + insets.getSystemWindowInsetBottom();

        layoutParams.setMargins(
            layoutParams.leftMargin,
            layoutParams.topMargin,
            layoutParams.rightMargin,
            finalBottomMargin
        );

        return super.onApplyWindowInsets(insets);
    }
}
