package nl.hypothermic.i18n.api.exception;

import nl.hypothermic.i18n.api.internal.InitializeException;

/**
 * Signals that an exception was thrown in the <code>initialize()</code> method.<br />
 * <br />
 * Please check the wrapped exception for more information about the error.
 */
public class ExceptionInInitialize extends InitializeException {

    private final Exception wrappedException;

    public ExceptionInInitialize(Exception wrappedException) {
        this.wrappedException = wrappedException;
    }

    public Exception getWrappedException() {
        return wrappedException;
    }
}
