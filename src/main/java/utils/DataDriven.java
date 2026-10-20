package utils;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

public class DataDriven {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static JsonNode jsonReader(){
        try(InputStream input = new FileInputStream("C:\\Users\\ahmed\\IdeaProjects\\SwagLabsAutomationAssignment\\src\\testData\\testData.json")) {

            return MAPPER.readTree(input);
        }catch(Exception e){
            throw new RuntimeException("Could not read testData.json",e);
        }
    }
}
