package com.qubacy.shareit.application.ui.activity._common.page.auth.validator.password.impl;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PasswordValidatorImplTest {
    private PasswordValidatorImpl _instance;

    @Before
    public void setup() {
        _instance = new PasswordValidatorImpl();
    }

    @Test
    public void validateTest() {
        class TestCase {
            public final String password;
            public final boolean expectedIsValid;

            public TestCase(
                @NotNull String password,
                boolean expectedIsValid
            ) {
                this.password = password;
                this.expectedIsValid = expectedIsValid;
            }
        };

        final List<TestCase> testCases = List.of(
            new TestCase("", false),
            new TestCase(" ", false),
            new TestCase("qweqweq", false),
            new TestCase("qweqweq ", false),
            new TestCase("qweqweqw", true)
        );

        for (final TestCase testCase : testCases) {
            final boolean gottenIsValid = _instance.validate(testCase.password);

            Assert.assertEquals(testCase.expectedIsValid, gottenIsValid);
        }
    }
}
