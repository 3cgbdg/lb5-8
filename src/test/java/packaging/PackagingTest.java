package packaging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Packaging class.
 * This is a simple data object, so we test the constructor
 * and the getInfo() formatting.
 */
class PackagingTest {

    @Test
    @DisplayName("Constructor should set properties correctly")
    void testConstructorAndGetters() {
        // 1. Arrange
        String expectedMaterial = "Aluminum";
        double expectedVolume = 330.5;

        Packaging packaging = new Packaging(expectedMaterial, expectedVolume);

        assertEquals(expectedMaterial, packaging.getMaterial());
        assertEquals(expectedVolume, packaging.getVolume());
    }

    @Test
    @DisplayName("getInfo should return correctly formatted string")
    void testGetInfo_ReturnsCorrectFormat() {

        Packaging packaging = new Packaging("Paper", 250.0);
        String expectedInfo = "Packaging{material='Paper', volume=250.0}";

        String actualInfo = packaging.getInfo();

        assertEquals(expectedInfo, actualInfo);
    }

    @Test
    @DisplayName("getInfo should handle zero volume")
    void testGetInfo_WithZeroVolume() {

        Packaging packaging = new Packaging("Plastic", 0.0);
        String expectedInfo = "Packaging{material='Plastic', volume=0.0}";

        String actualInfo = packaging.getInfo();

        assertEquals(expectedInfo, actualInfo);
    }

    @Test
    @DisplayName("getInfo should handle empty material string")
    void testGetInfo_WithEmptyMaterial() {

        Packaging packaging = new Packaging("", 100.0);
        String expectedInfo = "Packaging{material='', volume=100.0}";

        String actualInfo = packaging.getInfo();

        assertEquals(expectedInfo, actualInfo);
    }
}
