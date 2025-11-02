package coffee;

import static org.junit.jupiter.api.Assertions.*;

import coffee.enums.ConcentrationLevel;
import org.junit.jupiter.api.Test;
import packaging.Packaging;
import qualityparams.QualityParams;

class InstantCoffeeTest {

    @Test
    void testConstructor_generatesUniqueIds() {
        QualityParams quality = new QualityParams(6.0, 7.0, 8.0);
        Packaging packaging = new Packaging("Jar", 100.0);

        InstantCoffee coffee1 = new InstantCoffee("Nescafe", 100.0, 5.99,
                quality, packaging, ConcentrationLevel.HIGH);
        InstantCoffee coffee2 = new InstantCoffee("Nescafe", 100.0, 5.99,
                quality, packaging, ConcentrationLevel.HIGH);

        assertNotNull(coffee1.getId());
        assertNotEquals(coffee1.getId(), coffee2.getId());
    }

    @Test
    void testToFileString_correctFormat() {
        QualityParams quality = new QualityParams(6.0, 7.0, 8.0);
        Packaging packaging = new Packaging("Jar", 100.0);
        InstantCoffee coffee = new InstantCoffee("Nescafe", 100.0, 5.99,
                quality, packaging, ConcentrationLevel.HIGH);

        String fileString = coffee.toFileString();
        String[] parts = fileString.split(";");

        assertEquals(11, parts.length);
        assertEquals("INSTANT", parts[0]);
        assertEquals("Nescafe", parts[2]);
        assertEquals("HIGH", parts[10]);
    }

    @Test
    void testGetInfo_containsEssentialFields() {
        QualityParams quality = new QualityParams(6.0, 7.0, 8.0);
        Packaging packaging = new Packaging("Jar", 100.0);
        InstantCoffee coffee = new InstantCoffee("Nescafe", 100.0, 5.99,
                quality, packaging, ConcentrationLevel.HIGH);

        String info = coffee.getInfo();

        assertTrue(info.contains("InstantCoffee"));
        assertTrue(info.contains("Nescafe"));
        assertTrue(info.contains("HIGH"));
    }
}