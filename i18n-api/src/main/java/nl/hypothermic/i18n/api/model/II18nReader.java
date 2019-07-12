package nl.hypothermic.i18n.api.model;

import java.util.Collection;

public interface II18nReader {

    Collection<II18nResource> read(String input);

}
