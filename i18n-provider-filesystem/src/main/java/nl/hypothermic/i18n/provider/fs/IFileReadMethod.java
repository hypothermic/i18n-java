package nl.hypothermic.i18n.provider.fs;

import java.io.File;
import java.io.IOException;

public interface IFileReadMethod {

    String readFile(File file) throws IOException;

}
