package commands;

import coffeevan.CoffeeVan;

/**
 * Command that calculates and displays the total cost of all coffee items
 * currently loaded in the {@link CoffeeVan}.
 */
public class CountTotalPriceCommand implements Command {
    private final CoffeeVan coffeeVan;

    /**
     * Constructs a new command for counting the total price of coffee.
     *
     * @param coffeeVan the {@link CoffeeVan} instance to operate on
     */
    public CountTotalPriceCommand(CoffeeVan coffeeVan) {
        this.coffeeVan = coffeeVan;
    }

    /**
     * Prints the total price to the console.
     */
    @Override
    public void execute() {
        System.out.format("Total price is %f", coffeeVan.getTotalCost());
    }
}


