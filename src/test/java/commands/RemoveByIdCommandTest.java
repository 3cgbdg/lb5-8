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
import services.CoffeeStorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class RemoveByIdCommandTest {
    @Mock
    private CoffeeVan coffeeVan;
    @Mock
    private CoffeeStorageService coffeeStorage;

    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() throws Exception {
        System.setIn(originalIn);
    }

    private void simulateInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    void testExecute_successfulRemoval_shouldSaveToFile() throws IOException {
        simulateInput("12345\n");

        // Setup: removeCoffeeById returned true
        when(coffeeVan.removeCoffeeById("12345")).thenReturn(true);
        when(coffeeVan.getCargo()).thenReturn(new ArrayList<>()); // Needed for saveToFile

        RemoveByIdCommand command = new RemoveByIdCommand(coffeeVan, coffeeStorage);
        command.execute();

        // Verify:
        verify(coffeeVan, times(1)).removeCoffeeById("12345");
        verify(coffeeStorage, times(1)).saveToFile(any(), eq(false));
    }

    @Test
    void testExecute_itemNotFound_shouldNotSaveToFile() throws IOException {
        simulateInput("99999\n");

        // Setup: removeCoffeeById returned false
        when(coffeeVan.removeCoffeeById("99999")).thenReturn(false);

        RemoveByIdCommand command = new RemoveByIdCommand(coffeeVan, coffeeStorage);
        command.execute();

        // Verify:
        verify(coffeeVan, times(1)).removeCoffeeById("99999");
        verify(coffeeStorage, never()).saveToFile(any(), anyBoolean());
    }

    @Test
    void testExecute_handlesEmptyIdLoop() throws IOException {
        // 1. "" (incorrect) -> 2. "" (incorrect) -> 3. "12345" (correct)
        simulateInput("\n\n12345\n");

        when(coffeeVan.removeCoffeeById("12345")).thenReturn(true);
        when(coffeeVan.getCargo()).thenReturn(new ArrayList<>());

        RemoveByIdCommand command = new RemoveByIdCommand(coffeeVan, coffeeStorage);
        command.execute();

        // Verify: it was still called 1 time with the correct ID
        verify(coffeeVan, times(1)).removeCoffeeById("12345");
        verify(coffeeStorage, times(1)).saveToFile(any(), eq(false));
    }
}