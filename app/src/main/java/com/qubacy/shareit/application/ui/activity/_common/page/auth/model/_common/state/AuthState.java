package com.qubacy.shareit.application.ui.activity._common.page.auth.model._common.state;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.qubacy.shareit.application.ui.activity._common.model.base.state.ShareItState;

public class AuthState extends ShareItState implements Parcelable {
    public final boolean isAuthorized;
    public final boolean isLoading;

    public AuthState(
        boolean isAuthorized,
        boolean isLoading
    ) {
        this.isAuthorized = isAuthorized;
        this.isLoading = isLoading;
    }

    protected AuthState(Parcel in) {
        isAuthorized = in.readByte() != 0;
        isLoading = in.readByte() != 0;
    }

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
        dest.writeByte((byte) (isAuthorized ? 1 : 0));
        dest.writeByte((byte) (isLoading ? 1 : 0));
    }
}
