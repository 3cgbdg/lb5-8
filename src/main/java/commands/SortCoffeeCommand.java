package commands;

import coffee.Coffee;
import coffeevan.CoffeeVan;

/**
 * Command that sorts all coffee items in the {@link CoffeeVan}
 * by their price-to-weight ratio (price per kilogram).
 */
public class SortCoffeeCommand implements Command {
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
            System.out.println("There is no coffee yet!");
            return;
        }
        coffeeVan.sortByPricePerKg();
        System.out.println("Sorted cargo:");
        for (Coffee coffee : coffeeVan.getCargo()) {
            System.out.println(coffee.getInfo());
        }
    }
}
