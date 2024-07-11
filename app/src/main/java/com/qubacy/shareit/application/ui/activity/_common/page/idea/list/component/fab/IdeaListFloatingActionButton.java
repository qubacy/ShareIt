package com.qubacy.shareit.application.ui.activity._common.page.idea.list.component.fab;

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
    @Nullable
    private Integer _shiftBottomMargin = null;

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

        final int finalBottomMargin = _initBottomMargin + insets.getSystemWindowInsetBottom()
            + (_shiftBottomMargin != null ? _shiftBottomMargin : 0);

        layoutParams.setMargins(
            layoutParams.leftMargin,
            layoutParams.topMargin,
            layoutParams.rightMargin,
            finalBottomMargin
        );

        return super.onApplyWindowInsets(insets);
    }

    public void shiftBottomMargin(int value) {
        if (_shiftBottomMargin != null) return;

        final CoordinatorLayout.LayoutParams layoutParams =
                ((CoordinatorLayout.LayoutParams) getLayoutParams());

        _shiftBottomMargin = value;

        layoutParams.setMargins(
            layoutParams.leftMargin,
            layoutParams.topMargin,
            layoutParams.rightMargin,
            layoutParams.bottomMargin + _shiftBottomMargin
        );
        requestLayout();
    }

    public void resetBottomMarginShift() {
        final CoordinatorLayout.LayoutParams layoutParams =
                ((CoordinatorLayout.LayoutParams) getLayoutParams());

        layoutParams.setMargins(
            layoutParams.leftMargin,
            layoutParams.topMargin,
            layoutParams.rightMargin,
            layoutParams.bottomMargin - _shiftBottomMargin
        );
        requestLayout();

        _shiftBottomMargin = null;
    }
}
