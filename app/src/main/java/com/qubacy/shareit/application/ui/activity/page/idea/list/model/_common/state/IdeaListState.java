package com.qubacy.shareit.application.ui.activity.page.idea.list.model._common.state;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.qubacy.shareit.application.ui.activity._common.model.state.ShareItState;

public class IdeaListState extends ShareItState implements Parcelable {
    public IdeaListState() {

    }

    protected IdeaListState(Parcel in) {
    }

    public static final Creator<IdeaListState> CREATOR = new Creator<IdeaListState>() {
        @Override
        public IdeaListState createFromParcel(Parcel in) {
            return new IdeaListState(in);
        }

        @Override
        public IdeaListState[] newArray(int size) {
            return new IdeaListState[size];
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
