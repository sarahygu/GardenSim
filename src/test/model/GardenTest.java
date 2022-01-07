package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class GardenTest {
    //Tests for garden class

    private Garden newGarden;
    private Plant newCactusPlant;
    private Plant newMoneyPlant;

    @BeforeEach
    void runBefore() {
        newGarden = new Garden();

    }

    @Test
    void testAddPlant() {

        // tests addPlant method
        newGarden.addPlant(1, "Cactus");
        newCactusPlant = newGarden.getPlant(1);
        assertEquals("Cactus", newCactusPlant.getBreed());
        assertEquals("Cactus", newCactusPlant.getLabel());
        assertEquals(0, newCactusPlant.getGrowthStage());
        assertEquals(true, newCactusPlant.getIsThirsty());

        newGarden.addPlant(3, "Money Plant");
        newMoneyPlant = newGarden.getPlant(3);

        assertEquals("Money Plant", newMoneyPlant.getBreed());
        assertEquals("Money Plant", newMoneyPlant.getLabel());
        assertEquals(0, newMoneyPlant.getGrowthStage());
        assertEquals(true, newMoneyPlant.getIsThirsty());

    }

    @Test
    void testRemovePlant() {
        newGarden.addPlant(1, "Cactus");
        newGarden.removePlant(1);
        assertNull(newGarden.getPlant(1));
        assertEquals(0, newGarden.getSize());

    }


}
