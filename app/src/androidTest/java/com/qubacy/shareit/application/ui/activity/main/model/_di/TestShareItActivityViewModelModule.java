package com.qubacy.shareit.application.ui.activity.main.model._di;

import androidx.lifecycle.ViewModelProvider;

import com.qubacy.shareit.application.ui.activity.main.model._common.ShareItActivityViewModel;
import com.qubacy.shareit.application.ui.activity.main.model._common.state.ShareItActivityState;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.testing.TestInstallIn;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@Module
@TestInstallIn(
    components = {ActivityComponent.class},
    replaces = {ShareItActivityViewModelFactoryModule.class}
)
public abstract class TestShareItActivityViewModelModule {
    public static ShareItActivityViewModel instance;
    public static BehaviorSubject<ShareItActivityState> stateController;

    @ShareItActivityViewModelFactoryQualifier
    @Provides
    public static ViewModelProvider.Factory provideShareItActivityViewModelFactory() {
        instance = Mockito.mock(ShareItActivityViewModel.class);
        stateController = BehaviorSubject.create();

        Mockito.when(instance.getState()).thenReturn(stateController);

        final ViewModelProvider.Factory viewModelFactoryMock =
            Mockito.mock(ViewModelProvider.Factory.class);

        Mockito.when(viewModelFactoryMock.create(Mockito.any(), Mockito.any())).thenReturn(instance);

        return viewModelFactoryMock;
    }
}
