package com.qubacy.shareit.application;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class ShareItApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
