package coffee;

import static org.junit.jupiter.api.Assertions.*;

import coffee.enums.RoastLevel;
import org.junit.jupiter.api.Test;
import packaging.Packaging;
import qualityparams.QualityParams;

class BeanCoffeeTest {

    @Test
    void testConstructor_generatesUniqueIds() {
        QualityParams quality = new QualityParams(8.0, 9.0, 7.5);
        Packaging packaging = new Packaging("Paper", 250.0);

        BeanCoffee coffee1 = new BeanCoffee("Arabica", 250.0, 15.99, quality, packaging, RoastLevel.MEDIUM, "Brazil");
        BeanCoffee coffee2 = new BeanCoffee("Arabica", 250.0, 15.99, quality, packaging, RoastLevel.MEDIUM, "Brazil");

        assertNotNull(coffee1.getId());
        assertNotNull(coffee2.getId());
        assertNotEquals(coffee1.getId(), coffee2.getId());
    }

    @Test
    void testConstructor_withCustomId() {
        QualityParams quality = new QualityParams(8.0, 9.0, 7.5);
        Packaging packaging = new Packaging("Paper", 250.0);
        String customId = "custom-id-123";

        BeanCoffee coffee = new BeanCoffee("Arabica", 250.0, 15.99, quality, packaging, RoastLevel.DARK, "Ethiopia", customId);

        assertEquals(customId, coffee.getId());
    }

    @Test
    void testGetPriceToWeightRatio() {
        QualityParams quality = new QualityParams(8.0, 9.0, 7.5);
        Packaging packaging = new Packaging("Paper", 250.0);
        BeanCoffee coffee = new BeanCoffee("Arabica", 250.0, 15.99, quality, packaging, RoastLevel.MEDIUM, "Brazil");

        double expectedRatio = 15.99 / 250.0;
        assertEquals(expectedRatio, coffee.getPriceToWeightRatio(), 0.0001);
    }

    @Test
    void testToFileString_correctFormat() {
        QualityParams quality = new QualityParams(8.0, 9.0, 7.5);
        Packaging packaging = new Packaging("Paper", 250.0);
        BeanCoffee coffee = new BeanCoffee("Arabica", 250.0, 15.99, quality, packaging, RoastLevel.MEDIUM, "Brazil");

        String fileString = coffee.toFileString();
        String[] parts = fileString.split(";");

        assertEquals(12, parts.length);
        assertEquals("BEAN", parts[0]);
        assertEquals("Arabica", parts[2]);
        assertEquals("250.0", parts[3]);
        assertEquals("15.99", parts[4]);
        assertEquals("Brazil", parts[10]);
        assertEquals("MEDIUM", parts[11]);
    }

    @Test
    void testGetInfo_containsEssentialFields() {
        QualityParams quality = new QualityParams(8.0, 9.0, 7.5);
        Packaging packaging = new Packaging("Paper", 250.0);
        BeanCoffee coffee = new BeanCoffee("Arabica", 250.0, 15.99, quality, packaging, RoastLevel.MEDIUM, "Brazil");

        String info = coffee.getInfo();

        assertTrue(info.contains("BeanCoffee"));
        assertTrue(info.contains("Arabica"));
        assertTrue(info.contains("MEDIUM"));
        assertTrue(info.contains("Brazil"));
    }
}