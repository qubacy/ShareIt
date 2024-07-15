package com.qubacy.shareit.application.ui.activity._common.page.auth.model._di;

import androidx.lifecycle.ViewModelProvider;

import com.qubacy.shareit.application.ui.activity._common.page.auth.model._common.AuthViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._common.state.AuthState;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._di.module.AuthViewModelFactoryModule;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._di.module.AuthViewModelFactoryQualifier;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model.impl.factory.AuthViewModelImplFactory;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.testing.TestInstallIn;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@Module
@TestInstallIn(
    components = {FragmentComponent.class},
    replaces = {AuthViewModelFactoryModule.class}
)
public abstract class TestAuthViewModelModule {
    public static BehaviorSubject<AuthState> stateController;
    public static AuthViewModel instance;

    @AuthViewModelFactoryQualifier
    @Provides
    public static ViewModelProvider.Factory provideAuthViewModelFactory() {
        stateController = BehaviorSubject.create();
        instance = Mockito.mock(AuthViewModel.class);

        Mockito.when(instance.getState()).thenReturn(stateController);

        final AuthViewModelImplFactory viewModelFactoryMock =
            Mockito.mock(AuthViewModelImplFactory.class);

        Mockito.when(viewModelFactoryMock.create(Mockito.any())).thenReturn(instance);
        Mockito.when(viewModelFactoryMock.create(Mockito.any(), Mockito.any())).thenReturn(instance);

        return viewModelFactoryMock;
    }
}
