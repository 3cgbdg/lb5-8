package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExitCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ExitCommand.class);

    private final Runnable exiter;

    public ExitCommand() {
        this(() -> System.exit(0));
    }

    // конструктор для тесту
    public ExitCommand(Runnable exiter) {
        this.exiter = exiter;
    }

    @Override
    public void execute() {
        LOGGER.info("Program is exiting by user command.");
        exiter.run();
    }
}
