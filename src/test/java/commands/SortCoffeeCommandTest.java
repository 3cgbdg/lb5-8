package commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import coffee.BeanCoffee;
import coffee.Coffee;
import coffee.GroundCoffee;
import coffee.enums.GrindSize;
import coffee.enums.RoastLevel;
import coffeevan.CoffeeVan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import packaging.Packaging;
import qualityparams.QualityParams;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
@ExtendWith(MockitoExtension.class)
class SortCoffeeCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() throws Exception {
        System.setOut(originalOut);
    }

    @Test
    void testExecute_successfulSort() {
        List<Coffee> mockCargo = getCoffees();

        when(coffeeVan.getCargo()).thenReturn(mockCargo);
        doNothing().when(coffeeVan).sortByPricePerKg();

        SortCoffeeCommand command = new SortCoffeeCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).sortByPricePerKg();
        verify(coffeeVan, times(2)).getCargo();

        String output = outputStream.toString();
        assertTrue(output.contains("Sorted cargo:"));
    }

    private static List<Coffee> getCoffees() {
        List<Coffee> mockCargo = new ArrayList<>();
        Coffee coffee1 = new BeanCoffee("Expensive", 100.0, 20.0, new QualityParams(8.0, 9.0, 7.0), new Packaging("Box", 100.0), RoastLevel.DARK, "Ethiopia");
        Coffee coffee2 = new GroundCoffee("Cheap", 200.0, 10.0, new QualityParams(6.0, 7.0, 8.0), new Packaging("Bag", 200.0), GrindSize.MEDIUM);
        mockCargo.add(coffee1);
        mockCargo.add(coffee2);
        return mockCargo;
    }

    @Test
    void testExecute_emptyCargo() {
        when(coffeeVan.getCargo()).thenReturn(new ArrayList<>());

        SortCoffeeCommand command = new SortCoffeeCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("There is no coffee yet!"));
        verify(coffeeVan, never()).sortByPricePerKg();
    }

    @Test
    void testExecute_singleItemCargo() {
        List<Coffee> mockCargo = new ArrayList<>();
        Coffee coffee = new BeanCoffee("Single", 100.0, 10.0, new QualityParams(8.0, 9.0, 7.0), new Packaging("Box", 100.0), RoastLevel.MEDIUM, "Brazil");
        mockCargo.add(coffee);

        when(coffeeVan.getCargo()).thenReturn(mockCargo);
        doNothing().when(coffeeVan).sortByPricePerKg();

        SortCoffeeCommand command = new SortCoffeeCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).sortByPricePerKg();
    }
}