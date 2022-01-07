package ui;

import model.*;
import persistence.JsonOperator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Represents a Garden Simulator application
public class GardenSim {

    private static final String JSON_STORE = "./data/garden.json";

    private Garden garden;
    private Scanner input;
    private JsonOperator writer;
    private JsonOperator reader;


    String command = null;
    private int plotPos = 0;

    // EFFECTS: runs the GardenSim application
    public GardenSim() {
        runGardenSim();
    }

    // MODIFIES: this
    // EFFECTS: start the application
    private void runGardenSim() {
        boolean keepRunning = true;

        init();

        while (keepRunning) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepRunning = false;
            } else {
                processMainMenuCommand(command);
            }
        }

        System.out.println("\n Come visit soon!");
    }

    // MODIFIES: this
    // EFFECTS: initializes garden
    private void init() {
        garden = new Garden();
        input = new Scanner(System.in);
        writer = new JsonOperator(JSON_STORE);
        reader = new JsonOperator(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMainMenu() {
        System.out.println("\nWelcome to your garden!");
        System.out.println("\nPlease select a plot:");

        System.out.println("\t1 2 3");
        System.out.println("\ts -> save to file");
        System.out.println("\tl -> load from file");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: processes user command for main menu
    private void processMainMenuCommand(String command) {
        if (command.equals("1")) {
            displayMenuOptionsPlot(1);
        } else if (command.equals("2")) {
            displayMenuOptionsPlot(2);
        } else if (command.equals("3")) {
            displayMenuOptionsPlot(3);
        } else if (command.equals("s")) {
            saveGarden();
        } else if (command.equals("l")) {
            loadGarden();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: saves garden to file
    private void saveGarden() {
        try {
            writer.openFile();
            writer.write(garden);
            writer.closeFile();
            System.out.println("Your garden has been saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads garden from file
    private void loadGarden() {
        try {
            garden = reader.read();
            System.out.println("Your garden has been reloaded!");
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: displays options for selected plot
    private void displayMenuOptionsPlot(int plotPos) {
        this.plotPos = plotPos;
        Plant plant = garden.getPlant(plotPos);

        if (plant != null) {
            System.out.println("\nYou have selected plot: " + plotPos);
            System.out.println("\nIt contains: " + plant.getLabel());
            System.out.println("\nIt is a " + plant.getBreed() + " at growth stage " + plant.getGrowthStage() + ".");
            if (plant.getIsThirsty()) {
                System.out.println("It can be watered now.");
            } else {
                System.out.println("It cannot be watered at the moment.");
            }
        } else {
            System.out.println("There is nothing here.");
        }

        System.out.println("\nWhat would you like to do?");
        System.out.println("\ta -> add plant");
        System.out.println("\tw -> water plant");
        System.out.println("\tr -> remove plant");
        System.out.println("\tn -> rename plant");
        System.out.println("\tb -> go back");

        command = input.next();
        command = command.toLowerCase();

        processMenuOptionsPlot(command);
    }

    // MODIFIES: this
    // EFFECTS: processes user commands for selected pot
    private void processMenuOptionsPlot(String command) {
        if (command.equals("a")) {
            if (garden.getPlant(plotPos) == null) {
                displayMenuOptionsAddPlant();
            } else {
                System.out.println("There's already a plant here! Remove it first.");
                displayMenuOptionsPlot(plotPos);
            }
        } else if (command.equals("w")) {
            doAddWater(plotPos);
        } else if (command.equals("r")) {
            doRemovePlant(plotPos);
        } else if (command.equals("n")) {
            displayRenamePlant(plotPos);
        } else if (command.equals("b")) {
            displayMainMenu();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays options for adding a plant
    private void displayMenuOptionsAddPlant() {
        System.out.println("\nSelect a plant:");
        System.out.println("\t1 -> cactus");
        System.out.println("\t2 -> succulent");
        System.out.println("\t3 -> snake plant");
        System.out.println("\t4 -> money plant");
        System.out.println("\t5 -> daffodil");
        System.out.println("\t6 -> tulip");
        System.out.println("\tb -> go back");

        command = input.next();
        command = command.toLowerCase();

        processMenuOptionsAddPlant(command);

    }

    // MODIFIES: this
    // EFFECTS: processes user commands for adding a plant
    private void processMenuOptionsAddPlant(String command) {
        if (command.equals("1")) {
            doAddPlant(plotPos, "cactus");
        } else if (command.equals("2")) {
            doAddPlant(plotPos, "succulent");
        } else if (command.equals("3")) {
            doAddPlant(plotPos, "snake plant");
        } else if (command.equals("4")) {
            doAddPlant(plotPos, "money plant");
        } else if (command.equals("5")) {
            doAddPlant(plotPos, "daffodil");
        } else if (command.equals("6")) {
            doAddPlant(plotPos, "tulip");
        } else if (command.equals("b")) {
            displayMenuOptionsPlot(plotPos);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a plant of breed to garden at key plotPos
    private void doAddPlant(int plotPos, String breed) {
        garden.addPlant(plotPos, breed);
        System.out.println("You have planted a " + breed + " in plot " + plotPos);
    }

    // MODIFIES: this
    // EFFECTS: adds water to plant at key plotPos if there is a plant there and it is thirsty
    private void doAddWater(int plotPos) {
        Plant plant = garden.getPlant(plotPos);
        if (plant != null) {
            if (plant.getIsThirsty()) {
                plant.addWater();
                System.out.println("You have watered the plant!");
            } else {
                System.out.println("You watered this plant recently! Try again later.");
            }
        } else {
            System.out.println("There's nothing to water! Please add a plant first.");
            displayMenuOptionsPlot(plotPos);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes plant at key plotPos if there is a plant there
    private void doRemovePlant(int plotPos) {
        Plant plant = garden.getPlant(plotPos);
        if (plant != null) {
            garden.removePlant(plotPos);
            System.out.println("You have removed this plant.");
        } else {
            System.out.println("There's nothing to remove!");
            displayMenuOptionsPlot(plotPos);
        }
    }

    // EFFECTS: displays menu for renaming plant at key plotPos if there is a plant there
    private void displayRenamePlant(int plotPos) {
        Plant plant = garden.getPlant(plotPos);
        if (plant != null) {
            System.out.println("What do you want to name this plant?");

            command = input.next();

            processRenamePlant(command, plant);
        } else {
            System.out.println("There's nothing to rename! Please add a plant first.");
            displayMenuOptionsPlot(plotPos);
        }
    }

    // MODIFIES: this
    // EFFECTS: renames plant at key plotPos
    private void processRenamePlant(String command, Plant plant) {
        plant.rename(command);
        System.out.println("Your plant is now named " + command);
    }
}
