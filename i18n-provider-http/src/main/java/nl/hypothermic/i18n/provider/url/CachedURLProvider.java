package nl.hypothermic.i18n.provider.url;

import nl.hypothermic.i18n.api.exception.ExceptionInInitialize;
import nl.hypothermic.i18n.api.internal.InitializeException;
import nl.hypothermic.i18n.api.model.II18nFormat;
import nl.hypothermic.i18n.api.model.II18nProvider;
import nl.hypothermic.i18n.api.model.II18nResource;
import nl.hypothermic.i18n.api.model.ProviderException;
import nl.hypothermic.i18n.provider.url.methods.ApacheCommonsIOFetchMethod;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Locale;

public class CachedURLProvider implements II18nProvider {

    public static final IFetchMethod DEFAULT_FETCH_METHOD = new ApacheCommonsIOFetchMethod();

    private URL[] urls;
    private IFetchMethod fileReadMethod;
    private II18nFormat fileReadFormat;

    private final HashSet<II18nResource> cachedEntries = new HashSet<>();
    private boolean initialized = false;

    public CachedURLProvider() {
        this(new URL[] {}, DEFAULT_FETCH_METHOD, null);
    }

    public CachedURLProvider(URL url) {
        this(new URL[] { url }, DEFAULT_FETCH_METHOD, null);
    }

    public CachedURLProvider(URL[] urls) {
        this(urls, DEFAULT_FETCH_METHOD, null);
    }

    public CachedURLProvider(URL[] urls, IFetchMethod fileReadMethod) {
        this(urls, fileReadMethod, null);
    }

    public CachedURLProvider(URL[] urls, IFetchMethod fileReadMethod, II18nFormat fileReadFormat) {
        this.urls = urls;
        this.fileReadMethod = fileReadMethod;
        this.fileReadFormat = fileReadFormat;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void initialize() throws InitializeException {
        this.initialized = false;
        this.cachedEntries.clear();

        for (URL url : urls) {
            try {
                cachedEntries.addAll(fileReadFormat.format(fileReadMethod.fetch(url)));
            } catch (IOException e) {
                throw new ExceptionInInitialize(e);
            }
        }

        this.initialized = true;
    }

    @Override
    public II18nResource getResource(Locale locale, String key) throws ProviderException {
        if (!this.initialized) {
            throw new ProviderException("Provider not initialized");
        }
        for (II18nResource entry : cachedEntries) {
            if (entry.getLocale().equals(locale) &&
                    entry.getKey().equals(key)) {
                return entry;
            }
        }
        throw new ProviderException("Entry not found");
    }

    @Override
    public void setFileFormat(II18nFormat format) {
        this.fileReadFormat = format;
    }

    @Override
    public II18nFormat getFileFormat() {
        return fileReadFormat;
    }

    public URL[] getUrls() {
        return urls;
    }

    public void setUrls(URL[] urls) {
        this.urls = urls;
    }

    public IFetchMethod getFileReadMethod() {
        return fileReadMethod;
    }

    public void setFileReadMethod(IFetchMethod fileReadMethod) {
        this.fileReadMethod = fileReadMethod;
    }
}
