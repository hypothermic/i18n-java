package nl.hypothermic.i18n.api.model;

import nl.hypothermic.i18n.api.internal.IInitializable;
import nl.hypothermic.i18n.api.internal.NonNull;

import java.util.Locale;

/**
 * An abstract base class that defines the standard methods for each I18n implementation.<br />
 * <br />
 * The internal workings of such implementations will very likely be different, but the user experience
 * will stay the same due to these standard methods being availible in every implementation.
 */
public abstract class I18n implements IInitializable {

    protected Locale locale;
    protected final II18nProvider provider;

    /**
     * Not (yet) documented due to internal use only.
     */
    public I18n(II18nProvider provider) {
        if (provider == null) {
            throw new NullPointerException();
        }
        this.provider = provider;
    }

    /**
     * Not (yet) documented due to internal use only.
     */
    public I18n(II18nProvider provider, Locale locale) {
        this(provider);
        this.locale = locale;
    }

    /**
     * Get the internationalized resource of the specified resource's <i>key</i>.<br />
     *<br />
     * If the value cannot be retrieved, a {@link nl.hypothermic.i18n.api.model.ProviderException} will be thrown.
     */
    public abstract II18nResource getResource(@NonNull II18nResource resource) throws ProviderException;

    /**
     * Get the internationalized resource of the specified resource's <i>key</i>.<br />
     *<br />
     * If the resource cannot be retrieved, the <i>defaultValue</i> will be returned.
     */
    public abstract II18nResource getResource(II18nResource resource, II18nResource defaultValue);

    /**
     * Get the internationalized resource of the specified <i>key</i>.<br />
     *<br />
     * If the value cannot be retrieved, a {@link nl.hypothermic.i18n.api.model.ProviderException} will be thrown.
     */
    public abstract II18nResource getResource(@NonNull String key) throws ProviderException;

    /**
     * Get the internationalized resource of the specified <i>key</i>.<br />
     *<br />
     * If the resource cannot be retrieved, the <i>defaultValue</i> will be returned.
     */
    public abstract II18nResource getResource(String key, II18nResource defaultValue);

    /**
     * Get the internationalized value of the specified resource's <i>key</i>.<br />
     *<br />
     * If the value cannot be retrieved, a {@link nl.hypothermic.i18n.api.model.ProviderException} will be thrown.
     */
    public abstract String getString(@NonNull II18nResource resource) throws ProviderException;

    /**
     * Get the internationalized value of the specified resource's <i>key</i>.<br />
     *<br />
     * If the value cannot be retrieved, the <i>defaultValue</i> will be returned.
     */
    public abstract String getString(II18nResource resource, String defaultValue);

    /**
     * Get the internationalized value of the specified <i>key</i>.<br />
     *<br />
     * If the value cannot be retrieved, a {@link nl.hypothermic.i18n.api.model.ProviderException} will be thrown.
     */
    public abstract String getString(@NonNull String key) throws ProviderException;

    /**
     * Get the internationalized value of the specified <i>key</i>.<br />
     *<br />
     * If the value cannot be retrieved, the <i>defaultValue</i> will be returned.
     */
    public abstract String getString(String key, String defaultValue);

    /**
     * Returns the current locale of the I18n instance.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Sets the current locale of the I18n instance.
     */
    public void setLocale(Locale locale) {
        if (this.locale.equals(locale)) {
            return;
        }
        this.locale = locale;
    }

    /**
     * Returns the provider of the I18n instance.
     */
    public II18nProvider getProvider() {
        return provider;
    }
}
