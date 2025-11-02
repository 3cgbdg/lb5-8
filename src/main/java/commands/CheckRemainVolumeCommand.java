package commands;

import coffeevan.CoffeeVan;

/**
 * Command that displays the remaining available volume in the {@link CoffeeVan}.
 */
public class CheckRemainVolumeCommand implements Command {
    private final CoffeeVan coffeeVan;

    /**
     * Constructs a new command for checking remaining volume.
     *
     * @param coffeeVan the {@link CoffeeVan} instance to operate on
     */
    public CheckRemainVolumeCommand(CoffeeVan coffeeVan) {
        this.coffeeVan = coffeeVan;
    }

    /**
     * Prints the remaining volume to the console.
     */
    @Override
    public void execute() {
        System.out.format("Remaining volume is %f", coffeeVan.getRemainingVolume());
    }
}


