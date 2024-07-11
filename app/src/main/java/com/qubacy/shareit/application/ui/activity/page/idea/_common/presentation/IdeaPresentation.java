package com.qubacy.shareit.application.ui.activity.page.idea._common.presentation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@IgnoreExtraProperties
public class IdeaPresentation implements Parcelable {
    static final String UID_PROP_NAME = "uid";
    static final String TITLE_PROP_NAME = "title";
    static final String CONTENT_PROP_NAME = "content";

    public String uid;
    public String title;
    public String content;

    public IdeaPresentation() {}
    public IdeaPresentation(
        @NotNull String uid,
        @NotNull String title,
        @NotNull String content
    ) {
        this.uid = uid;
        this.title = title;
        this.content = content;
    }

    protected IdeaPresentation(Parcel in) {
        uid = in.readString();
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<IdeaPresentation> CREATOR = new Creator<IdeaPresentation>() {
        @Override
        public IdeaPresentation createFromParcel(Parcel in) {
            return new IdeaPresentation(in);
        }

        @Override
        public IdeaPresentation[] newArray(int size) {
            return new IdeaPresentation[size];
        }
    };

    @Exclude
    @NotNull
    public Map<String, Object> toMap() {
        final HashMap<String, Object> map = new HashMap<>();

        map.put(UID_PROP_NAME, uid);
        map.put(TITLE_PROP_NAME, title);
        map.put(CONTENT_PROP_NAME, content);

        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdeaPresentation that)) return false;

        return Objects.equals(uid, that.uid)
            && Objects.equals(title, that.title)
            && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, title, content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(title);
        dest.writeString(content);
    }
}
