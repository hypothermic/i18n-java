package nl.hypothermic.i18n.api.implementation;

import nl.hypothermic.i18n.api.model.II18nFormat;
import nl.hypothermic.i18n.api.model.II18nResource;

import java.util.Collection;
import java.util.Collections;

public class NoopFormat implements II18nFormat {

    @Override
    public Collection<II18nResource> format(String input) {
        return Collections.emptyList();
    }
}
