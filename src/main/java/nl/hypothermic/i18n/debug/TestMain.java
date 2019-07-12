package nl.hypothermic.i18n.debug;

import nl.hypothermic.i18n.SynchronousI18n;
import nl.hypothermic.i18n.api.model.I18n;
import nl.hypothermic.i18n.provider.fs.CachedFileSystemProvider;

public class TestMain {

    public static void main(String[] args) {
        I18n i18n = new SynchronousI18n(new CachedFileSystemProvider());

        TODO: TEST THE JSON (DE)SERIALIZER.
    }
}
