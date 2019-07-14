package nl.hypothermic.i18n;

import nl.hypothermic.i18n.api.model.II18nFormat;
import nl.hypothermic.i18n.api.model.II18nProvider;
import org.reflections.Reflections;

import java.util.Collection;

public class ModuleSystem {

    private static final Reflections REFLECTIONS = new Reflections("nl.hypothermic.i18n");

    private ModuleSystem() {

    }

    public static Collection<Class<? extends II18nFormat>> getFormats() {
        return REFLECTIONS.getSubTypesOf(II18nFormat.class);
    }

    public static Collection<Class<? extends II18nProvider>> getProviders() {
        return REFLECTIONS.getSubTypesOf(II18nProvider.class);
    }
}
