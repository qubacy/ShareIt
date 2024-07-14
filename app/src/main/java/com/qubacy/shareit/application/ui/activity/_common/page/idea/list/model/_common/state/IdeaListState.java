package com.qubacy.shareit.application.ui.activity._common.page.idea.list.model._common.state;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qubacy.shareit.application.ui.activity._common.model.base.state.ShareItState;
import com.qubacy.shareit.application.ui.activity._common.page.idea._common.presentation.IdeaPresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class IdeaListState extends ShareItState implements Parcelable {
    @Nullable
    public final ArrayList<IdeaPresentation> ideas;
    public final boolean isLoading;

    public IdeaListState(
        @Nullable ArrayList<IdeaPresentation> ideas,
        boolean isLoading
    ) {
        this.ideas = ideas;
        this.isLoading = isLoading;
    }

    public IdeaListState(
        @Nullable List<IdeaPresentation> ideas,
        boolean isLoading
    ) {
        this.ideas = new ArrayList<>(ideas);
        this.isLoading = isLoading;
    }

    protected IdeaListState(Parcel in) {
        ideas = in.<IdeaPresentation>readArrayList(IdeaListState.class.getClassLoader());
        isLoading = in.readByte() != 0;
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
        if (ideas != null) dest.writeArray(ideas.toArray());
        dest.writeByte((byte) (isLoading ? 1 : 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdeaListState that = (IdeaListState) o;
        boolean areItemsEqual;

        if (that.ideas == null && ideas == null) areItemsEqual = true;
        else if (that.ideas == null || ideas == null) areItemsEqual = false;
        else areItemsEqual = Arrays.equals(ideas.toArray(), that.ideas.toArray());

        return isLoading == that.isLoading && areItemsEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ideas, isLoading);
    }
}
