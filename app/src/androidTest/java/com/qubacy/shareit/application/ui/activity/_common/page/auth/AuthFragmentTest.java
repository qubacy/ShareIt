package com.qubacy.shareit.application.ui.activity._common.page.auth;

import android.os.Bundle;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.qubacy.shareit.R;
import com.qubacy.shareit._test.util.TestUtils;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._di.TestAuthViewModelModule;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model._di.module.AuthViewModelFactoryModule;
import com.qubacy.shareit.application.ui.activity._common.page.auth.model.impl.AuthViewModelImpl;

import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;

@RunWith(AndroidJUnit4.class)
@HiltAndroidTest
@UninstallModules({AuthViewModelFactoryModule.class})
public class AuthFragmentTest {
    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Before
    public void setup() {
        hiltRule.inject();
    }

    @Test
    public void test() {
        launchFragment(null);


    }

    private AuthViewModelImpl retrieveViewModelMock() {
        return TestAuthViewModelModule.instance;
    }

    private void launchFragment(@Nullable Bundle args) {
        final Bundle finalArgs = args == null ? new Bundle() : args;

        TestUtils.launchFragmentInHiltContainer(
            AuthFragment.class, finalArgs, R.style.Theme_ShareIt, null);
    }
}
