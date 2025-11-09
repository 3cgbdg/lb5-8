package commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import coffee.Coffee;
import coffee.GroundCoffee;
import coffee.enums.GrindSize;
import coffeevan.CoffeeVan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import packaging.Packaging;
import qualityparams.QualityParams;
import services.CoffeeStorageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SaveToFileCommandTest {
    @Mock
    private CoffeeVan coffeeVan;
    @Mock
    private CoffeeStorageService storageService;

    @Test
    void testExecute_successfulSave() throws IOException {
        // Setup
        List<Coffee> mockCargo = new ArrayList<>();
        Coffee coffee = new GroundCoffee("Robusta", 100.0, 5.99,
                new QualityParams(7.0, 6.0, 8.0),
                new Packaging("Plastic", 100.0),
                GrindSize.COARSE);
        mockCargo.add(coffee);

        when(coffeeVan.getCargo()).thenReturn(mockCargo);
        doNothing().when(storageService).saveToFile(mockCargo, true);

        // Action
        SaveToFileCommand command = new SaveToFileCommand(coffeeVan, storageService);
        command.execute();

        // Verify:
        verify(coffeeVan, times(1)).getCargo();
        verify(storageService, times(1)).saveToFile(mockCargo, true);
    }

    @Test
    void testExecute_ioException() throws IOException {
        // Setup
        List<Coffee> mockCargo = new ArrayList<>();
        when(coffeeVan.getCargo()).thenReturn(mockCargo);
        // Setup mock to throw an error
        doThrow(new IOException("Write error")).when(storageService).saveToFile(mockCargo, true);

        SaveToFileCommand command = new SaveToFileCommand(coffeeVan, storageService);

        // Action and Verify: expect the command to re-throw the exception
        assertThrows(IOException.class, command::execute);
    }

    @Test
    void testExecute_emptyCargo() throws IOException {
        // Setup
        List<Coffee> emptyCargo = new ArrayList<>();
        when(coffeeVan.getCargo()).thenReturn(emptyCargo);
        doNothing().when(storageService).saveToFile(emptyCargo, true);

        SaveToFileCommand command = new SaveToFileCommand(coffeeVan, storageService);

        // Action
        command.execute();

        // Verify: was saveToFile called with an empty list?
        verify(storageService, times(1)).saveToFile(emptyCargo, true);
    }
}