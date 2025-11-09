package commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import coffee.BeanCoffee;
import coffee.Coffee;
import coffee.enums.RoastLevel;
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
class GetFromFileCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    @Mock
    private CoffeeStorageService storageService;

    @Test
    void testExecute_successfulLoad() throws IOException {
        // Setup
        List<Coffee> mockCoffees = new ArrayList<>();
        Coffee coffee1 = new BeanCoffee("Arabica", 250.0, 15.99,
                new QualityParams(8.0, 9.0, 7.0),
                new Packaging("Paper", 250.0),
                RoastLevel.MEDIUM, "Brazil");
        mockCoffees.add(coffee1);

        when(storageService.getFromFile()).thenReturn(mockCoffees);

        // Action
        GetFromFileCommand command = new GetFromFileCommand(coffeeVan, storageService);
        command.execute();

        // Verify
        verify(storageService, times(1)).getFromFile();
        verify(coffeeVan, times(1)).addCoffee(coffee1); // Verify that the coffee was added
    }

    @Test
    void testExecute_emptyFile() throws IOException {
        // Setup
        when(storageService.getFromFile()).thenReturn(new ArrayList<>());
        GetFromFileCommand command = new GetFromFileCommand(coffeeVan, storageService);

        // Action
        command.execute();

        // Verify
        verify(storageService, times(1)).getFromFile();
        // Verify that addCoffee was NOT called
        verify(coffeeVan, never()).addCoffee(any(Coffee.class));
    }

    @Test
    void testExecute_ioException() throws IOException {
        // Setup: storageService throws an error
        when(storageService.getFromFile()).thenThrow(new IOException("File not found"));
        GetFromFileCommand command = new GetFromFileCommand(coffeeVan, storageService);

        assertThrows(IOException.class, command::execute);
    }
}