package nl.hypothermic.i18n.examples.filesystem;

import nl.hypothermic.i18n.I18nBuilder;
import nl.hypothermic.i18n.SynchronousI18n;
import nl.hypothermic.i18n.api.model.I18n;
import nl.hypothermic.i18n.format.json.JsonI18nFormat;
import nl.hypothermic.i18n.provider.fs.CachedFileSystemProvider;

import java.io.File;
import java.util.Locale;

public class I18nExampleFilesystem {

    public static void main(String[] args) {
        new I18nExampleFilesystem().execute();
    }

    public static final String ERROR_MESSAGE = "Unable to get the value...";

    private final I18n i18n = I18nBuilder.newBuilder()
                                            .setImplementation(SynchronousI18n.class)
                                            .setFileFormat(new JsonI18nFormat())
                                            .setProvider(new CachedFileSystemProvider(new File("i18n-examples")))
                                            .setLocale(Locale.ENGLISH)
                                            .initializeProviderAndCatchExceptions()
                                            .buildAndCatchExceptions();

    public void execute() {
        // This should print "Welcome!" in console.
        System.out.println(i18n.getString("myapp.dialog.greeting", ERROR_MESSAGE));

        // Changing the locale at any time is not a problem
        i18n.setLocale(Locale.GERMAN);

        // This should print "Wilkommen!" in console.
        System.out.println(i18n.getString("myapp.dialog.greeting", ERROR_MESSAGE));
    }
}
