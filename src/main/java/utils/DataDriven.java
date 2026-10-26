package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataDriven {

    private static final String JSON_PATH = "C:\\Users\\ahmed\\IdeaProjects\\SwagLabsAutomationAssignment\\src\\testData\\testData.json";

    public static JsonObject jsonReader() {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException("Could not read JSON file: " + JSON_PATH, e);
        }
    }


    public static String getValue(String section, String key) {
        return jsonReader().getAsJsonObject(section).get(key).getAsString();
    }


    public static List<String> getCartProducts() {
        JsonArray array = jsonReader().getAsJsonArray("cartProducts");
        List<String> products = new ArrayList<>();
        for (JsonElement element : array) {
            products.add(element.getAsString());
        }
        return products;
    }
}
