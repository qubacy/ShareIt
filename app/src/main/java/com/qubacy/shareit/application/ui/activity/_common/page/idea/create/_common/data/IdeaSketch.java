package com.qubacy.shareit.application.ui.activity._common.page.idea.create._common.data;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class IdeaSketch {
    public static final String TITLE_PROP_NAME = "title";
    public static final String CONTENT_PROP_NAME = "content";

    public final String title;
    public final String content;

    public IdeaSketch(
        @NotNull String title,
        @NotNull String content
    ) {
        this.title = title;
        this.content = content;
    }

    Map<String, Object> toMap() {
        final HashMap<String, Object> map = new HashMap<>();

        map.put(TITLE_PROP_NAME, title);
        map.put(CONTENT_PROP_NAME, content);

        return map;
    }
}
