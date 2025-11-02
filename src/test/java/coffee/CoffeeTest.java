package coffee;

import static org.junit.jupiter.api.Assertions.*;

import coffee.enums.ConcentrationLevel;
import coffee.enums.GrindSize;
import coffee.enums.RoastLevel;
import org.junit.jupiter.api.Test;
import packaging.Packaging;
import qualityparams.QualityParams;

class CoffeeTest {

    @Test
    void testFromFileString_beanCoffee() {
        String line = "BEAN;test-id;Arabica;250.0;15.99;8.0;9.0;7.5;Paper;250.0;Brazil;MEDIUM";
        Coffee coffee = Coffee.fromFileString(line);

        assertNotNull(coffee);
        assertInstanceOf(BeanCoffee.class, coffee);
        assertEquals("test-id", coffee.getId());
        assertEquals("Arabica", coffee.getName());
        assertEquals(RoastLevel.MEDIUM, ((BeanCoffee) coffee).getRoastLevel());
    }

    @Test
    void testFromFileString_groundCoffee() {
        String line = "GROUND;test-id;Robusta;150.0;8.99;7.0;8.5;6.0;Plastic;150.0;COARSE";
        Coffee coffee = Coffee.fromFileString(line);

        assertNotNull(coffee);
        assertInstanceOf(GroundCoffee.class, coffee);
        assertEquals(GrindSize.COARSE, ((GroundCoffee) coffee).getGrindSize());
    }

    @Test
    void testFromFileString_instantCoffee() {
        String line = "INSTANT;test-id;Nescafe;100.0;5.99;6.0;7.0;8.0;Jar;100.0;HIGH";
        Coffee coffee = Coffee.fromFileString(line);

        assertNotNull(coffee);
        assertInstanceOf(InstantCoffee.class, coffee);
        assertEquals(ConcentrationLevel.HIGH, ((InstantCoffee) coffee).getConcentrationLevel());
    }

    @Test
    void testFromFileString_invalidType_returnsNull() {
        String line = "INVALID;id;Name;100;10;5;5;5;Box;100;Extra";
        Coffee coffee = Coffee.fromFileString(line);
        assertNull(coffee);
    }

    @Test
    void testFromFileString_invalidFormat_returnsNull() {
        String line = "BEAN;id;Name"; // Недостатньо полів
        Coffee coffee = Coffee.fromFileString(line);

        assertNull(coffee);
    }

    @Test
    void testRoundTrip_beanCoffee() {
        QualityParams quality = new QualityParams(8.0, 9.0, 7.0);
        Packaging packaging = new Packaging("Paper", 250.0);
        BeanCoffee original = new BeanCoffee("Arabica", 200.0, 12.0, quality, packaging, RoastLevel.DARK, "Colombia");

        String fileString = original.toFileString();
        Coffee restored = Coffee.fromFileString(fileString);

        assertNotNull(restored);
        assertEquals(original.getId(), restored.getId());
        assertEquals(original.getName(), restored.getName());
        assertEquals(original.getWeight(), restored.getWeight());
    }

    @Test
    void testRoundTrip_groundCoffee() {
        QualityParams quality = new QualityParams(7.0, 8.0, 6.0);
        Packaging packaging = new Packaging("Bag", 150.0);
        GroundCoffee original = new GroundCoffee("Robusta", 150.0, 9.0, quality, packaging, GrindSize.MEDIUM);

        String fileString = original.toFileString();
        Coffee restored = Coffee.fromFileString(fileString);

        assertNotNull(restored);
        assertEquals(original.getId(), restored.getId());
        assertEquals(GrindSize.MEDIUM, ((GroundCoffee) restored).getGrindSize());
    }

    @Test
    void testRoundTrip_instantCoffee() {
        QualityParams quality = new QualityParams(6.0, 7.0, 5.0);
        Packaging packaging = new Packaging("Jar", 100.0);
        InstantCoffee original = new InstantCoffee("Nescafe", 80.0, 6.0, quality, packaging, ConcentrationLevel.LOW);

        String fileString = original.toFileString();
        Coffee restored = Coffee.fromFileString(fileString);

        assertNotNull(restored);
        assertEquals(original.getId(), restored.getId());
        assertEquals(ConcentrationLevel.LOW, ((InstantCoffee) restored).getConcentrationLevel());
    }
}