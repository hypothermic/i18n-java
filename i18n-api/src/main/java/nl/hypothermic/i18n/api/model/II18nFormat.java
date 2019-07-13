package nl.hypothermic.i18n.api.model;

import java.util.Collection;

public interface II18nFormat {

    Collection<II18nResource> format(String input);

}
