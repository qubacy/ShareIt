package com.qubacy.shareit.application.ui.activity.page.idea._common.presentation;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class IdeaPresentation {
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

    @Exclude
    @NotNull
    public Map<String, Object> toMap() {
        final HashMap<String, Object> map = new HashMap<>();

        map.put(UID_PROP_NAME, uid);
        map.put(TITLE_PROP_NAME, title);
        map.put(CONTENT_PROP_NAME, content);

        return map;
    }
}
