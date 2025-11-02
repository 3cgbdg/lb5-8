package menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the MenuEnum static factory method.
 * This test does not require Mockito as it tests pure logic.
 */
class MenuEnumTest {

    @ParameterizedTest(name = "Input {0} should return {1}")
    @CsvSource({
            "1, LOADVAN",
            "2, SHOWCOFFEE",
            "3, SORTCOFFEE",
            "4, REMOVECOFFEEBYID",
            "5, SEARCHBYQUALITY",
            "6, CHECKREMAINBUDGET",
            "7, CHECKREMAINVOLUME",
            "8, COUNTTOTALPRICE",
            "9, GETFROMFILE",
            "10, SAVETOFILE",
            "11, EXIT"
    })
    @DisplayName("Should return correct enum for valid numbers (1-11)")
    void fromNumber_ShouldReturnCorrectEnum(int choice, MenuEnum expectedEnum) {

        MenuEnum actual = MenuEnum.fromNumber(choice);

        assertEquals(expectedEnum, actual);
    }

    @Test
    @DisplayName("Should return null for an invalid number")
    void fromNumber_ShouldReturnNullForInvalidNumber() {

        int invalidChoice = 99;

        MenuEnum actual = MenuEnum.fromNumber(invalidChoice);

        assertNull(actual);
    }

    @Test
    @DisplayName("Should return null for zero")
    void fromNumber_ShouldReturnNullForZero() {

        int zeroChoice = 0;

        MenuEnum actual = MenuEnum.fromNumber(zeroChoice);

        assertNull(actual);
    }
}
