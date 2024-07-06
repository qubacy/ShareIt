package com.qubacy.shareit.application.ui.activity;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.qubacy.shareit.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShareItActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        splashScreen.setOnExitAnimationListener(splashScreenViewProvider -> {
            splashScreenViewProvider.remove();
            setupEdgeToEdge();
        });

        setContentView(R.layout.activity_container);
    }

    private void setupEdgeToEdge() {
        EdgeToEdge.enable(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getWindow().setNavigationBarContrastEnforced(false);
        }
    }
}
