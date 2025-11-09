package commands;

import coffeevan.CoffeeVan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Command that displays the remaining budget available in the {@link CoffeeVan}.
 */
public class CheckRemainBudgetCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CheckRemainBudgetCommand.class);
    private final CoffeeVan coffeeVan;

    /**
     * Constructs a new command for checking remaining budget.
     *
     * @param coffeeVan the {@link CoffeeVan} instance to operate on
     */
    public CheckRemainBudgetCommand(CoffeeVan coffeeVan) {
        this.coffeeVan = coffeeVan;
    }

    /**
     * Prints the remaining budget to the console.
     */
    @Override
    public void execute() {
        LOGGER.info("Remaining Budget: {} $", coffeeVan.getRemainingBudget());
    }
}


