package commands;

import static org.mockito.Mockito.*;

import coffeevan.CoffeeVan;
import commands.ShowCoffeeCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ShowCoffeeCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_callsDisplayMethod() {
        doNothing().when(coffeeVan).displayCargoInfo();

        ShowCoffeeCommand command = new ShowCoffeeCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).displayCargoInfo();
    }

    @Test
    void testExecute_multipleCallsToDisplayMethod() {
        doNothing().when(coffeeVan).displayCargoInfo();

        ShowCoffeeCommand command = new ShowCoffeeCommand(coffeeVan);
        command.execute();
        command.execute();
        command.execute();

        verify(coffeeVan, times(3)).displayCargoInfo();
    }
}