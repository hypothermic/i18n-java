package nl.hypothermic.i18n.api.implementation;

import nl.hypothermic.i18n.api.model.II18nResource;
import nl.hypothermic.i18n.api.model.IModifiableI18nResource;

import java.util.Locale;

public class SimpleI18nResource implements II18nResource, IModifiableI18nResource {

    private Locale locale;
    private String key, value;

    public SimpleI18nResource() {

    }

    public SimpleI18nResource(Locale locale, String key, String value) {
        this.locale = locale;
        this.key = key;
        this.value = value;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
