package com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.title.impl;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class IdeaTitleValidatorImplTest {
    private IdeaTitleValidatorImpl _instance;

    @Before
    public void setup() {
        _instance = new IdeaTitleValidatorImpl();
    }

    @Test
    public void validateTest() {
        class TestCase {
            public final String title;
            public final boolean expectedIsValid;

            public TestCase(
                @NotNull String title,
                boolean expectedIsValid
            ) {
                this.title = title;
                this.expectedIsValid = expectedIsValid;
            }
        };

        final List<TestCase> testCases = List.of(
            new TestCase("", false),
            new TestCase(" ", false),
            new TestCase("i", true)
        );

        for (final TestCase testCase : testCases) {
            final boolean gottenIsValid = _instance.validate(testCase.title);

            Assert.assertEquals(testCase.expectedIsValid, gottenIsValid);
        }
    }
}
