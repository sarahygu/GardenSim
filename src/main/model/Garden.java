package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.HashMap;
import java.util.Map;

//Class representing a garden
public class Garden implements Writable {

    private HashMap<Integer, Plant> garden;

    // EFFECTS : creates a new garden
    public Garden() {
        garden = new HashMap<>();
    }

    //MODIFIES : this
    // EFFECTS : adds a new plant to specified key int
    public void addPlant(int k, String breed) {
        garden.put(k, new Plant(breed));
    }

    //MODIFIES : this
    // EFFECTS : adds a specified plant to specified key int
    public void addPlant(int k, Plant plant) {
        garden.put(k, plant);
    }

    // MODIFIES: this
    // EFFECTS:  removes a plant from the list from key int
    public void removePlant(int k) {
        garden.remove(k);
    }

    // EFFECTS: returns the plant from key int
    public Plant getPlant(int k) {
        return garden.get(k);
    }

    // EFFECTS: return number of plants in garden
    public int getSize() {
        return garden.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("plants", plantsToJson());
        return json;
    }

    // EFFECTS: returns garden as a JSON array
    private JSONArray plantsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Integer, Plant> entry : garden.entrySet()) {
            Plant plant = entry.getValue();
            Integer key = entry.getKey();
            jsonArray.put(plantToJson(key, plant));;
        }
        return jsonArray;
    }

    public JSONObject plantToJson(Integer plot, Plant plant) {
        JSONObject json = new JSONObject();
        json.put("plot", plot);
        json.put("breed", plant.getBreed());
        json.put("label", plant.getLabel());
        json.put("growthStage", plant.getGrowthStage());
        json.put("isThirsty", plant.getIsThirsty());
        return json;
    }
}

