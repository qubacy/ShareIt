package com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common;

import com.qubacy.shareit.application.ui.activity._common.model.base.ShareItViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create._common.data.IdeaSketch;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.state.IdeaCreateState;

import org.jetbrains.annotations.NotNull;

public abstract class IdeaCreateViewModel extends ShareItViewModel<IdeaCreateState> {
    public abstract void createIdea(@NotNull IdeaSketch ideaSketch);
}
