package com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.title.impl;

import com.qubacy.shareit.application.ui._common.validator.Validator;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class IdeaTitleValidatorImpl implements Validator {
    @Inject
    public IdeaTitleValidatorImpl() {}

    @Override
    public @NotNull String regExp() {
        return "\\S+";
    }
}
