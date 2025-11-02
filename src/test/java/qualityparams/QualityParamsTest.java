package qualityparams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the QualityParams class.
 * Focuses on constructor validation and the isInRange() logic.
 */
class QualityParamsTest {

    private QualityParams params;

    @BeforeEach
    void setUp() {
        // This is our "standard" object for testing isInRange
        params = new QualityParams(5.0, 7.0, 9.0);
    }


    @Test
    @DisplayName("Constructor should set properties correctly (Happy Path)")
    void testConstructor_HappyPath() {

        QualityParams p = new QualityParams(8.5, 7.2, 9.9);

        assertEquals(8.5, p.getAromaScore());
        assertEquals(7.2, p.getTasteScore());
        assertEquals(9.9, p.getFreshnessScore());
    }

    @Test
    @DisplayName("Constructor should accept 10.0 as a valid score")
    void testConstructor_EdgeCaseAtTen() {
        assertDoesNotThrow(() -> {
            new QualityParams(10.0, 10.0, 10.0);
        });
    }

    @Test
    @DisplayName("Constructor should throw exception if Aroma is > 10")
    void testConstructor_ThrowsOnAromaTooHigh() {

        assertThrows(IllegalArgumentException.class, () -> {
            new QualityParams(10.1, 8.0, 8.0);
        });
    }

    @Test
    @DisplayName("Constructor should throw exception if Taste is > 10")
    void testConstructor_ThrowsOnTasteTooHigh() {

        assertThrows(IllegalArgumentException.class, () -> {
            new QualityParams(8.0, 11.0, 8.0);
        });
    }

    @Test
    @DisplayName("Constructor should throw exception if Freshness is > 10")
    void testConstructor_ThrowsOnFreshnessTooHigh() {

        assertThrows(IllegalArgumentException.class, () -> {
            new QualityParams(8.0, 8.0, 10.0001);
        });
    }

    @Test
    @DisplayName("Constructor should accept negative scores (based on current logic)")
    void testConstructor_AcceptsNegativeScores() {

        assertDoesNotThrow(() -> {
            new QualityParams(-5.0, 0.0, -10.0);
        });
    }


    @Test
    @DisplayName("isInRange should return true when all scores are inside range")
    void testIsInRange_True_AllInside() {

        assertTrue(params.isInRange(4.0, 6.0, 6.0, 8.0, 8.0, 10.0));
    }

    @Test
    @DisplayName("isInRange should return true when all scores are on the edge")
    void testIsInRange_True_AllOnEdge() {

        assertTrue(params.isInRange(5.0, 5.0, 7.0, 7.0, 9.0, 9.0));
    }

    @Test
    @DisplayName("isInRange should return false if Aroma is too low")
    void testIsInRange_False_AromaTooLow() {

        assertFalse(params.isInRange(5.1, 6.0, 6.0, 8.0, 8.0, 10.0));
    }

    @Test
    @DisplayName("isInRange should return false if Aroma is too high")
    void testIsInRange_False_AromaTooHigh() {

        assertFalse(params.isInRange(4.0, 4.9, 6.0, 8.0, 8.0, 10.0));
    }

    @Test
    @DisplayName("isInRange should return false if Taste is too low")
    void testIsInRange_False_TasteTooLow() {

        assertFalse(params.isInRange(4.0, 6.0, 7.1, 8.0, 8.0, 10.0));
    }

    @Test
    @DisplayName("isInRange should return false if Freshness is too high")
    void testIsInRange_False_FreshnessTooHigh() {

        assertFalse(params.isInRange(4.0, 6.0, 6.0, 8.0, 8.0, 8.9));
    }

    @Test
    @DisplayName("isInRange should return false if one param is outside")
    void testIsInRange_False_OneOutside() {

        assertFalse(params.isInRange(4.0, 6.0, 1.0, 2.0, 8.0, 10.0));
    }
}
