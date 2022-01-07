package persistence;

import model.Garden;
import model.Plant;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents operator to read and write JSON data
// this class is heavily inspired by the JsonSerializationDemo from the UBC repository
public class JsonOperator {
    private static final int INDENT_SPACES = 4;
    private PrintWriter writer;
    private String jsonFile;

    // EFFECTS: constructs operator to read and write with a json file
    public JsonOperator(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void openFile() throws FileNotFoundException {
        writer = new PrintWriter(new File(jsonFile));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void closeFile() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of garden to file
    public void write(Garden garden) {
        JSONObject json = garden.toJson();
        writer.print(json.toString(INDENT_SPACES));
    }





    // EFFECTS: reads JSON representation of garden from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Garden read() throws IOException {

        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(jsonFile), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        String jsonData = contentBuilder.toString();

        JSONObject jsonObject = new JSONObject(jsonData);

        return parseGarden(jsonObject);
    }

    //MODIFIES: garden
    // EFFECTS: parses garden from JSON object and returns it
    private Garden parseGarden(JSONObject jsonObject) {
        Garden garden = new Garden();
        JSONArray jsonArray = jsonObject.getJSONArray("plants");
        for (Object json : jsonArray) {
            JSONObject nextPlant = (JSONObject) json;
            addJsonPlant(garden, nextPlant);
        }
        return garden;
    }

    //MODIFIES: garden
    // EFFECTS: parses plant from JSON object and adds it to garden
    private void addJsonPlant(Garden garden, JSONObject jsonObject) {
        Integer k = jsonObject.getInt("plot");
        String breed = jsonObject.getString("breed");
        String label = jsonObject.getString("label");
        Integer growthStage = jsonObject.getInt("growthStage");
        boolean isThirsty = true;
        garden.addPlant(k, new Plant(breed, label, growthStage, isThirsty));

    }
}
