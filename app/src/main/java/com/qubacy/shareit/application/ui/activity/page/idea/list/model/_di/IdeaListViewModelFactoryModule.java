package com.qubacy.shareit.application.ui.activity.page.idea.list.model._di;

import androidx.lifecycle.ViewModelProvider;

import com.qubacy.shareit.application.ui.activity.page.idea.list.model.impl.factory.IdeaListViewModelImplFactory;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@InstallIn(FragmentComponent.class)
@Module
public interface IdeaListViewModelFactoryModule {
    @IdeaListViewModelFactoryQualifier
    @Binds
    ViewModelProvider.Factory bindIdeaListViewModelFactory(
        IdeaListViewModelImplFactory ideaListViewModelFactory
    );
}
