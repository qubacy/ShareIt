package com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._di;

import androidx.lifecycle.ViewModelProvider;

import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._common.IdeaListViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._common.state.IdeaListState;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.testing.TestInstallIn;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@Module
@TestInstallIn(
    components = {FragmentComponent.class},
    replaces = {IdeaListViewModelFactoryModule.class}
)
public abstract class TestIdeaListViewModelModule {
    public static IdeaListViewModel instance;
    public static BehaviorSubject<IdeaListState> stateController;

    @IdeaListViewModelFactoryQualifier
    @Provides
    public static ViewModelProvider.Factory provideIdeaListViewModel() {
        instance = Mockito.mock(IdeaListViewModel.class);
        stateController = BehaviorSubject.create();

        Mockito.when(instance.getState()).thenReturn(stateController);

        final ViewModelProvider.Factory factoryMock = Mockito.mock(ViewModelProvider.Factory.class);

        Mockito.when(factoryMock.create(Mockito.any(), Mockito.any())).thenReturn(instance);

        return factoryMock;
    }
}
