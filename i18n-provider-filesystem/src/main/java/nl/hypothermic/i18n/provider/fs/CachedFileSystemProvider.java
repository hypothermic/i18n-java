package nl.hypothermic.i18n.provider.fs;

import nl.hypothermic.i18n.api.internal.InitializeException;
import nl.hypothermic.i18n.api.model.II18nProvider;
import nl.hypothermic.i18n.api.model.II18nResource;
import nl.hypothermic.i18n.api.model.ProviderException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class CachedFileSystemProvider implements II18nProvider {

    public static final String DEFAULT_PATH = ".",
                               DEFAULT_FILE_EXTENSION = "lrf";

    private String[] fileExtensions;
    private File[] paths;
    private boolean recursive;

    private final HashMap<String, String> cachedEntries = new HashMap<>();
    private boolean initialized = false;

    /**
     * Creates a CachedFileSystemProvider with the default path
     * ({@value CachedFileSystemProvider#DEFAULT_PATH})
     */
    public CachedFileSystemProvider() {
        this(new File[] { new File(DEFAULT_PATH) },
             new String[] { DEFAULT_FILE_EXTENSION },
             false);
    }

    /**
     * Creates a CachedFileSystemProvider with specified paths.<br>
     * Subdirectories will not be scanned.
     *
     * @param paths The paths to scan for resource files.
     */
    public CachedFileSystemProvider(File[] paths) {
        this(paths,
             new String[] { DEFAULT_FILE_EXTENSION },
             false);
    }

    /**
     * Creates a CachedFileSystemProvider with specified paths
     * and an option to scan recursively (in subdirectories)
     *
     * @param paths The paths to scan for resource files.
     * @param recursive Whether to scan recursively (in subdirectories) or not.
     */
    public CachedFileSystemProvider(File[] paths, boolean recursive) {
        this(paths,
             new String[] { DEFAULT_FILE_EXTENSION },
             recursive);
    }

    /**
     * Creates a CachedFileSystemProvider with specified paths
     * and an option to scan recursively (in subdirectories)
     *
     * @param paths The paths to scan for resource files.
     * @param recursive Whether to scan recursively (in subdirectories) or not.
     */
    public CachedFileSystemProvider(File[] paths, String[] fileExtensions, boolean recursive) {
        this.paths = paths;
        this.fileExtensions = fileExtensions;
        this.recursive = recursive;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void initialize() throws InitializeException {
        initialized = false;
        cachedEntries.clear();

        HashSet<File> files = new HashSet<>();
        for (File path : paths) {
            files.addAll(FileUtils.listFiles(path, fileExtensions, recursive));
        }

        for (File file : files) {

        }

        initialized = true;
    }

    @Override
    public II18nResource getResource(Locale locale, String key) throws ProviderException {
        return null;
    }
}
