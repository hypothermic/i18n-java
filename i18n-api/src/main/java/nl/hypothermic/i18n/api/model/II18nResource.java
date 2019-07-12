package nl.hypothermic.i18n.api.model;

import java.util.Locale;

public interface II18nResource {

    Locale getLocale();

    String getKey();

    String getValue();

}
