package nl.hypothermic.i18n.format.json;

import com.google.gson.*;
import nl.hypothermic.i18n.api.implementation.SimpleI18nResource;
import nl.hypothermic.i18n.api.model.II18nResource;
import nl.hypothermic.i18n.api.model.IModifiableI18nResource;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

public class JsonI18nResourceSerializer implements JsonSerializer<Collection<II18nResource>>, JsonDeserializer<Collection<II18nResource>> {

    private final Class<? extends IModifiableI18nResource> resourceSubclass;

    public JsonI18nResourceSerializer() {
        resourceSubclass = SimpleI18nResource.class;
    }

    public JsonI18nResourceSerializer(Class<? extends IModifiableI18nResource> resourceSubclass) {
        this.resourceSubclass = resourceSubclass;
    }

    @Override
    public JsonElement serialize(Collection<II18nResource> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        JsonArray array = new JsonArray();

        for (II18nResource resource : src) {
            JsonObject object = new JsonObject();

            object.add("key", new JsonPrimitive(resource.getKey()));
            object.add("value", new JsonPrimitive(resource.getValue()));
            object.add("locale", new JsonPrimitive(resource.getLocale().toLanguageTag()));

            array.add(object);
        }

        jsonObject.add("entries", array);
        return jsonObject;
    }

    @Override
    public Collection<II18nResource> deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        HashSet<II18nResource> resources = new HashSet<>();

        JsonArray array = jsonElement.getAsJsonObject().get("entries").getAsJsonArray();

        for (JsonElement arrayElement : array) {
            JsonObject arrayObject = (JsonObject) arrayElement;

            // TODO clean this and report the exception somewhere
            IModifiableI18nResource resource;
            try {
                resource = resourceSubclass.newInstance();
            } catch (Exception e) {
                resource = new SimpleI18nResource();
            }

            resource.setLocale(Locale.forLanguageTag(arrayObject.get("locale").getAsJsonPrimitive().getAsString()));
            resource.setKey(arrayObject.get("key").getAsString());
            resource.setValue(arrayObject.get("value").getAsString());

            resources.add(resource);
        }
        return resources;
    }
}
