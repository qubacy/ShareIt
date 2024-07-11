package com.qubacy.shareit.application.ui.activity._common.page._common.base.stateful;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qubacy.shareit.application.ui.activity._common.model.base.ShareItViewModel;
import com.qubacy.shareit.application.ui.activity._common.model.base.state.ShareItState;
import com.qubacy.shareit.application.ui.activity._common.page._common.base._common.ShareItFragment;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public abstract class StatefulFragment<
    StateType extends ShareItState,
    ViewModelType extends ShareItViewModel<? extends StateType>
> extends ShareItFragment {
    protected abstract ViewModelType getModel();

    private Disposable _stateSubscription;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        listenState();
    }

    @Override
    public void onDestroy() {
        if (_stateSubscription != null) _stateSubscription.dispose();

        super.onDestroy();
    }

    private void listenState() {
        _stateSubscription = getModel().getState()
            .subscribe((Consumer<StateType>) state -> processState(state));
    }

    @CallSuper
    protected void processState(@NotNull StateType state) {}
}
