package nl.hypothermic.i18n;

public class ExceptionWithinBuilder extends RuntimeException {

    private Exception wrappedException;

    public ExceptionWithinBuilder(Exception wrappedException) {
        this.wrappedException = wrappedException;
    }

    public Exception getWrappedException() {
        return wrappedException;
    }
}
