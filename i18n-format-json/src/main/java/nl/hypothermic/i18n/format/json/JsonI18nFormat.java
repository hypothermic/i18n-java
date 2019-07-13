package nl.hypothermic.i18n.format.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import nl.hypothermic.i18n.api.model.II18nFormat;
import nl.hypothermic.i18n.api.model.II18nResource;

import java.lang.reflect.Type;
import java.util.Collection;

public class JsonI18nFormat implements II18nFormat {

    public static final Type COLLECTION_TYPE = new TypeToken<Collection<II18nResource>>(){}.getType();

    private final Gson gson;

    public JsonI18nFormat() {
        this.gson = new GsonBuilder().registerTypeAdapter(JsonI18nFormat.COLLECTION_TYPE, new JsonI18nResourceSerializer()).create();
    }

    public JsonI18nFormat(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Collection<II18nResource> format(String input) {
        return gson.fromJson(input, COLLECTION_TYPE);
    }
}
