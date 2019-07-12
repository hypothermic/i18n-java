package nl.hypothermic.i18n.format.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import nl.hypothermic.i18n.api.model.II18nReader;
import nl.hypothermic.i18n.api.model.II18nResource;

import java.lang.reflect.Type;
import java.util.Collection;

public class JsonI18nReader implements II18nReader {

    public static final Type COLLECTION_TYPE = new TypeToken<Collection<II18nResource>>(){}.getType();

    private final Gson gson;

    public JsonI18nReader() {
        this.gson = new GsonBuilder().registerTypeAdapter(II18nResource.class, new I18nResourceSerializer()).create();
    }

    public JsonI18nReader(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Collection<II18nResource> read(String input) {
        return gson.fromJson(input, COLLECTION_TYPE);
    }
}
