package commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import coffee.BeanCoffee;
import coffee.Coffee;
import coffee.GroundCoffee;
import coffee.enums.GrindSize;
import coffee.enums.RoastLevel;
import coffeevan.CoffeeVan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import packaging.Packaging;
import qualityparams.QualityParams;
import services.CoffeeStorageService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class GetFromFileCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

    @Mock
    private CoffeeStorageService storageService;

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
    void testExecute_successfulLoad() throws IOException {
        List<Coffee> mockCoffees = new ArrayList<>();
        Coffee coffee1 = new BeanCoffee("Arabica", 250.0, 15.99,
                new QualityParams(8.0, 9.0, 7.0),
                new Packaging("Paper", 250.0),
                RoastLevel.MEDIUM, "Brazil");
        mockCoffees.add(coffee1);

        when(storageService.getFromFile()).thenReturn(mockCoffees);
        when(coffeeVan.addCoffee(any(Coffee.class))).thenReturn(true);

        GetFromFileCommand command = new GetFromFileCommand(coffeeVan, storageService);
        command.execute();

        verify(storageService, times(1)).getFromFile();
        verify(coffeeVan, times(1)).addCoffee(coffee1);

        String output = outputStream.toString();
        assertTrue(output.contains("Successfully received!"));
    }

    @Test
    void testExecute_emptyFile() throws IOException {
        when(storageService.getFromFile()).thenReturn(new ArrayList<>());

        GetFromFileCommand command = new GetFromFileCommand(coffeeVan, storageService);
        command.execute();

        verify(storageService, times(1)).getFromFile();
        verify(coffeeVan, never()).addCoffee(any(Coffee.class));

        String output = outputStream.toString();
        assertTrue(output.contains("Successfully received!"));
    }

    @Test
    void testExecute_ioException() throws IOException {
        when(storageService.getFromFile()).thenThrow(new IOException("File not found"));

        GetFromFileCommand command = new GetFromFileCommand(coffeeVan, storageService);

        assertThrows(IOException.class, command::execute);
    }

    @Test
    void testExecute_multipleCoffees() throws IOException {
        List<Coffee> mockCoffees = new ArrayList<>();
        mockCoffees.add(new BeanCoffee("Arabica", 250.0, 15.99,
                new QualityParams(8.0, 9.0, 7.0),
                new Packaging("Paper", 250.0),
                RoastLevel.MEDIUM, "Brazil"));
        mockCoffees.add(new GroundCoffee("Robusta", 100.0, 5.99,
                new QualityParams(7.0, 6.0, 8.0),
                new Packaging("Plastic", 100.0),
                GrindSize.COARSE));

        when(storageService.getFromFile()).thenReturn(mockCoffees);
        when(coffeeVan.addCoffee(any(Coffee.class))).thenReturn(true);

        GetFromFileCommand command = new GetFromFileCommand(coffeeVan, storageService);
        command.execute();

        verify(coffeeVan, times(2)).addCoffee(any(Coffee.class));
    }
}
