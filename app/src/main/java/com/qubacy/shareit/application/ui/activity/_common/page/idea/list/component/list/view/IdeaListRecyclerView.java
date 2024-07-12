package com.qubacy.shareit.application.ui.activity._common.page.idea.list.component.list.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IdeaListRecyclerView extends RecyclerView {
    private boolean _isAtBeginning = true;

    public IdeaListRecyclerView(
        @NonNull Context context,
        @Nullable AttributeSet attrs
    ) {
        super(context, attrs);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        final LayoutManager layoutManager = getLayoutManager();

        if (layoutManager.getClass() != LinearLayoutManager.class) return;

        final int firstVisibleItemIndex =
            ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();

        if (firstVisibleItemIndex == 0) _isAtBeginning = true;
        else _isAtBeginning = false;
    }

    public boolean getIsAtBeginning() {
        return _isAtBeginning;
    }
}
