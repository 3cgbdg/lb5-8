package commands;

import coffeevan.CoffeeVan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Command that displays the remaining available volume in the {@link CoffeeVan}.
 */
public class CheckRemainVolumeCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CheckRemainVolumeCommand.class);
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
        LOGGER.info("Remaining Volume: {} ml", coffeeVan.getRemainingVolume());
    }
}


