package nl.hypothermic.i18n.provider.url.methods;

import nl.hypothermic.i18n.provider.url.IFetchMethod;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Fetches content at the URL location using the Apache Commons IO {@link org.apache.commons.io.IOUtils} class.
 */
public class ApacheCommonsIOFetchMethod implements IFetchMethod {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private Charset charset;

    public ApacheCommonsIOFetchMethod() {
        this(DEFAULT_CHARSET);
    }

    public ApacheCommonsIOFetchMethod(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String fetch(URL url) throws IOException {
        return IOUtils.toString(url, charset);
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    static {
        // Fix "authorization" for some websites
        System.setProperty("http.agent", "i18n/1.0.0.0");
    }
}
