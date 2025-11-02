package commands;

import coffeevan.CoffeeVan;

/**
 * Command that displays the remaining budget available in the {@link CoffeeVan}.
 */
public class CheckRemainBudgetCommand implements Command {
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
        System.out.format("Remaining Budget is %f", coffeeVan.getRemainingBudget());
    }
}


