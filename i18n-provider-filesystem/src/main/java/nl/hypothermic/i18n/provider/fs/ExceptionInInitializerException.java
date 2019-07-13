package nl.hypothermic.i18n.provider.fs;

import nl.hypothermic.i18n.api.internal.InitializeException;

/**
 * Signals that an exception was thrown in the <code>initialize()</code> method.<br />
 * <br />
 * Please check the wrapped exception for more information about the error.
 */
public class ExceptionInInitializerException extends InitializeException {

    private final Exception wrappedException;

    public ExceptionInInitializerException(Exception wrappedException) {
        this.wrappedException = wrappedException;
    }

    public Exception getWrappedException() {
        return wrappedException;
    }
}
