package com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.content.impl;

import com.qubacy.shareit.application.ui._common.validator.Validator;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class IdeaContentValidatorImpl implements Validator {
    @Inject
    public IdeaContentValidatorImpl() {}

    @Override
    public @NotNull String regExp() {
        return "\\S+";
    }
}
