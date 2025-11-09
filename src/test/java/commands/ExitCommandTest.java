package commands;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean; // Use AtomicBoolean as it's final and can be modified inside the lambda
import static org.junit.jupiter.api.Assertions.*;

class ExitCommandTest {

    @Test
    void testExecute_triggersCallback() {
        // Use AtomicBoolean as it's final and can be modified inside the lambda
        final AtomicBoolean exited = new AtomicBoolean(false);

        // Create command with a "fake" exit runnable
        ExitCommand command = new ExitCommand(() -> exited.set(true));

        // Action
        command.execute();

        // Verify: was our fake exit runnable triggered?
        assertTrue(exited.get(), "Exiter runnable should be triggered");

    }
}