package commands;

/**
 * Command that terminates the application.
 */
public class ExitCommand implements Command {
    /**
     * Prints a shutdown message and exits the program.
     */
    @Override
    public void execute() {
        System.out.println("The program has finished working!");
        System.exit(0);
    }
}
