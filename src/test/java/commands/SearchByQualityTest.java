package commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import coffee.BeanCoffee;
import coffee.Coffee;
import coffee.enums.RoastLevel;
import coffeevan.CoffeeVan;
import commands.SearchByQuality;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import packaging.Packaging;
import qualityparams.QualityParams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class SearchByQualityTest {
    @Mock
    private CoffeeVan coffeeVan;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() throws Exception {
        System.setOut(originalOut);
        closeable.close();
    }

    @Test
    void testExecute_foundItems() {
        String input = "5.0\n8.0\n6.0\n9.0\n7.0\n10.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        List<Coffee> allCoffee = new ArrayList<>();
        Coffee coffee = new BeanCoffee("Test", 100.0, 10.0,
                new QualityParams(7.0, 8.0, 9.0),
                new Packaging("Box", 100.0),
                RoastLevel.LIGHT, "Colombia");
        allCoffee.add(coffee);

        List<Coffee> foundCoffee = new ArrayList<>();
        foundCoffee.add(coffee);

        when(coffeeVan.getCargo()).thenReturn(allCoffee);
        when(coffeeVan.findByQuality(5.0, 8.0, 6.0, 9.0, 7.0, 10.0))
                .thenReturn(foundCoffee);

        SearchByQuality command = new SearchByQuality(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Found items:"));
        verify(coffeeVan, times(1)).findByQuality(5.0, 8.0, 6.0, 9.0, 7.0, 10.0);
    }

    @Test
    void testExecute_noItemsFound() {
        String input = "1.0\n2.0\n1.0\n2.0\n1.0\n2.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        List<Coffee> allCoffee = new ArrayList<>();
        allCoffee.add(mock(Coffee.class));

        when(coffeeVan.getCargo()).thenReturn(allCoffee);
        when(coffeeVan.findByQuality(1.0, 2.0, 1.0, 2.0, 1.0, 2.0))
                .thenReturn(new ArrayList<>());

        SearchByQuality command = new SearchByQuality(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("No coffee found matching these quality parameters."));
    }

    @Test
    void testExecute_emptyCargo() {
        when(coffeeVan.getCargo()).thenReturn(new ArrayList<>());

        SearchByQuality command = new SearchByQuality(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("There is no coffee yet!"));
        verify(coffeeVan, never()).findByQuality(anyDouble(), anyDouble(),
                anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    void testExecute_invalidRangeHandling() {
        String input = "8.0\n5.0\n5.0\n8.0\n6.0\n9.0\n7.0\n10.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        List<Coffee> allCoffee = new ArrayList<>();
        allCoffee.add(mock(Coffee.class));

        when(coffeeVan.getCargo()).thenReturn(allCoffee);
        when(coffeeVan.findByQuality(5.0, 8.0, 6.0, 9.0, 7.0, 10.0))
                .thenReturn(new ArrayList<>());

        SearchByQuality command = new SearchByQuality(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Minimum cannot be greater than maximum"));
    }

    @Test
    void testExecute_invalidInputHandling() {
        String input = "abc\n5.0\n8.0\n6.0\n9.0\n7.0\n10.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        List<Coffee> allCoffee = new ArrayList<>();
        allCoffee.add(mock(Coffee.class));

        when(coffeeVan.getCargo()).thenReturn(allCoffee);
        when(coffeeVan.findByQuality(5.0, 8.0, 6.0, 9.0, 7.0, 10.0))
                .thenReturn(new ArrayList<>());

        SearchByQuality command = new SearchByQuality(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid input!"));
    }

    @Test
    void testExecute_outOfRangeValues() {
        String input = "0.5\n5.0\n8.0\n6.0\n9.0\n7.0\n10.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        List<Coffee> allCoffee = new ArrayList<>();
        allCoffee.add(mock(Coffee.class));

        when(coffeeVan.getCargo()).thenReturn(allCoffee);
        when(coffeeVan.findByQuality(5.0, 8.0, 6.0, 9.0, 7.0, 10.0))
                .thenReturn(new ArrayList<>());

        SearchByQuality command = new SearchByQuality(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Value must be between 1 and 10"));
    }
}