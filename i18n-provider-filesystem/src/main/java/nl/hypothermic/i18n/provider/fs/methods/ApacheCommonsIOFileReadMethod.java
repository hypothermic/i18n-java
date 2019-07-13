package nl.hypothermic.i18n.provider.fs.methods;

import nl.hypothermic.i18n.provider.fs.IFileReadMethod;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Reads a file using the Apache Commons IO {@link org.apache.commons.io.FileUtils} class.
 */
public class ApacheCommonsIOFileReadMethod implements IFileReadMethod {

    @Override
    public String readFile(File file) throws IOException {
        return FileUtils.readFileToString(file, Charset.defaultCharset());
    }
}
