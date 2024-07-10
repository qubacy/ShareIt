package com.qubacy.shareit.application.ui.activity.page.idea.list.model.impl.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.qubacy.shareit.application.ui.activity.page.idea.list.model.impl.IdeaListViewModelImpl;

import javax.inject.Inject;

public class IdeaListViewModelImplFactory extends AbstractSavedStateViewModelFactory {
    @Inject
    public IdeaListViewModelImplFactory() {}

    @NonNull
    @Override
    protected <T extends ViewModel> T create(
        @NonNull String s,
        @NonNull Class<T> aClass,
        @NonNull SavedStateHandle savedStateHandle
    ) {
        if (!aClass.isAssignableFrom(IdeaListViewModelImpl.class))
            throw new IllegalArgumentException();

        return (T) new IdeaListViewModelImpl(savedStateHandle);
    }
}
