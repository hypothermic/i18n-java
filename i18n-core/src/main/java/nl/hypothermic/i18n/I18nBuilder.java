package nl.hypothermic.i18n;

import nl.hypothermic.i18n.api.internal.InitializeException;
import nl.hypothermic.i18n.api.model.I18n;
import nl.hypothermic.i18n.api.model.II18nFormat;
import nl.hypothermic.i18n.api.model.II18nProvider;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.function.Consumer;

public class I18nBuilder {

    /**
     * An alternative for calling <code>new I18nBuilder()</code>.
     */
    public static I18nBuilder newBuilder() {
        return new I18nBuilder();
    }

    public Class<? extends I18n> implementation;
    public II18nFormat format;
    public II18nProvider provider;
    public Locale locale;

    private final I18nBuilderFormatStage formatStage = new I18nBuilderFormatStage();
    private final I18nBuilderProviderStage providerStage = new I18nBuilderProviderStage();
    private final I18nBuilderFinalStage finalStage = new I18nBuilderFinalStage();

    public I18nBuilder() {

    }

    /**
     * Useful for "Advanced Builder" patterns using a lambda.
     */
    public I18nBuilderFinalStage with(Consumer<I18nBuilder> builderFunction) {
        builderFunction.accept(this);
        return finalStage;
    }

    public I18nBuilderFormatStage setImplementation(Class<? extends I18n> implementation) {
        this.implementation = implementation;
        return formatStage;
    }

    public I18nBuilderFormatStage setImplementation(String implClassName) throws ClassNotFoundException {
        return this.setImplementation((Class<? extends I18n>) Class.forName(implClassName));
    }

    public class I18nBuilderFormatStage {

        public I18nBuilderProviderStage setFileFormat(II18nFormat format) {
            I18nBuilder.this.format = format;
            return I18nBuilder.this.providerStage;
        }

        public I18nBuilderProviderStage setFileFormat(Class<? extends II18nFormat> formatClass) throws IllegalAccessException, InstantiationException {
            return this.setFileFormat(formatClass.newInstance());
        }

        public I18nBuilderProviderStage setFileFormat(Class<? extends II18nFormat> formatClass, Object... parameters) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
            return this.setFileFormat(formatClass.getDeclaredConstructor(getTypesForArgs(parameters)).newInstance(parameters));
        }
    }

    public class I18nBuilderProviderStage {

        public I18nBuilderFinalStage setProvider(II18nProvider provider) {
            I18nBuilder.this.provider = provider;
            provider.setFileFormat(format);
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

        public I18nBuilderFinalStage initializeProvider() throws InitializeException {
            if (!I18nBuilder.this.provider.isInitialized()) {
                I18nBuilder.this.provider.initialize();
            }
            return this;
        }

        public I18nBuilderFinalStage initializeProviderAndCatchExceptions() {
            try {
                return this.initializeProvider();
            } catch (InitializeException e) {
                throw new ExceptionWithinBuilder(e);
            }
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
                throw new ExceptionWithinBuilder(e);
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
