package com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._di;

import androidx.lifecycle.ViewModelProvider;

import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.IdeaCreateViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.state.IdeaCreateState;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.testing.TestInstallIn;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@Module
@TestInstallIn(
    components = {FragmentComponent.class},
    replaces = {IdeaCreateViewModelFactoryModule.class}
)
public abstract class TestIdeaCreateViewModelModule {
    public static IdeaCreateViewModel instance;
    public static BehaviorSubject<IdeaCreateState> stateController;

    @IdeaCreateViewModelFactoryQualifier
    @Provides
    public static ViewModelProvider.Factory provideIdeaCreateViewModelFactory() {
        instance = Mockito.mock(IdeaCreateViewModel.class);
        stateController = BehaviorSubject.create();

        Mockito.when(instance.getState()).thenReturn(stateController);

        final ViewModelProvider.Factory viewModelFactory =
            Mockito.mock(ViewModelProvider.Factory.class);

        Mockito.when(viewModelFactory.create(Mockito.any(), Mockito.any()))
            .thenReturn(instance);

        return viewModelFactory;
    }
}
