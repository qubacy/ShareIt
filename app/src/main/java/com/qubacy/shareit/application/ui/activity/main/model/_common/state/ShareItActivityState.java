package com.qubacy.shareit.application.ui.activity.main.model._common.state;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.qubacy.shareit.application._common.error.model.ShareItError;
import com.qubacy.shareit.application.ui.activity._common.model.base.state.ShareItState;

import org.jetbrains.annotations.Nullable;

public class ShareItActivityState extends ShareItState implements Parcelable {
    @Nullable
    public final ShareItError error;

    public ShareItActivityState(
        @Nullable ShareItError error
    ) {
        this.error = error;
    }

    protected ShareItActivityState(Parcel in) {
        error = in.readParcelable(ShareItError.class.getClassLoader());
    }

    public static final Creator<ShareItActivityState> CREATOR = new Creator<ShareItActivityState>() {
        @Override
        public ShareItActivityState createFromParcel(Parcel in) {
            return new ShareItActivityState(in);
        }

        @Override
        public ShareItActivityState[] newArray(int size) {
            return new ShareItActivityState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(error, flags);
    }
}
