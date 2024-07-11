package com.qubacy.shareit.application.ui.activity._common.page.idea.create.model.impl;

import androidx.lifecycle.SavedStateHandle;

import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application.ui.activity._common.page.idea._common.presentation.IdeaPresentation;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.IdeaCreateViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.state.IdeaCreateState;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class IdeaCreateViewModelImpl extends IdeaCreateViewModel {
    static final String STATE_KEY = "STATE";

    private final SavedStateHandle _store;
    private final BehaviorSubject<IdeaCreateState> _stateController;
    private final ErrorBus _errorBus;

    public IdeaCreateViewModelImpl(
        @NotNull SavedStateHandle store,
        @NotNull ErrorBus errorBus
    ) {
        _store = store;
        _stateController = BehaviorSubject.createDefault(
            new IdeaCreateState(false, false));
        _errorBus = errorBus;

        restoreState();
    }

    @Override
    protected void onCleared() {
        preserveState();

        super.onCleared();
    }

    private void restoreState() {
        IdeaCreateState state = _store.get(STATE_KEY);

        if (state == null) return;

        _stateController.onNext(state);
    }

    private void preserveState() {
        IdeaCreateState lastState = _stateController.getValue();

        _store.set(STATE_KEY, lastState);
    }

    @Override
    public @NotNull Observable<IdeaCreateState> getState() {
        return _stateController;
    }


    @Override
    public void createIdea(@NotNull IdeaPresentation idea) {
        // todo: implement..


    }
}
