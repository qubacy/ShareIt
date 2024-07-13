package com.qubacy.shareit.application._common.error.model;

import com.qubacy.shareit.application.data.error.repository.source.local.database._common.view.ErrorDatabaseView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ShareItErrorTest {
    @Test
    public void fromErrorDatabaseViewTest() {
        class TestCase {
            public final ErrorDatabaseView errorDatabaseView;
            public final String cause;
            public final ShareItError expectedShareItError;

            public TestCase(
                @NotNull ErrorDatabaseView errorDatabaseView,
                @Nullable String cause,
                @NotNull ShareItError expectedShareItError
            ) {
                this.errorDatabaseView = errorDatabaseView;
                this.cause = cause;
                this.expectedShareItError = expectedShareItError;
            }
        };

        final ErrorDatabaseView errorDatabaseView =
            new ErrorDatabaseView(0, "ts", "test", false);

        final List<TestCase> testCases = List.of(
            new TestCase(
                errorDatabaseView,
                null,
                new ShareItError(
                    errorDatabaseView.id,
                    errorDatabaseView.localization,
                    errorDatabaseView.isCritical
                )
            ),
            new TestCase(
                errorDatabaseView,
                "test cause",
                new ShareItError(
                    errorDatabaseView.id,
                    "test cause",
                    errorDatabaseView.isCritical
                )
            )
        );

        for (final TestCase testCase : testCases) {
            final ShareItError gottenShareItError = ShareItError
                .fromErrorDatabaseView(testCase.errorDatabaseView, testCase.cause);

            Assert.assertEquals(testCase.expectedShareItError, gottenShareItError);
        }
    }
}
