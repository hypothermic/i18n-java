package nl.hypothermic.i18n.debug;

import nl.hypothermic.i18n.I18nBuilder;
import nl.hypothermic.i18n.SynchronousI18n;
import nl.hypothermic.i18n.api.model.I18n;
import nl.hypothermic.i18n.format.json.JsonI18nFormat;
import nl.hypothermic.i18n.provider.url.CachedURLProvider;

import java.net.URL;
import java.util.Locale;

/**
 * A class used by developers to test their changes to the code.<br />
 * <br />
 * Not to be used in production.
 */
public class TestMain {

    public static final String ERROR_MESSAGE = "Unable to get the value...";

    public static void main(String[] args) throws Exception {

        I18n i18n = I18nBuilder.newBuilder().setImplementation(SynchronousI18n.class)
                                            .setFileFormat(new JsonI18nFormat())
                                            .setProvider(new CachedURLProvider(new URL("https://www.hypothermic.nl/dev/i18n-example-json.lrf")))
                                            .initializeProviderAndCatchExceptions()
                                            .buildAndCatchExceptions();

        // This should print "Welcome!" in console.
        System.out.println(i18n.getString("myapp.dialog.greeting", ERROR_MESSAGE));

        // Changing the locale at any time is not a problem
        i18n.setLocale(Locale.GERMAN);

        // This should print "Wilkommen!" in console.
        System.out.println(i18n.getString("myapp.dialog.greeting", ERROR_MESSAGE));
    }
}
