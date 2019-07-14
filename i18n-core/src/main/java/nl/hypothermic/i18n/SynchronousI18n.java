package nl.hypothermic.i18n;

import nl.hypothermic.i18n.api.internal.InitializeException;
import nl.hypothermic.i18n.api.model.I18n;
import nl.hypothermic.i18n.api.model.II18nProvider;
import nl.hypothermic.i18n.api.model.II18nResource;
import nl.hypothermic.i18n.api.model.ProviderException;

import java.util.Locale;

public class SynchronousI18n extends I18n {

    private static II18nResource catchExceptions(II18nProvider provider, Locale locale, String key, II18nResource defaultValue) {
        try {
            return provider.getResource(locale, key);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    private static String catchExceptions(II18nProvider provider, Locale locale, String key, String defaultValue) {
        try {
            return provider.getResource(locale, key).getValue();
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public SynchronousI18n(II18nProvider provider) {
        super(provider);
    }

    public SynchronousI18n(II18nProvider provider, Locale locale) {
        super(provider, locale);
    }

    @Override
    public boolean isInitialized() {
        return super.provider.isInitialized();
    }

    @Override
    public void initialize() throws InitializeException {
        super.provider.initialize();
    }

    @Override
    public II18nResource getResource(II18nResource resource) throws ProviderException {
        return super.provider.getResource(super.locale, resource.getKey());
    }

    @Override
    public II18nResource getResource(II18nResource resource, II18nResource defaultValue) {
        return catchExceptions(super.provider, super.locale, resource.getKey(), defaultValue);
    }

    @Override
    public II18nResource getResource(String key) throws ProviderException {
        return super.provider.getResource(super.locale, key);
    }

    @Override
    public II18nResource getResource(String key, II18nResource defaultValue) {
        return catchExceptions(super.provider, super.locale, key, defaultValue);
    }

    @Override
    public String getString(II18nResource resource) throws ProviderException {
        return super.provider.getResource(locale, resource.getKey()).getValue();
    }

    @Override
    public String getString(II18nResource resource, String defaultValue) {
        return catchExceptions(super.provider, super.locale, resource.getKey(), defaultValue);
    }

    @Override
    public String getString(String key) throws ProviderException {
        return super.provider.getResource(locale, key).getValue();
    }

    @Override
    public String getString(String key, String defaultValue) {
        return catchExceptions(super.provider, super.locale, key, defaultValue);
    }
}
