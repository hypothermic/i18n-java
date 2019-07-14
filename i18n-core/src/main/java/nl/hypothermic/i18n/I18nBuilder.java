package nl.hypothermic.i18n;

import nl.hypothermic.i18n.api.model.I18n;
import nl.hypothermic.i18n.api.model.II18nProvider;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.function.Consumer;

public class I18nBuilder {

    public static I18nBuilder newBuilder() {
        return new I18nBuilder();
    }

    public Class<? extends I18n> implementation;
    public II18nProvider provider;
    public Locale locale;

    private final I18nBuilderProviderStage providerStage = new I18nBuilderProviderStage();
    private final I18nBuilderFinalStage finalStage = new I18nBuilderFinalStage();

    public I18nBuilder() {

    }

    public I18nBuilderFinalStage with(Consumer<I18nBuilder> builderFunction) {
        builderFunction.accept(this);
        return finalStage;
    }

    public I18nBuilderProviderStage setImplementation(Class<? extends I18n> implementation) {
        this.implementation = implementation;
        return providerStage;
    }

    public I18nBuilderProviderStage setImplementation(String implClassName) throws ClassNotFoundException {
        return this.setImplementation((Class<? extends I18n>) Class.forName(implClassName));
    }

    public class I18nBuilderProviderStage {

        public I18nBuilderFinalStage setProvider(II18nProvider provider) {
            I18nBuilder.this.provider = provider;
            return finalStage;
        }

        public I18nBuilderFinalStage setProvider(Class<? extends II18nProvider> providerClass) throws IllegalAccessException, InstantiationException {
            return this.setProvider(providerClass.newInstance());
        }

        public I18nBuilderFinalStage setProvider(Class<? extends II18nProvider> providerClass, Object... parameters) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
            return this.setProvider(providerClass.getDeclaredConstructor(getTypesForArgs(parameters)).newInstance(parameters));
        }

        public I18nBuilderFinalStage setProvider(String providerClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
            return this.setProvider((Class<? extends II18nProvider>) Class.forName(providerClassName));
        }

        public I18nBuilderFinalStage setProvider(String providerClassName, Object... parameters) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
            return this.setProvider((Class<? extends II18nProvider>) Class.forName(providerClassName), parameters);
        }
    }

    public class I18nBuilderFinalStage {

        public I18nBuilderFinalStage with(Consumer<I18nBuilderFinalStage> builderFunction) {
            builderFunction.accept(this);
            return this;
        }

        public I18nBuilderFinalStage setLocale(Locale locale) {
            I18nBuilder.this.locale = locale;
            return this;
        }

        public I18n build() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
            I18n result = null;

            if (locale != null) {
                result = implementation.getDeclaredConstructor(II18nProvider.class, Locale.class).newInstance(provider, locale);
            } else {
                result = implementation.getDeclaredConstructor(II18nProvider.class).newInstance(provider);
            }

            return result;
        }

        public I18n buildAndCatchExceptions() {
            try {
                return this.build();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    public static Class<?>[] getTypesForArgs(Object... args) {
        Class<?>[] classes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            classes[i] = args[i].getClass();
        }
        return classes;
    }
}
