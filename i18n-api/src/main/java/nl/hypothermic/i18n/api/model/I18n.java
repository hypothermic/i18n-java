package nl.hypothermic.i18n.api.model;

import nl.hypothermic.i18n.api.internal.IInitializable;

import java.util.Locale;

public abstract class I18n implements IInitializable {

    protected Locale locale;
    protected final II18nProvider provider;

    public I18n(II18nProvider provider) {
        if (provider == null) {
            throw new NullPointerException();
        }
        this.provider = provider;
    }

    public I18n(II18nProvider provider, Locale locale) {
        this(provider);
        this.locale = locale;
    }

    public abstract II18nResource getResource(II18nResource resource) throws ProviderException;

    public abstract II18nResource getResource(II18nResource resource, II18nResource defaultValue);

    public abstract II18nResource getResource(String key) throws ProviderException;

    public abstract II18nResource getResource(String key, II18nResource defaultValue);

    public abstract String getString(II18nResource resource) throws ProviderException;

    public abstract String getString(II18nResource resource, String defaultValue);

    public abstract String getString(String key) throws ProviderException;

    public abstract String getString(String key, String defaultValue);

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public II18nProvider getProvider() {
        return provider;
    }
}
