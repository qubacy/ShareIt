package com.qubacy.shareit.application.ui.activity._common.page.idea.list.model.impl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.SavedStateHandle;

import com.google.common.collect.Lists;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qubacy.shareit.application._common.error.ErrorEnum;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.ui.activity._common.page.idea._common.context.IdeaContext;
import com.qubacy.shareit.application.ui.activity._common.page.idea._common.presentation.IdeaPresentation;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._common.IdeaListViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._common.state.IdeaListState;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class IdeaListViewModelImpl extends IdeaListViewModel {
    static final String TAG = "IdeaListViewModelImpl";
    static final String STATE_KEY = "STATE";

    static final int IDEA_COUNT_LIMIT = 20;

    @NotNull
    private final BehaviorSubject<IdeaListState> _stateController;
    @NotNull
    private final SavedStateHandle _store;
    @NotNull
    private final ErrorBus _errorBus;
    @NotNull
    private final DatabaseReference _database;

    @Nullable
    private ValueEventListener _recentIdeasListener;

    public IdeaListViewModelImpl(
        @NotNull SavedStateHandle store,
        @NotNull ErrorBus errorBus,
        @NotNull DatabaseReference database
    ) {
        _stateController = BehaviorSubject
            .createDefault(new IdeaListState(null, false));
        _store = store;
        _errorBus = errorBus;
        _database = database;

        restoreState();
    }

    @Override
    protected void onCleared() {
        preserveState();

        if (_recentIdeasListener != null)
            _database.removeEventListener(_recentIdeasListener);

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
        _stateController.onNext(new IdeaListState(null, true));

        _recentIdeasListener = _database.child(IdeaContext.IDEAS_PATH).limitToLast(IDEA_COUNT_LIMIT)
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) { onIdeasGotten(snapshot); }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { onDatabaseErrorGotten(error); }
            });
    }

    @Override
    public void pause() {
        if (_recentIdeasListener != null)
            _database.child(IdeaContext.IDEAS_PATH).removeEventListener(_recentIdeasListener);
    }

    private void onIdeasGotten(@NotNull DataSnapshot snapshot) {
        final ArrayList<IdeaPresentation> ideas = new ArrayList<>();

        for (final DataSnapshot idea : snapshot.getChildren()) {
            ideas.add(idea.getValue(IdeaPresentation.class));
        }

        _stateController.onNext(new IdeaListState(Lists.reverse(ideas), false));
    }

    private void onDatabaseErrorGotten(@NotNull DatabaseError error) {
        if (error.getCode() == DatabaseError.WRITE_CANCELED) return;

        _stateController.onNext(new IdeaListState(null, false));
        _errorBus.emitError(new ErrorReference(ErrorEnum.DATABASE_FAIL.id, error.getMessage()));
    }
}
