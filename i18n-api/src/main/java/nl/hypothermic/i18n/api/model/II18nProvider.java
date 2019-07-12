package nl.hypothermic.i18n.api.model;

import nl.hypothermic.i18n.api.stages.IInitializable;

public interface II18nProvider extends IInitializable {

    II18nResource getResource() throws ProviderException;

}
