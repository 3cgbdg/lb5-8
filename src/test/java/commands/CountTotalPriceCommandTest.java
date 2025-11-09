package commands;

import static org.mockito.Mockito.*;

import coffeevan.CoffeeVan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CountTotalPriceCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    @Test
    void testExecute_shouldCallGetTotalCost() {
        // Setup
        when(coffeeVan.getTotalCost()).thenReturn(2345.67);
        CountTotalPriceCommand command = new CountTotalPriceCommand(coffeeVan);

        // Action
        command.execute();

        // Verify: was getTotalCost() called 1 time?
        verify(coffeeVan, times(1)).getTotalCost();
    }

    @Test
    void testExecute_zeroTotal() {
        // Setup
        when(coffeeVan.getTotalCost()).thenReturn(0.0);
        CountTotalPriceCommand command = new CountTotalPriceCommand(coffeeVan);

        // Action
        command.execute();

        // Verify
        verify(coffeeVan, times(1)).getTotalCost();
    }
}