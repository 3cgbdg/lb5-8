package coffeevan;

import coffee.Coffee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualityparams.QualityParams;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoffeeVanTest {

    private CoffeeVan van;
    private Coffee coffee1;
    private Coffee coffee2;

    @BeforeEach
    void setUp() {
        van = new CoffeeVan(1000, 500);
        coffee1 = mock(Coffee.class);
        coffee2 = mock(Coffee.class);

        when(coffee1.getId()).thenReturn("A");
        when(coffee2.getId()).thenReturn("B");

        when(coffee1.getTotalVolume()).thenReturn(200.0);
        when(coffee2.getTotalVolume()).thenReturn(300.0);

        when(coffee1.getPrice()).thenReturn(100.0);
        when(coffee2.getPrice()).thenReturn(150.0);
    }

    @Test
    void addCoffee_ShouldAddSuccessfully() {
        assertTrue(van.addCoffee(coffee1));
        assertEquals(1, van.getCargo().size());
    }

    @Test
    void addCoffee_ShouldThrowException_WhenNull() {
        assertThrows(IllegalArgumentException.class, () -> van.addCoffee(null));
    }

    @Test
    void removeCoffeeById_ShouldWork() {
        van.addCoffee(coffee1);
        assertTrue(van.removeCoffeeById("A"));
        assertFalse(van.removeCoffeeById("Z"));
    }

    @Test
    void getTotalVolume_ShouldReturnSum() {
        van.addCoffee(coffee1);
        van.addCoffee(coffee2);
        assertEquals(500.0, van.getTotalVolume());
    }

    @Test
    void getTotalCost_ShouldReturnSum() {
        van.addCoffee(coffee1);
        van.addCoffee(coffee2);
        assertEquals(250.0, van.getTotalCost());
    }

    @Test
    void sortByPricePerKg_ShouldSortCorrectly() {
        when(coffee1.getPriceToWeightRatio()).thenReturn(5.0);
        when(coffee2.getPriceToWeightRatio()).thenReturn(2.0);

        van.addCoffee(coffee1);
        van.addCoffee(coffee2);
        van.sortByPricePerKg();

        assertEquals(List.of(coffee2, coffee1), van.getCargo());
    }

    @Test
    void findByQuality_ShouldFilterCorrectly() {
        QualityParams q = mock(QualityParams.class);
        when(coffee1.getQuality()).thenReturn(q);
        when(q.isInRange(1, 10, 1, 10, 1, 10)).thenReturn(true);

        van.addCoffee(coffee1);
        assertEquals(1, van.findByQuality(1, 10, 1, 10, 1, 10).size());
    }

    @Test
    void displayCargoInfo_ShouldPrintEmptyCargo() {
        CoffeeVan emptyVan = new CoffeeVan(1000, 500);
        emptyVan.displayCargoInfo();
    }

    @Test
    void displayCargoInfo_ShouldPrintNonEmptyCargo() {
        van.addCoffee(coffee1);
        van.displayCargoInfo();
    }

    @Test
    void getRemainingVolume_ShouldWork() {
        van.addCoffee(coffee1);
        assertEquals(800.0, van.getRemainingVolume());
    }

    @Test
    void getRemainingBudget_ShouldWork() {
        van.addCoffee(coffee1);
        assertEquals(400.0, van.getRemainingBudget());
    }

    @Test
    void findByQuality_ShouldReturnEmpty_WhenNotInRange() {
        QualityParams q = mock(QualityParams.class);
        when(coffee1.getQuality()).thenReturn(q);
        when(q.isInRange(1, 10, 1, 10, 1, 10)).thenReturn(false);

        van.addCoffee(coffee1);
        assertTrue(van.findByQuality(1, 10, 1, 10, 1, 10).isEmpty());
    }
}
