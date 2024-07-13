package com.qubacy.shareit.application._common.error.bus.impl;

import com.qubacy.shareit._test.rule.RxImmediateSchedulerRule;
import com.qubacy.shareit.application._common.error.bus._common.ErrorBus;
import com.qubacy.shareit.application._common.error.model.ErrorReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.disposables.Disposable;

public class ErrorBusImplTest {
    @ClassRule
    public static final RxImmediateSchedulerRule rxSchedulerRule = new RxImmediateSchedulerRule();

    private ErrorBusImpl _instance;

    @Before
    public void setup() {
        _instance = new ErrorBusImpl();
    }

    @Test
    public void errorReferenceIsPassedToListenerTest() {
        final ErrorReference expectedErrorReference = new ErrorReference(0);

        AtomicReference<ErrorReference> gottenErrorReference = new AtomicReference<>();

        final ErrorBus.Listener listener = errorReference -> {
            gottenErrorReference.set(errorReference);
        };

        _instance.setup(listener);
        _instance.emitError(expectedErrorReference);

        Assert.assertEquals(expectedErrorReference, gottenErrorReference.get());
    }

    @Test
    public void subscriptionIsDisposedAfterDisposeCallTest()
        throws NoSuchFieldException, IllegalAccessException
    {
        final Field subscriptionFieldReflection = ErrorBusImpl.class
            .getDeclaredField("_subscription");

        subscriptionFieldReflection.setAccessible(true);

        final boolean expectedInitIsDisposed = false;
        final boolean expectedFinalIsDisposed = true;

        _instance.setup(errorReference -> {});

        final boolean gottenInitIsDisposed = ((Disposable) subscriptionFieldReflection
            .get(_instance)).isDisposed();

        Assert.assertEquals(expectedInitIsDisposed, gottenInitIsDisposed);

        _instance.dispose();

        final boolean gottenFinalIsDisposed = ((Disposable) subscriptionFieldReflection
            .get(_instance)).isDisposed();

        Assert.assertEquals(expectedFinalIsDisposed, gottenFinalIsDisposed);
    }
}
