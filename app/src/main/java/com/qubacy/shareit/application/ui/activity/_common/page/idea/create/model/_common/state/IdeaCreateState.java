package com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.qubacy.shareit.application.ui.activity._common.model.base.state.ShareItState;

public class IdeaCreateState extends ShareItState implements Parcelable {
    public final boolean isLoading;
    public final boolean isCreated;

    public IdeaCreateState(
        boolean isLoading,
        boolean isCreated
    ) {
        this.isLoading = isLoading;
        this.isCreated = isCreated;
    }

    protected IdeaCreateState(Parcel in) {
        isLoading = in.readByte() != 0;
        isCreated = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeByte((byte) (isCreated ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IdeaCreateState> CREATOR = new Creator<IdeaCreateState>() {
        @Override
        public IdeaCreateState createFromParcel(Parcel in) {
            return new IdeaCreateState(in);
        }

        @Override
        public IdeaCreateState[] newArray(int size) {
            return new IdeaCreateState[size];
        }
    };
}
