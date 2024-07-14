package com.qubacy.shareit._test.runner;

import android.app.Application;
import android.content.Context;

import androidx.test.runner.AndroidJUnitRunner;

import dagger.hilt.android.testing.HiltTestApplication;

public class ShareItTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(
        ClassLoader cl,
        String className,
        Context context
    ) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, HiltTestApplication.class.getName(), context);
    }
}
