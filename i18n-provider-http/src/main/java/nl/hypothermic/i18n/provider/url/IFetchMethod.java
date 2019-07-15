package nl.hypothermic.i18n.provider.url;

import java.io.IOException;
import java.net.URL;

public interface IFetchMethod {

    String fetch(URL url) throws IOException;

}
