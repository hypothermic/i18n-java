package nl.hypothermic.i18n.provider.fs;

import nl.hypothermic.i18n.api.internal.InitializeException;
import nl.hypothermic.i18n.api.model.II18nFormat;
import nl.hypothermic.i18n.api.model.II18nProvider;
import nl.hypothermic.i18n.api.model.II18nResource;
import nl.hypothermic.i18n.api.model.ProviderException;
import nl.hypothermic.i18n.provider.fs.methods.ApacheCommonsIOFileReadMethod;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;

public class CachedFileSystemProvider implements II18nProvider {

    public static final String DEFAULT_PATH = ".",
                               DEFAULT_FILE_EXTENSION = "lrf";

    public static final IFileReadMethod DEFAULT_READ_METHOD = new ApacheCommonsIOFileReadMethod();

    private String[] fileExtensions;
    private File[] paths;
    private boolean recursive;
    private IFileReadMethod fileReadMethod;
    private II18nFormat fileReadFormat;

    private final HashSet<II18nResource> cachedEntries = new HashSet<>();
    private boolean initialized = false;

    /**
     * Creates a CachedFileSystemProvider with the default path
     * ({@value CachedFileSystemProvider#DEFAULT_PATH})
     */
    public CachedFileSystemProvider() {
        this(new File[] { new File(DEFAULT_PATH) },
             new String[] { DEFAULT_FILE_EXTENSION },
             false,
             DEFAULT_READ_METHOD);
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
             false,
             DEFAULT_READ_METHOD);
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
             recursive,
             DEFAULT_READ_METHOD);
    }

    /**
     * Creates a CachedFileSystemProvider with specified paths
     * and an option to scan recursively (in subdirectories)
     *
     * @param paths The paths to scan for resource files.
     * @param fileExtensions Which file extensions to scan for.
     * @param recursive Whether to scan recursively (in subdirectories) or not.
     * @param fileReadMethod Specifies how to read the files from the filesystem.
     */
    public CachedFileSystemProvider(File[] paths, String[] fileExtensions, boolean recursive, IFileReadMethod fileReadMethod) {
        this.paths = paths;
        this.fileExtensions = fileExtensions;
        this.recursive = recursive;
        this.fileReadMethod = fileReadMethod;
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
            try {
                cachedEntries.addAll(fileReadFormat.format(fileReadMethod.readFile(file)));
            } catch (IOException e) {
                throw new ExceptionInInitializerException(e);
            }
        }

        initialized = true;
    }

    @Override
    public II18nResource getResource(Locale locale, String key) throws ProviderException {
        if (!this.initialized) {
            throw new ProviderException("Provider not initialized");
        }
        for (II18nResource entry : cachedEntries) {
            if (entry.getLocale().equals(locale) &&
                entry.getKey().equals(key)) {
                return entry;
            }
        }
        throw new ProviderException("Entry not found");
    }

    public String[] getFileExtensions() {
        return fileExtensions;
    }

    public void setFileExtensions(String[] fileExtensions) {
        this.fileExtensions = fileExtensions;
    }

    public File[] getPaths() {
        return paths;
    }

    public void setPaths(File[] paths) {
        this.paths = paths;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public IFileReadMethod getFileReadMethod() {
        return fileReadMethod;
    }

    public void setFileReadMethod(IFileReadMethod fileReadMethod) {
        this.fileReadMethod = fileReadMethod;
    }
}
