package commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import coffeevan.CoffeeVan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


@ExtendWith(MockitoExtension.class)
class CheckRemainBudgetCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Mocks are now initialized by @ExtendWith
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testExecute_printsCorrectBudget() {
        when(coffeeVan.getRemainingBudget()).thenReturn(1500.50);

        CheckRemainBudgetCommand command = new CheckRemainBudgetCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        // Used .contains to avoid issues with different line endings
        assertTrue(output.contains("1500.5"));
        verify(coffeeVan, times(1)).getRemainingBudget();
    }

    @Test
    void testExecute_zeroBudget() {
        when(coffeeVan.getRemainingBudget()).thenReturn(0.0);

        CheckRemainBudgetCommand command = new CheckRemainBudgetCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("0.0"));
    }

    @Test
    void testExecute_negativeBudget() {
        when(coffeeVan.getRemainingBudget()).thenReturn(-100.0);

        CheckRemainBudgetCommand command = new CheckRemainBudgetCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("-100"));
    }
}
