package com.qubacy.shareit.application.ui.activity._common.page.idea.create.model.impl;

import androidx.lifecycle.SavedStateHandle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.qubacy.shareit.application._common.error.ErrorEnum;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.ui.activity._common.page.idea._common.context.IdeaContext;
import com.qubacy.shareit.application.ui.activity._common.page.idea._common.presentation.IdeaPresentation;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create._common.data.IdeaSketch;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.IdeaCreateViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.state.IdeaCreateState;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class IdeaCreateViewModelImpl extends IdeaCreateViewModel {
    static final String STATE_KEY = "STATE";

    private final SavedStateHandle _store;
    private final BehaviorSubject<IdeaCreateState> _stateController;
    private final ErrorBus _errorBus;

    private final DatabaseReference _database;

    public IdeaCreateViewModelImpl(
        @NotNull SavedStateHandle store,
        @NotNull ErrorBus errorBus
    ) {
        _store = store;
        _stateController = BehaviorSubject.createDefault(
            new IdeaCreateState(false, false));
        _errorBus = errorBus;

        _database = FirebaseDatabase.getInstance(IdeaContext.DATABASE_URL).getReference();

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
    public void createIdea(@NotNull IdeaSketch ideaSketch) {
        _stateController.onNext(new IdeaCreateState(true, false));

        final String uid = _database.push().getKey();
        final IdeaPresentation ideaPresentation = IdeaPresentation.fromIdeaSketch(uid, ideaSketch);
        final Map<String, Object> ideaMap = ideaPresentation.toMap();

        final String updatePath = IdeaContext.IDEAS_PATH + '/' + uid;
        final Map<String, Object> updateMap = new HashMap<>();

        updateMap.put(updatePath, ideaMap);

        _database.updateChildren(updateMap)
            .addOnSuccessListener(unused -> {
                _stateController.onNext(new IdeaCreateState(false, true));
            })
            .addOnFailureListener(e -> {
                _errorBus.emitError(new ErrorReference(ErrorEnum.DATABASE_FAIL.id, e.getMessage()));
                _stateController.onNext(new IdeaCreateState(false, false));
            });
    }
}
