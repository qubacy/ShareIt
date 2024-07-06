package com.qubacy.shareit.application.ui.activity.page._common.stateful;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qubacy.shareit.application.ui._common.presentation.error.ErrorPresentation;
import com.qubacy.shareit.application.ui.activity.page._common.base.ShareItFragment;
import com.qubacy.shareit.application.ui.activity.page._common.stateful.model.ShareItViewModel;
import com.qubacy.shareit.application.ui.activity.page._common.stateful.model.state.ShareItState;

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

        listenState();
    }

    @Override
    public void onDestroy() {
        _stateSubscription.dispose();

        super.onDestroy();
    }

    private void listenState() {
        _stateSubscription = getModel().getState()
            .subscribe((Consumer<StateType>) state -> processState(state));
    }

    @CallSuper
    protected void processState(@NotNull StateType state) {
        ErrorPresentation error = state.getError();

        if (error != null) processError(error);
    }

    protected void processError(ErrorPresentation error) {
        // todo: processing the error (showing a dialog, etc.);
    }
}
