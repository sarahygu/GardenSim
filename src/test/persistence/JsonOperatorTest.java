package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class JsonOperatorTest {

    protected void checkGarden(String breed, String label, int growthStage, boolean isThirsty, Plant plant) {
        assertEquals(breed, plant.getBreed());
        assertEquals(label, plant.getLabel());
        assertEquals(growthStage, plant.getGrowthStage());
        assertEquals(isThirsty, plant.getIsThirsty());
    }


    @Test
    void testReadNonExistentFile() {
        JsonOperator reader = new JsonOperator("./data/noSuchFile.json");
        try {
            Garden g = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGarden() {
        JsonOperator reader = new JsonOperator("./data/testEmptyGarden.json");
        try {
            Garden g = reader.read();
            assertEquals(0, g.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReadGeneralGarden() {
        JsonOperator reader = new JsonOperator("./data/testGeneralGarden.json");
        try {
            Garden g = reader.read();
            assertEquals(2, g.getSize());
            checkGarden("cactus", "cactus", 0, true, g.getPlant(1));
            checkGarden("succulent", "succulent", 0, true, g.getPlant(2));
        } catch (IOException e) {
            fail("Couldn't read file");
        }
    }

    @Test
    void testWriteInvalidFile() {
        try {
            Garden g = new Garden();
            JsonOperator writer = new JsonOperator("./data/my\0illegal:fileName!.json");
            writer.openFile();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteEmptyGarden() {
        try {
            Garden g = new Garden();
            JsonOperator writer = new JsonOperator("./data/testWriteEmptyGarden.json");
            writer.openFile();
            writer.write(g);
            writer.closeFile();

            JsonOperator reader = new JsonOperator("./data/testWriteEmptyGarden.json");
            g = reader.read();
            assertEquals(0, g.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteGeneralGarden() {
        try {
            Garden g = new Garden();
            g.addPlant(1,"cactus");
            g.addPlant(2,"succulent" );
            JsonOperator writer = new JsonOperator("./data/testWriteGeneralGarden.json");
            writer.openFile();
            writer.write(g);
            writer.closeFile();

            JsonOperator reader = new JsonOperator("./data/testWriteGeneralGarden.json");
            g = reader.read();
            assertEquals(2, g.getSize());
            checkGarden("cactus", "cactus", 0, true, g.getPlant(1));
            checkGarden("succulent", "succulent", 0, true, g.getPlant(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

