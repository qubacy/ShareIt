package com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.content.impl;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class IdeaContentValidatorImplTest {
    private IdeaContentValidatorImpl _instance;

    @Before
    public void setup() {
        _instance = new IdeaContentValidatorImpl();
    }

    @Test
    public void validateTest() {
        class TestCase {
            public final String content;
            public final boolean expectedIsValid;

            public TestCase(
                @NotNull String content,
                boolean expectedIsValid
            ) {
                this.content = content;
                this.expectedIsValid = expectedIsValid;
            }
        };

        final List<TestCase> testCases = List.of(
            new TestCase("", false),
            new TestCase(" ", false),
            new TestCase("i", true)
        );

        for (final TestCase testCase : testCases) {
            final boolean gottenIsValid = _instance.validate(testCase.content);

            Assert.assertEquals(testCase.expectedIsValid, gottenIsValid);
        }
    }
}
