package commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import coffee.BeanCoffee;
import coffee.Coffee;
import coffee.enums.RoastLevel;
import coffeevan.CoffeeVan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import packaging.Packaging;
import qualityparams.QualityParams;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SearchByQualityTest {
    @Mock
    private CoffeeVan coffeeVan;

    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() throws Exception {
        System.setIn(originalIn);
    }

    private void simulateInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    void testExecute_foundItems() {
        // Simulate user input for ranges
        String input = "5.0\n8.0\n6.0\n9.0\n7.0\n10.0\n";
        simulateInput(input);

        // Setup:
        List<Coffee> allCoffee = new ArrayList<>();
        Coffee coffee = new BeanCoffee("Test", 100.0, 10.0,
                new QualityParams(7.0, 8.0, 9.0),
                new Packaging("Box", 100.0),
                RoastLevel.LIGHT, "Colombia");
        allCoffee.add(coffee);
        when(coffeeVan.getCargo()).thenReturn(allCoffee);

        // 2. Van returns the *found* cargo in response to findByQuality
        List<Coffee> foundCoffee = new ArrayList<>();
        foundCoffee.add(coffee);
        when(coffeeVan.findByQuality(5.0, 8.0, 6.0, 9.0, 7.0, 10.0))
                .thenReturn(foundCoffee);

        // Action
        SearchByQuality command = new SearchByQuality(coffeeVan);
        command.execute();

        // Verify: was findByQuality called with the correct parameters?
        verify(coffeeVan, times(1)).findByQuality(5.0, 8.0, 6.0, 9.0, 7.0, 10.0);
    }

    @Test
    void testExecute_noItemsFound() {
        String input = "1.0\n2.0\n1.0\n2.0\n1.0\n2.0\n";
        simulateInput(input);

        List<Coffee> allCoffee = new ArrayList<>();
        allCoffee.add(mock(Coffee.class)); // Just so the van isn't empty

        when(coffeeVan.getCargo()).thenReturn(allCoffee);
        when(coffeeVan.findByQuality(1.0, 2.0, 1.0, 2.0, 1.0, 2.0))
                .thenReturn(new ArrayList<>()); // Return an empty list

        // Action
        SearchByQuality command = new SearchByQuality(coffeeVan);
        command.execute();

        // Verify
        verify(coffeeVan, times(1)).findByQuality(1.0, 2.0, 1.0, 2.0, 1.0, 2.0);
    }

    @Test
    void testExecute_emptyCargo() {
        // Setup: van is empty
        when(coffeeVan.getCargo()).thenReturn(new ArrayList<>());

        // Action
        SearchByQuality command = new SearchByQuality(coffeeVan);
        command.execute();

        // Verify: the command should exit early and NOT call findByQuality
        verify(coffeeVan, never()).findByQuality(anyDouble(), anyDouble(),
                anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    void testExecute_handlesInvalidRangeLoop() {
        // 1. Aroma: "8.0\n5.0" (incorrect) -> 2. Aroma: "5.0\n8.0" (correct)
        // 3. Rest is correct.
        String input = "8.0\n5.0\n5.0\n8.0\n6.0\n9.0\n7.0\n10.0\n";
        simulateInput(input);

        List<Coffee> allCoffee = new ArrayList<>();
        allCoffee.add(mock(Coffee.class));
        when(coffeeVan.getCargo()).thenReturn(allCoffee);
        when(coffeeVan.findByQuality(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(new ArrayList<>());

        // Action
        SearchByQuality command = new SearchByQuality(coffeeVan);
        command.execute();

        // Verify: despite the error, the command continued
        verify(coffeeVan, times(1)).findByQuality(5.0, 8.0, 6.0, 9.0, 7.0, 10.0);
    }
}