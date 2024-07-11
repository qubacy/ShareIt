package com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._common;

import com.qubacy.shareit.application.ui.activity._common.model.base.ShareItViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._common.state.IdeaListState;

public abstract class IdeaListViewModel extends ShareItViewModel<IdeaListState> {
    public abstract void getRecentIdeas();
}
