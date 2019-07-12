package nl.hypothermic.i18n.api.model;

import nl.hypothermic.i18n.api.internal.IInitializable;

import java.util.Locale;

public interface II18nProvider extends IInitializable {

    II18nResource getResource(Locale locale, String key) throws ProviderException;

}
