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
class CountTotalPriceCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testExecute_printsCorrectTotal() {
        when(coffeeVan.getTotalCost()).thenReturn(2345.67);

        CountTotalPriceCommand command = new CountTotalPriceCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("2345.67"));
        verify(coffeeVan, times(1)).getTotalCost();
    }

    @Test
    void testExecute_zeroTotal() {
        when(coffeeVan.getTotalCost()).thenReturn(0.0);

        CountTotalPriceCommand command = new CountTotalPriceCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("0.0"));
    }

    @Test
    void testExecute_multipleCalls() {

        when(coffeeVan.getTotalCost()).thenReturn(100.0).thenReturn(200.0);

        CountTotalPriceCommand command = new CountTotalPriceCommand(coffeeVan);

        command.execute();
        String output1 = outputStream.toString();
        assertTrue(output1.contains("100.0"));

        outputStream.reset();

        command.execute();
        String output2 = outputStream.toString();
        assertTrue(output2.contains("200.0"));

        verify(coffeeVan, times(2)).getTotalCost();
    }
}
