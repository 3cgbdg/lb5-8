package commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import coffee.Coffee;
import coffee.GroundCoffee;
import coffee.enums.GrindSize;
import coffeevan.CoffeeVan;
import commands.SaveToFileCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import packaging.Packaging;
import qualityparams.QualityParams;
import services.CoffeeStorageService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class SaveToFileCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    @Mock
    private CoffeeStorageService storageService;

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
    void testExecute_successfulSave() throws IOException {
        List<Coffee> mockCargo = new ArrayList<>();
        Coffee coffee = new GroundCoffee("Robusta", 100.0, 5.99,
                new QualityParams(7.0, 6.0, 8.0),
                new Packaging("Plastic", 100.0),
                GrindSize.COARSE);
        mockCargo.add(coffee);

        when(coffeeVan.getCargo()).thenReturn(mockCargo);
        doNothing().when(storageService).saveToFile(mockCargo);

        SaveToFileCommand command = new SaveToFileCommand(coffeeVan, storageService);
        command.execute();

        verify(storageService, times(1)).saveToFile(mockCargo);

        String output = outputStream.toString();
        assertTrue(output.contains("Successfully loaded!"));
    }

    @Test
    void testExecute_ioException() throws IOException {
        List<Coffee> mockCargo = new ArrayList<>();
        when(coffeeVan.getCargo()).thenReturn(mockCargo);
        doThrow(new IOException("Write error")).when(storageService).saveToFile(mockCargo);

        SaveToFileCommand command = new SaveToFileCommand(coffeeVan, storageService);

        assertThrows(IOException.class, command::execute);
    }

    @Test
    void testExecute_emptyCargo() throws IOException {
        List<Coffee> emptyCargo = new ArrayList<>();
        when(coffeeVan.getCargo()).thenReturn(emptyCargo);
        doNothing().when(storageService).saveToFile(emptyCargo);

        SaveToFileCommand command = new SaveToFileCommand(coffeeVan, storageService);
        command.execute();

        verify(storageService, times(1)).saveToFile(emptyCargo);
    }
}