package model;

import java.util.Timer;
import java.util.TimerTask;

//Class representing a plant
public class Plant {

    private static final int MAX_GROWTH_STAGE = 4;
    private static final long GROWTH_INTERVAL = 5000;

    private String breed;           // breed of plant in pot
    private String label;           // what the plant pot's label reads
    private int growthStage;        // the potted plant's current growth state
    private boolean isThirsty;      // true if plant is thirsty, false if not


    //EFFECTS : create a plant of breed breed and label breed at growth stage of zero and is thirsty
    public Plant(String breed) {
        this.breed = breed;
        this.label = breed;
        this.growthStage = 0;
        this.isThirsty = true;
    }

    //EFFECTS : create a plant with breed, label, growthStage and isThirsty
    public Plant(String breed, String label, int growthStage, boolean isThirsty) {
        this.breed = breed;
        this.label = label;
        this.growthStage = growthStage;
        this.isThirsty = isThirsty;
    }

    //MODIFIES : this
    //EFFECTS  : plant is not thirsty for the next GROWTH_INTERVAL
    //           plant stage increases by 1 and is thirsty when interval passes after watering
    public void addWater() {
        if (this.isThirsty) {
            this.isThirsty = false;
            Timer growthTimer = new Timer();
            TimerTask growthTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (growthStage < MAX_GROWTH_STAGE) {
                        growthStage++;
                        isThirsty = true;
                    }
                }
            };
            growthTimer.schedule(growthTimerTask, GROWTH_INTERVAL);
        }
    }

    //MODIFIES : this
    //EFFECTS  : renames label of plant
    public void rename(String label) {
        this.label = label;
    }

    //EFFECTS  : returns the breed of plant, "" if empty
    public String getBreed() {
        return this.breed;
    }

    //EFFECTS  : returns the current label of the plant
    public String getLabel() {
        return this.label;
    }

    //EFFECTS  : returns the current growth state of the plant
    public int getGrowthStage() {
        return this.growthStage;
    }

    //EFFECTS  : returns whether plant is thirsty or not
    public Boolean getIsThirsty() {
        return this.isThirsty;
    }
}
