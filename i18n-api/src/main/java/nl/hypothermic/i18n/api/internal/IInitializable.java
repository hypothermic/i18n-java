package nl.hypothermic.i18n.api.internal;

public interface IInitializable {

    boolean isInitialized();

    void initialize() throws InitializeException;

}
