package commands;

import coffee.Coffee;
import coffeevan.CoffeeVan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Command that sorts all coffee items in the {@link CoffeeVan}
 * by their price-to-weight ratio (price per kilogram).
 */
public class SortCoffeeCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SortCoffeeCommand.class);
    private final CoffeeVan coffeeVan;
    /**
     * Constructs a new command for sorting coffee items.
     *
     * @param coffeeVan the {@link CoffeeVan} instance to operate on
     */
    public SortCoffeeCommand(CoffeeVan coffeeVan) {
        this.coffeeVan = coffeeVan;
    }

    /**
     * Sorts the van’s coffee list and prints the sorted result.
     */
    @Override
    public void execute() {
        if (coffeeVan.getCargo().isEmpty()) {
            LOGGER.warn("Cannot sort: van is empty!");
            return;
        }
        LOGGER.info("Sorting cargo by price/weight ratio...");
        coffeeVan.sortByPricePerKg();
        LOGGER.info("Sorted cargo:");
        for (Coffee coffee : coffeeVan.getCargo()) {
            LOGGER.info(coffee.getInfo());
        }
    }
}
