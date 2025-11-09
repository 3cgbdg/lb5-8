package commands;

import static org.mockito.Mockito.*;

import coffeevan.CoffeeVan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CheckRemainBudgetCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    @Test
    void testExecute_shouldCallGetRemainingBudget() {
        // Setup
        when(coffeeVan.getRemainingBudget()).thenReturn(1500.50);
        CheckRemainBudgetCommand command = new CheckRemainBudgetCommand(coffeeVan);

        // Action
        command.execute();

        // Verify: was getRemainingBudget() called exactly 1 time?
        verify(coffeeVan, times(1)).getRemainingBudget();
    }

    @Test
    void testExecute_zeroBudget() {
        // Setup
        when(coffeeVan.getRemainingBudget()).thenReturn(0.0);
        CheckRemainBudgetCommand command = new CheckRemainBudgetCommand(coffeeVan);

        // Action
        command.execute();

        // Verify
        verify(coffeeVan, times(1)).getRemainingBudget();
    }
}