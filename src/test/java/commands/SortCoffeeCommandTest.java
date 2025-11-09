package commands;

import static org.mockito.Mockito.*;

import coffee.Coffee;
import coffeevan.CoffeeVan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SortCoffeeCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    @Test
    void testExecute_successfulSort() {
        // Setup: return a non-empty list so the command doesn't exit early
        List<Coffee> mockCargo = new ArrayList<>();
        mockCargo.add(mock(Coffee.class)); // Content doesn't matter

        when(coffeeVan.getCargo()).thenReturn(mockCargo);
        doNothing().when(coffeeVan).sortByPricePerKg();

        // Action
        SortCoffeeCommand command = new SortCoffeeCommand(coffeeVan);
        command.execute();

        // Verify:
        verify(coffeeVan, times(1)).sortByPricePerKg();
        verify(coffeeVan, times(2)).getCargo();
    }

    @Test
    void testExecute_emptyCargo() {
        // Setup: van returns an empty list
        when(coffeeVan.getCargo()).thenReturn(new ArrayList<>());

        SortCoffeeCommand command = new SortCoffeeCommand(coffeeVan);
        command.execute();

        // Verify:
        verify(coffeeVan, never()).sortByPricePerKg();
        verify(coffeeVan, times(1)).getCargo();
    }
}