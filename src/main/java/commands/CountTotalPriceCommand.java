package commands;

import coffeevan.CoffeeVan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Command that calculates and displays the total cost of all coffee items
 * currently loaded in the {@link CoffeeVan}.
 */
public class CountTotalPriceCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CountTotalPriceCommand.class);
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
        LOGGER.info("Total cargo price: {} $", coffeeVan.getTotalCost());
    }
}


