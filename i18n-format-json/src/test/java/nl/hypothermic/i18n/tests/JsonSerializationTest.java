package nl.hypothermic.i18n.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nl.hypothermic.i18n.api.implementation.SimpleI18nResource;
import nl.hypothermic.i18n.api.model.II18nResource;
import nl.hypothermic.i18n.format.json.JsonI18nResourceSerializer;
import nl.hypothermic.i18n.format.json.JsonI18nReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

/**
 * These tests are not the cleanest and fastest but they provide real world examples.
 */
public class JsonSerializationTest {

    /**
     * Tests the lower-level Gson serializer directly
     */
    @Test
    public void testGsonSerialization() {
        Gson gson = new GsonBuilder().registerTypeAdapter(JsonI18nReader.COLLECTION_TYPE, new JsonI18nResourceSerializer()).create();

        HashSet<SimpleI18nResource> resources = new HashSet<>();
        resources.add(new SimpleI18nResource(Locale.ENGLISH,         "myapp.dialog.greeting", "Welcome!"    ));
        resources.add(new SimpleI18nResource(Locale.GERMAN,          "myapp.dialog.greeting", "Wilkommen!"  ));

        Collection<II18nResource> deserialized = gson.fromJson(gson.toJson(resources, JsonI18nReader.COLLECTION_TYPE), JsonI18nReader.COLLECTION_TYPE);

        for (II18nResource resource : deserialized) {
            verifySampleDataResource(resource);
        }
    }

    /**
     * Tests the higher-level JsonI18nReader implementation
     */
    @Test
    public void testWrappedSerialization() {
        final String sampleInputData = "{\"entries\":[{\"key\":\"myapp.dialog.greeting\",\"value\":\"Wilkommen!\",\"locale\":\"de\"},{\"key\":\"myapp.dialog.greeting\",\"value\":\"Welcome!\",\"locale\":\"en\"}]}";

        JsonI18nReader reader = new JsonI18nReader();

        for (II18nResource resource : reader.read(sampleInputData)) {
            verifySampleDataResource(resource);
        }
    }

    // Hardcoded strings + messy code = hope nobody sees this
    private static void verifySampleDataResource(II18nResource resource) {
        Assert.assertNotNull("Locale is null", resource.getLocale());
        if (resource.getLocale() != Locale.ENGLISH &&
                resource.getLocale() != Locale.GERMAN) {
            throw new AssertionError("Locale is unexpected: " + resource.getLocale().getDisplayName());
        }

        Assert.assertNotNull("Key is null", resource.getKey());
        Assert.assertEquals("Key is wrong", resource.getKey(), "myapp.dialog.greeting");

        Assert.assertNotNull("Value is null", resource.getValue());
        if (!resource.getValue().equals("Welcome!") &&
                !resource.getValue().equals("Wilkommen!")) {
            throw new AssertionError("Value is unexpected: " + resource.getValue());
        }
    }
}
