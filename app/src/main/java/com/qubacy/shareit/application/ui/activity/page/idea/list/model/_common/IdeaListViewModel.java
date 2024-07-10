package com.qubacy.shareit.application.ui.activity.page.idea.list.model._common;

import com.qubacy.shareit.application.ui.activity._common.model.ShareItViewModel;
import com.qubacy.shareit.application.ui.activity.page.idea.list.model._common.state.IdeaListState;

public abstract class IdeaListViewModel extends ShareItViewModel<IdeaListState> {
    public abstract void getRecentIdeas();
}
