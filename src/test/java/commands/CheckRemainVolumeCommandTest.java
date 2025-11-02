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
class CheckRemainVolumeCommandTest {
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
    void testExecute_printsCorrectVolume() {
        when(coffeeVan.getRemainingVolume()).thenReturn(750.25);

        CheckRemainVolumeCommand command = new CheckRemainVolumeCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("750.25"));
        verify(coffeeVan, times(1)).getRemainingVolume();
    }

    @Test
    void testExecute_zeroVolume() {
        when(coffeeVan.getRemainingVolume()).thenReturn(0.0);

        CheckRemainVolumeCommand command = new CheckRemainVolumeCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("0.0"));
    }

    @Test
    void testExecute_largeVolume() {
        when(coffeeVan.getRemainingVolume()).thenReturn(99999.99);

        CheckRemainVolumeCommand command = new CheckRemainVolumeCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("99999.99"));
    }
}
