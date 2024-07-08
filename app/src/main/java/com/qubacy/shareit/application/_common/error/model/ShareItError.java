package com.qubacy.shareit.application._common.error.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class ShareItError implements Parcelable {
    public final long id;
    public final String message;
    public final boolean isCritical;

    public ShareItError(
        int id,
        @NotNull String message,
        boolean isCritical
    ) {
        this.id = id;
        this.message = message;
        this.isCritical = isCritical;
    }

    protected ShareItError(Parcel in) {
        id = in.readLong();
        message = in.readString();
        isCritical = in.readByte() != 0;
    }

    public static final Creator<ShareItError> CREATOR = new Creator<ShareItError>() {
        @Override
        public ShareItError createFromParcel(Parcel in) {
            return new ShareItError(in);
        }

        @Override
        public ShareItError[] newArray(int size) {
            return new ShareItError[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(message);
        dest.writeByte((byte) (isCritical ? 1 : 0));
    }
}
