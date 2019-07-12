package nl.hypothermic.i18n.format.json;

import com.google.gson.*;
import nl.hypothermic.i18n.api.implementation.SimpleI18nResource;
import nl.hypothermic.i18n.api.model.II18nResource;
import nl.hypothermic.i18n.api.model.IModifiableI18nResource;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

public class I18nResourceSerializer implements JsonSerializer<Collection<II18nResource>>, JsonDeserializer<Collection<II18nResource>> {

    private final Class<? extends IModifiableI18nResource> resourceSubclass;

    public I18nResourceSerializer() {
        resourceSubclass = SimpleI18nResource.class;
    }

    public I18nResourceSerializer(Class<? extends IModifiableI18nResource> resourceSubclass) {
        this.resourceSubclass = resourceSubclass;
    }

    @Override
    public JsonElement serialize(Collection<II18nResource> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        // TODO un-hardcode locale
        jsonObject.add("locale", new JsonPrimitive(Locale.ENGLISH.getLanguage()));

        JsonArray array = new JsonArray();

        for (II18nResource resource : src) {
            JsonObject object = new JsonObject();
            object.add("key", new JsonPrimitive(resource.getKey()));
            object.add("value", new JsonPrimitive(resource.getValue()));
            array.add(object);
        }

        jsonObject.add("entries", array);
        return jsonObject;
    }

    @Override
    public Collection<II18nResource> deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        HashSet<II18nResource> resources = new HashSet<>();

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        final Locale locale = Locale.forLanguageTag(jsonObject.get("locale").getAsJsonPrimitive().getAsString());

        JsonArray array = jsonObject.get("entries").getAsJsonArray();
        for (JsonElement element : array) {
            // TODO clean this
            IModifiableI18nResource resource;
            try {
                resource = resourceSubclass.newInstance();
            } catch (Exception e) {
                resource = new SimpleI18nResource();
            }

            resource.setKey(jsonObject.get("key").getAsString());
            resource.setValue(jsonObject.get("value").getAsString());

            resources.add(resource);
        }
        return resources;
    }
}
