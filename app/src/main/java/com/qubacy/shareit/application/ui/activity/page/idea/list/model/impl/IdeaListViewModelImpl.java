package com.qubacy.shareit.application.ui.activity.page.idea.list.model.impl;

import androidx.lifecycle.SavedStateHandle;

import com.qubacy.shareit.application.ui.activity.page.idea._common.presentation.IdeaPresentation;
import com.qubacy.shareit.application.ui.activity.page.idea.list.model._common.IdeaListViewModel;
import com.qubacy.shareit.application.ui.activity.page.idea.list.model._common.state.IdeaListState;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class IdeaListViewModelImpl extends IdeaListViewModel {
    static final String STATE_KEY = "STATE";

    private final BehaviorSubject<IdeaListState> _stateController;
    private final SavedStateHandle _store;

    public IdeaListViewModelImpl(
        @NotNull SavedStateHandle store
    ) {
        _stateController = BehaviorSubject
            .createDefault(new IdeaListState(
                // todo: delete:
                List.of(new IdeaPresentation("test", "test title", "test content")),
                false
            ));
        _store = store;

        restoreState();
    }

    @Override
    protected void onCleared() {
        preserveState();

        super.onCleared();
    }

    private void restoreState() {
        IdeaListState state = _store.get(STATE_KEY);

        if (state == null) return;

        _stateController.onNext(state);
    }

    private void preserveState() {
        IdeaListState lastState = _stateController.getValue();

        _store.set(STATE_KEY, lastState);
    }

    @Override
    public @NotNull Observable<IdeaListState> getState() {
        return _stateController;
    }

    @Override
    public void getRecentIdeas() {
        // todo: loading ideas..


    }
}
