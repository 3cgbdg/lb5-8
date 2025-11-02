package coffee;

import static org.junit.jupiter.api.Assertions.*;

import coffee.enums.GrindSize;
import org.junit.jupiter.api.Test;
import packaging.Packaging;
import qualityparams.QualityParams;

class GroundCoffeeTest {

    @Test
    void testConstructor_generatesUniqueIds() {
        QualityParams quality = new QualityParams(7.0, 8.5, 6.0);
        Packaging packaging = new Packaging("Plastic", 150.0);

        GroundCoffee coffee1 = new GroundCoffee("Robusta", 150.0, 8.99, quality, packaging, GrindSize.COARSE);
        GroundCoffee coffee2 = new GroundCoffee("Robusta", 150.0, 8.99, quality, packaging, GrindSize.COARSE);

        assertNotNull(coffee1.getId());
        assertNotEquals(coffee1.getId(), coffee2.getId());
    }

    @Test
    void testToFileString_correctFormat() {
        QualityParams quality = new QualityParams(7.0, 8.5, 6.0);
        Packaging packaging = new Packaging("Plastic", 150.0);
        GroundCoffee coffee = new GroundCoffee("Robusta", 150.0, 8.99, quality, packaging, GrindSize.COARSE);

        String fileString = coffee.toFileString();
        String[] parts = fileString.split(";");

        assertEquals(11, parts.length);
        assertEquals("GROUND", parts[0]);
        assertEquals("Robusta", parts[2]);
        assertEquals("COARSE", parts[10]);
    }

    @Test
    void testGetInfo_containsEssentialFields() {
        QualityParams quality = new QualityParams(7.0, 8.5, 6.0);
        Packaging packaging = new Packaging("Plastic", 150.0);
        GroundCoffee coffee = new GroundCoffee("Robusta", 150.0, 8.99, quality, packaging, GrindSize.COARSE);

        String info = coffee.getInfo();

        assertTrue(info.contains("GroundCoffee"));
        assertTrue(info.contains("Robusta"));
        assertTrue(info.contains("COARSE"));
    }
}