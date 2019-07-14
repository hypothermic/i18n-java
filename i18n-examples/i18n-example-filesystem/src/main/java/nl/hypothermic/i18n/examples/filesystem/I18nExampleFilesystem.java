package nl.hypothermic.i18n.examples.filesystem;

import nl.hypothermic.i18n.I18nBuilder;
import nl.hypothermic.i18n.SynchronousI18n;
import nl.hypothermic.i18n.api.model.I18n;
import nl.hypothermic.i18n.provider.fs.CachedFileSystemProvider;

public class I18nExampleFilesystem {

    public static void main(String[] args) {
        new I18nExampleFilesystem().execute();
    }

    private final I18n i18n = I18nBuilder.newBuilder()
                                            .setImplementation(SynchronousI18n.class)
                                            .setProvider(new CachedFileSystemProvider())
                                            .buildAndCatchExceptions();

    public void execute() {
        String myInternationalizedString = i18n.getString("myapp.dialog.greeting", "Unable to get the value...");

        System.out.println(myInternationalizedString);
    }
}
