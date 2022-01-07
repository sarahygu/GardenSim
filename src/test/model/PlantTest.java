package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class PlantTest {
    // Tests for Plant class

    private static final int MAX_GROWTH_STAGE = 4;
    private static final int WAIT_INTERVAL = 10000 + 1;

    private Plant cactusPlant;
    private Plant succulentPlant;
    private Plant maxGrowthStagePlant;

    @BeforeEach
    void runBefore() {
        cactusPlant = new Plant("cactus");
        succulentPlant = new Plant("succulent");
        maxGrowthStagePlant = new Plant("cactus", "John", MAX_GROWTH_STAGE, true);
    }

    @Test
    void testNewPlant() {
        assertEquals("cactus", cactusPlant.getBreed());
        assertEquals("cactus", cactusPlant.getLabel());
        assertEquals(0, cactusPlant.getGrowthStage());
        assertEquals(true, cactusPlant.getIsThirsty());

        assertEquals("succulent", succulentPlant.getBreed());
        assertEquals("succulent", succulentPlant.getLabel());
        assertEquals(0, succulentPlant.getGrowthStage());
        assertEquals(true, succulentPlant.getIsThirsty());
    }

    @Test
    void testConstructPlant() {
        Plant newConstructPlant = new Plant("money plant", "Joe", 2, true);

        assertEquals("money plant", newConstructPlant.getBreed());
        assertEquals("Joe", newConstructPlant.getLabel());
        assertEquals(2, newConstructPlant.getGrowthStage());
        assertEquals(true, newConstructPlant.getIsThirsty());

    }

    @Test
    void testAddWater() {
        // adding water to a plant that is thirsty
        assertEquals("cactus", cactusPlant.getBreed());
        assertEquals("cactus", cactusPlant.getLabel());
        assertEquals(true, cactusPlant.getIsThirsty());
        assertEquals(0, cactusPlant.getGrowthStage());

        cactusPlant.addWater();

        assertEquals("cactus", cactusPlant.getBreed());
        assertEquals("cactus", cactusPlant.getLabel());
        assertEquals(false, cactusPlant.getIsThirsty());
        assertEquals(0, cactusPlant.getGrowthStage());

        // wait until plant growth
        try {
            Thread.sleep(WAIT_INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // plant grows by 1 stage and is thirsty after interval passes
        assertEquals("cactus", cactusPlant.getBreed());
        assertEquals("cactus", cactusPlant.getLabel());
        assertEquals(1, cactusPlant.getGrowthStage());
        assertEquals(true, cactusPlant.getIsThirsty());

        // adding water to a plant that is not thirsty does not do anything
        succulentPlant.addWater();

        assertEquals("cactus", cactusPlant.getBreed());
        assertEquals("cactus", cactusPlant.getLabel());
        assertEquals(false, succulentPlant.getIsThirsty());
        assertEquals(0, succulentPlant.getGrowthStage());

        succulentPlant.addWater();

        assertEquals("cactus", cactusPlant.getBreed());
        assertEquals("cactus", cactusPlant.getLabel());
        assertEquals(false, succulentPlant.getIsThirsty());
        assertEquals(0, succulentPlant.getGrowthStage());

        // test that growth stage doesn't increase when watered at max growth stage
        maxGrowthStagePlant.addWater();
        // wait until plant growth
        try {
            Thread.sleep(WAIT_INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("cactus", maxGrowthStagePlant.getBreed());
        assertEquals("John", maxGrowthStagePlant.getLabel());
        assertEquals(MAX_GROWTH_STAGE, maxGrowthStagePlant.getGrowthStage());
        assertEquals(false, maxGrowthStagePlant.getIsThirsty());
    }

    @Test
    void testRename() {
        cactusPlant.rename("Tim");

        assertEquals("cactus", cactusPlant.getBreed());
        assertEquals("Tim", cactusPlant.getLabel());
        assertEquals(0, cactusPlant.getGrowthStage());
        assertEquals(true, cactusPlant.getIsThirsty());

    }
}