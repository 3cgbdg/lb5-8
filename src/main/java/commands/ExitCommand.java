package commands;

public class ExitCommand implements Command {
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
        System.out.println("The program has finished working!");
        exiter.run();
    }
}
