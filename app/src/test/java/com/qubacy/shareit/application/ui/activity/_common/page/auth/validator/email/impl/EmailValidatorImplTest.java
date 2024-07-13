package com.qubacy.shareit.application.ui.activity._common.page.auth.validator.email.impl;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class EmailValidatorImplTest {
    private EmailValidatorImpl _instance;

    @Before
    public void setup() {
        _instance = new EmailValidatorImpl();
    }

    @Test
    public void validateTest() {
        class TestCase {
            public final String email;
            public final boolean expectedIsValid;

            public TestCase(
                @NotNull String email,
                boolean expectedIsValid
            ) {
                this.email = email;
                this.expectedIsValid = expectedIsValid;
            }
        };

        final List<TestCase> testCases = List.of(
            new TestCase("", false),
            new TestCase("email", false),
            new TestCase("email@", false),
            new TestCase("email@mail", false),
            new TestCase("email@mail.", false),
            new TestCase("email@mail.com", true),
            new TestCase("@mail.com", false),
            new TestCase("email@.com", false),
            new TestCase("emailmail.com", false)
        );

        for (final TestCase testCase : testCases) {
            final boolean gottenIsValid = _instance.validate(testCase.email);

            Assert.assertEquals(testCase.expectedIsValid, gottenIsValid);
        }
    }
}
