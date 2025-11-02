package commands;

import coffeevan.CoffeeVan;

/**
 * Command that displays detailed information about all coffee items in the {@link CoffeeVan}.
 */
public class ShowCoffeeCommand implements Command {
    private final CoffeeVan coffeeVan;

    /**
     * Constructs a new command for displaying coffee info.
     *
     * @param coffeeVan the {@link CoffeeVan} instance to operate on
     */
    public ShowCoffeeCommand(CoffeeVan coffeeVan) {
        this.coffeeVan = coffeeVan;
    }

    /**
     * Calls the {@link CoffeeVan#displayCargoInfo()} method to show current van state.
     */
    @Override
    public void execute() {
        coffeeVan.displayCargoInfo();
    }
}
