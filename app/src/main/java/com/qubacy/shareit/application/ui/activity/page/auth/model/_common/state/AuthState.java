package com.qubacy.shareit.application.ui.activity.page.auth.model._common.state;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.qubacy.shareit.application.ui.activity._common.model.state.ShareItState;

public class AuthState extends ShareItState implements Parcelable {
    public AuthState() {}

    protected AuthState(Parcel in) {}

    public static final Creator<AuthState> CREATOR = new Creator<AuthState>() {
        @Override
        public AuthState createFromParcel(Parcel in) {
            return new AuthState(in);
        }

        @Override
        public AuthState[] newArray(int size) {
            return new AuthState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }
}
