package com.qubacy.shareit.application.ui.activity._common.model;

import androidx.lifecycle.ViewModel;

import com.qubacy.shareit.application.ui.activity._common.model.state.ShareItState;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Observable;

public abstract class ShareItViewModel<StateType extends ShareItState> extends ViewModel {
    @NotNull
    abstract public Observable<StateType> getState();
}
