package ui;

import model.*;
import persistence.JsonOperator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents a GUI for the Garden Simulator application
public class MainGUI extends JFrame {

    private static final String JSON_STORE = "./data/garden.json";
    private static final int DELAY = 1000;

    private GardenPanel gp;
    private Garden garden;
    private int selectedPot;
    private JsonOperator writer;
    private JsonOperator reader;


    private String[] pots = {"Pot #1", "Pot #2", "Pot #3"};
    private String[] plants = {"cactus", "succulent", "snake plant", "money plant", "daffodil", "tulip"};

    // MODIFIES: this
    // EFFECTS: start the application
    public MainGUI() {
        super("Garden Simulator");
        garden = new Garden();


        writer = new JsonOperator(JSON_STORE);
        reader = new JsonOperator(JSON_STORE);

        selectedPot = 1;

        gp = new GardenPanel(garden);
        gp.setBorder(BorderFactory.createEmptyBorder(100, 20, 175, 20));
        add(gp, BorderLayout.NORTH);
        addGardenPanel();
        addPotSelectionPanel();
        addButtonPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("GardenSim");
        pack();
        setVisible(true);
        setResizable(false);

        addTimer();
    }

    // EFFECTS : creates a timer to update garden every interval
    private void addTimer() {
        Timer t = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                updateGarden();
            }
        });
        t.start();
    }

    // MODIFIES: this
    // EFFECTS: updates garden image
    private void updateGarden() {
        this.remove(gp);
        gp = new GardenPanel(garden);
        this.add(gp);
        gp.setBorder(BorderFactory.createEmptyBorder(100, 20, 175, 20));
        add(gp, BorderLayout.NORTH);
        gp.revalidate();
        gp.repaint();
    }

    //MODIFIES: this
    // EFFECTS: adds a pot selection panel
    public void addPotSelectionPanel() {
        JComboBox potSelect = new JComboBox(pots);
        potSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String potChoice = (String) potSelect.getSelectedItem();
                setPot(potChoice);
            }
        });

        JPanel potSelectionPanel = new JPanel();
        potSelectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        potSelectionPanel.setLayout(new GridLayout(1, 0));
        potSelectionPanel.add(potSelect);

        add(potSelectionPanel, BorderLayout.CENTER);
    }

    //MODIFIES: this
    // EFFECTS: adds a garden panel
    public void addGardenPanel() {
        gp = new GardenPanel(garden);
        gp.setBorder(BorderFactory.createEmptyBorder(100, 20, 175, 20));


        add(gp, BorderLayout.NORTH);
    }

    //MODIFIES: this
    // EFFECTS: adds a button panel
    public void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 30, 50));
        buttonPanel.setLayout(new GridLayout(2, 0));

        addAddButton(buttonPanel);
        addWaterButton(buttonPanel);
        addRemoveButton(buttonPanel);
        addSaveButton(buttonPanel);
        addReloadButton(buttonPanel);
        addQuitButton(buttonPanel);

        add(buttonPanel, BorderLayout.SOUTH);
    }


    // MODIFIES: selectedPot
    // EFFECTS: changes selectedPot to the chosen pot from drop down menu
    public void setPot(String potChoice) {
        if (potChoice.equals("Pot #1")) {
            selectedPot = 1;
        } else if (potChoice.equals("Pot #2")) {
            selectedPot = 2;
        } else if (potChoice.equals("Pot #3")) {
            selectedPot = 3;
        }
    }


    // MODIFIES: p
    // EFFECTS: adds an add button to p
    private void addAddButton(JPanel p) {
        JButton addButton = new JButton("Add Plant");
        addButton.setActionCommand("add plant");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (garden.getPlant(selectedPot) == null) {
                    int plantInt = JOptionPane.showOptionDialog(null,
                            "Which plant?",
                            "Choose one",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            plants,
                            plants[0]);
                    selectPlant(plantInt);
                } else {
                    System.out.println("There's already a plant here! Remove it first.");
                }
            }
        });
        p.add(addButton);
    }

    // MODIFIES: p
    // EFFECTS: adds a water button to p
    private void addWaterButton(JPanel p) {
        JButton waterButton = new JButton("Add Water");
        waterButton.setActionCommand("add water");
        waterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doAddWater(selectedPot);
            }
        });
        p.add(waterButton);
    }

    // MODIFIES: p
    // EFFECTS: adds a remove button to p
    private void addRemoveButton(JPanel p) {
        JButton removeButton = new JButton("Remove Plant");
        removeButton.setActionCommand("remove plant");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doRemovePlant(selectedPot);
            }
        });
        p.add(removeButton);
    }


    // MODIFIES: p
    // EFFECTS: adds a save button to p
    private void addSaveButton(JPanel p) {
        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("garden saved");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.openFile();
                    writer.write(garden);
                    writer.closeFile();
                } catch (FileNotFoundException f) {
                    System.out.println("Unable to write to file: " + JSON_STORE);
                }
            }
        });
        p.add(saveButton);
    }

    // MODIFIES: p
    // EFFECTS: adds a reload button to p
    private void addReloadButton(JPanel p) {
        JButton reloadButton = new JButton("Reload");
        reloadButton.setActionCommand("garden reloaded");
        reloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    garden = reader.read();
                } catch (IOException f) {
                    System.out.println("Unable to write to file: " + JSON_STORE);
                }
            }
        });
        p.add(reloadButton);
    }

    // MODIFIES: this
    // EFFECTS: adds a quit button to panel
    private void addQuitButton(JPanel p) {
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        p.add(quitButton);
    }

    // MODIFIES: this
    // EFFECTS: adds selected plant given p
    private void selectPlant(int p) {
        if (p == 0) {
            doAddPlant(selectedPot, "cactus");
        } else if (p == 1) {
            doAddPlant(selectedPot, "succulent");
        } else if (p == 2) {
            doAddPlant(selectedPot, "snake plant");
        } else if (p == 3) {
            doAddPlant(selectedPot, "money plant");
        } else if (p == 4) {
            doAddPlant(selectedPot, "daffodil");
        } else {
            doAddPlant(selectedPot, "tulip");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a plant of breed to garden at key plotPos
    private void doAddPlant(int k, String breed) {
        garden.addPlant(k, breed);
        System.out.println("You have planted a " + breed + " in plot " + k);
    }

    // MODIFIES: this
    // EFFECTS: removes plant at key plotPos if there is a plant there

    private void doRemovePlant(int k) {
        Plant plant = garden.getPlant(k);
        if (plant != null) {
            garden.removePlant(k);
            System.out.println("You have removed this plant.");
        } else {
            System.out.println("There's nothing to remove!");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds water to plant at key plotPos if there is a plant there and it is thirsty
    private void doAddWater(int k) {
        Plant plant = garden.getPlant(k);
        if (plant != null) {
            if (plant.getIsThirsty()) {
                plant.addWater();
                System.out.println("You have watered the plant!");
            } else {
                System.out.println("You watered this plant recently! Try again later.");
            }
        } else {
            System.out.println("There's nothing to water! Please add a plant first.");
        }
    }


    public static void main(String[] args) {
        new MainGUI();
    }
}
