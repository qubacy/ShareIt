package com.qubacy.shareit.application.ui._common.validator;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validator {
    @NotNull
    String regExp();

    default boolean validate(@NotNull String data) {
        Pattern pattern = Pattern.compile(regExp());
        Matcher matcher = pattern.matcher(data);

        return matcher.find();
    }
}
