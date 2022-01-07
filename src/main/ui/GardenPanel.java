package ui;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// represents the garden display
public class GardenPanel extends JPanel {


    private Garden garden;

    private JLabel plant1;
    private JLabel plant2;
    private JLabel plant3;

    private BufferedImage plantImg1;
    private BufferedImage plantImg2;
    private BufferedImage plantImg3;

    // EFFECTS : creates a garden panel
    public GardenPanel(Garden g) {
        garden = g;
        plantImgToPot();
    }

    // MODIFIES : this
    // EFFECTS : adds plant images as jLabels to the jPanel
    private void plantImgToPot() {
        try {
            plantImg1 = ImageIO.read(new File("./images/" + getPlant1() + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            plantImg2 = ImageIO.read(new File("./images/" + getPlant2() + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            plantImg3 = ImageIO.read(new File("./images/" + getPlant3() + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        plant1 = new JLabel(new ImageIcon(plantImg1));
        plant2 = new JLabel(new ImageIcon(plantImg2));
        plant3 = new JLabel(new ImageIcon(plantImg3));

        this.add(plant1);
        this.add(new JLabel("              "));
        this.add(plant2);
        this.add(new JLabel("              "));
        this.add(plant3);
    }

    // EFFECTS: returns image name for plant at position 1
    public String getPlant1() {
        Plant plant = garden.getPlant(1);
        if (plant != null) {
            if (plant.getGrowthStage() == 0) {
                return "null";
            } else {
                String breed = plant.getBreed();
                int growthStage = plant.getGrowthStage();
                return breed + Integer.toString(growthStage);
            }
        } else {
            return "null";
        }
    }

    // EFFECTS: returns image name for plant at position 2
    public String getPlant2() {
        Plant plant = garden.getPlant(2);
        if (plant != null) {
            if (plant.getGrowthStage() == 0) {
                return "null";
            } else {
                String breed = plant.getBreed();
                int growthStage = plant.getGrowthStage();
                return breed + Integer.toString(growthStage);
            }
        } else {
            return "null";
        }
    }

    // EFFECTS: returns image name for plant at position 3
    public String getPlant3() {
        Plant plant = garden.getPlant(3);
        if (plant != null) {
            if (plant.getGrowthStage() == 0) {
                return "null";
            } else {
                String breed = plant.getBreed();
                int growthStage = plant.getGrowthStage();
                return breed + Integer.toString(growthStage);
            }
        } else {
            return "null";
        }
    }

    // MODIFIES: this
    // EFFECTS: paints panel with garden image background
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon gardenBG = new ImageIcon("./images/plontbg.png");
        g.drawImage(gardenBG.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
