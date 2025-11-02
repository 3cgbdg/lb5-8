package commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


class ExitCommandTest {

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
    void testExecute_printsShutdownMessageAndTriggersCallback() {
        // Used an array to hold a mutable boolean inside the lambda
        final boolean[] exited = {false};

        ExitCommand command = new ExitCommand(() -> exited[0] = true);

        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("The program has finished working!"));
        assertTrue(exited[0], "Exiter runnable should be triggered");
    }
}
