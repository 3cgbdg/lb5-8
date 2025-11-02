package commands;

import java.io.IOException;

/**
 * Represents a command in the Command design pattern.
 * <p>
 * Each command encapsulates a specific user action and
 * defines a single method {@link #execute()} to perform it.
 */
public interface Command {
    /**
     * Executes the command action.
     */
    void execute() throws IOException;
}
