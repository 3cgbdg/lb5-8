package menu;

import commands.Command;
import commands.ExitCommand;
import commands.GetFromFileCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the Menu class.
 * This class tests the menu's logic for command execution,
 * loop control, and error handling.
 */
@ExtendWith(MockitoExtension.class) // Automatically initializes @Mock fields
class MenuTest {

    private Menu menu;

    @Mock
    private Command showCoffeeCommand;
    @Mock
    private GetFromFileCommand getFromFileCommand; // Mocked concrete class for 'instanceof' check
    @Mock
    private ExitCommand exitCommand; // Mocked concrete class for 'instanceof' check

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in; // Store original System.in

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));

        // Create a new menu for each test
        menu = new Menu();

        // Add our mock commands
        menu.addCommand(MenuEnum.SHOWCOFFEE, showCoffeeCommand); // Choice 2
        menu.addCommand(MenuEnum.GETFROMFILE, getFromFileCommand); // Choice 9
        menu.addCommand(MenuEnum.EXIT, exitCommand); // Choice 11
    }

    @AfterEach
    void tearDown() {
        // Restore standard streams after each test
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    @Test
    void run_ShouldExecuteExitCommand_WhenChoiceIs11() throws IOException {
        // Arrange: User types "11"
        provideInput("11\n");

        // Act
        menu.run();

        // Assert: The exit command was called exactly once, and the loop terminated
        verify(exitCommand, times(1)).execute();
    }

    @Test
    void run_ShouldExecuteCorrectCommand_WhenChoiceIs2() throws IOException {
        // Arrange: User types "2" (Show Coffee) and then "11" (Exit)
        provideInput("2\n11\n");

        // Act
        menu.run();

        // Assert: The 'showCoffee' command was called
        verify(showCoffeeCommand, times(1)).execute();
        // Assert: The 'exit' command was also called to stop the loop
        verify(exitCommand, times(1)).execute();
    }

    @Test
    void run_ShouldHandleInvalidNumericChoice_AndContinueLoop() throws IOException {
        // Arrange: User types "99" (invalid), then "11" (Exit)
        provideInput("99\n11\n");

        // Act
        menu.run();

        // Assert: No valid command was executed for choice "99"
        verify(showCoffeeCommand, never()).execute();
        verify(getFromFileCommand, never()).execute();

        // Assert: The loop continued and eventually called 'exit'
        verify(exitCommand, times(1)).execute();
    }

    @Test
    void run_ShouldHandleInputMismatchException_AndContinueLoop() throws IOException {
        // Arrange: User types "abc" (which causes InputMismatchException), then "11" (Exit)
        provideInput("abc\n11\n");

        // Act
        menu.run();

        // Assert: No command was executed for "abc"
        verify(showCoffeeCommand, never()).execute();
        verify(getFromFileCommand, never()).execute();

        // Assert: The loop caught the error and continued until "11" was entered
        verify(exitCommand, times(1)).execute();
    }

    @Test
    void run_ShouldExecuteGetFromFileCommand_OnlyOnce() throws IOException {
        // Arrange: User types "9", then "9" again, then "11" (Exit)
        provideInput("9\n9\n11\n");

        // Act
        menu.run();

        // Assert: The 'getFromFile' command was executed *only once*
        verify(getFromFileCommand, times(1)).execute();

        // Assert: The loop continued and exited
        verify(exitCommand, times(1)).execute();
    }

    @Test
    void run_ShouldHandleCommandException_AndContinueLoop() throws IOException {
        // Arrange: Stub command "2" to throw an IOException when executed
        doThrow(new IOException("Test Exception: File not found")).when(showCoffeeCommand).execute();

        // Arrange: User types "2" (which will fail), then "11" (Exit)
        provideInput("2\n11\n");

        // Act
        menu.run();

        // Assert: The menu *attempted* to execute the command
        verify(showCoffeeCommand, times(1)).execute();

        // Assert: The menu caught the error (logged it) and *continued* running
        verify(exitCommand, times(1)).execute();
    }
}