package nl.hypothermic.i18n.api.model;

import java.util.Locale;

public interface IModifiableI18nResource extends II18nResource {

    void setLocale(Locale locale);

    void setKey(String key);

    void setValue(String value);

}
