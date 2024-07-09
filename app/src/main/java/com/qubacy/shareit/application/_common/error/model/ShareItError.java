package com.qubacy.shareit.application._common.error.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.qubacy.shareit.application.data.error.repository.source.local.database._common.view.ErrorDatabaseView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShareItError implements Parcelable {
    public final long id;
    public final String message;
    public final boolean isCritical;

    public ShareItError(
        long id,
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

    public static ShareItError fromErrorDatabaseView(
        @NotNull ErrorDatabaseView errorDatabaseView,
        @Nullable String cause
    ) {
        return new ShareItError(
            errorDatabaseView.id,
            cause != null ? cause : errorDatabaseView.localization,
            errorDatabaseView.isCritical
        );
    }
}
