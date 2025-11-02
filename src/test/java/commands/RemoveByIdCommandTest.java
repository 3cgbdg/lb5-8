package commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import coffeevan.CoffeeVan;
import commands.RemoveByIdCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

class RemoveByIdCommandTest {
    @Mock
    private CoffeeVan coffeeVan;

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
    void testExecute_successfulRemoval() throws IOException {
        String input = "12345\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.removeCoffeeById("12345")).thenReturn(true);
        when(coffeeVan.getCargo()).thenReturn(new ArrayList<>());

        RemoveByIdCommand command = new RemoveByIdCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).removeCoffeeById("12345");

        String output = outputStream.toString();
        assertTrue(output.contains("Item was successfully removed!"));
    }

    @Test
    void testExecute_itemNotFound() throws IOException {
        String input = "99999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.removeCoffeeById("99999")).thenReturn(false);

        RemoveByIdCommand command = new RemoveByIdCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).removeCoffeeById("99999");

        String output = outputStream.toString();
        assertTrue(output.contains("Item was not found!"));
    }

    @Test
    void testExecute_emptyIdHandling() throws IOException {
        String input = "\n\n12345\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.removeCoffeeById("12345")).thenReturn(true);
        when(coffeeVan.getCargo()).thenReturn(new ArrayList<>());

        RemoveByIdCommand command = new RemoveByIdCommand(coffeeVan);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("ID cannot be empty!"));
    }

    @Test
    void testExecute_whitespaceIdHandling() throws IOException {
        String input = "   \n  12345  \n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(coffeeVan.removeCoffeeById("12345")).thenReturn(true);
        when(coffeeVan.getCargo()).thenReturn(new ArrayList<>());

        RemoveByIdCommand command = new RemoveByIdCommand(coffeeVan);
        command.execute();

        verify(coffeeVan, times(1)).removeCoffeeById("12345");
    }
}
