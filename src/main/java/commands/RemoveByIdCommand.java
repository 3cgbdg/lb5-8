package commands;

import coffee.Coffee;
import coffeevan.CoffeeVan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.CoffeeStorageService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Command that removes a coffee item from the {@link CoffeeVan} by its unique ID.
 */
public class RemoveByIdCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveByIdCommand.class);
    private final CoffeeVan coffeeVan;
    private CoffeeStorageService coffeeStorage;

    /**
     * Constructs a new command for removing coffee by ID.
     *
     * @param coffeeVan the {@link CoffeeVan} instance to operate on
     */
    public RemoveByIdCommand(CoffeeVan coffeeVan, CoffeeStorageService coffeeStorage) {
        this.coffeeVan = coffeeVan;
        this.coffeeStorage = coffeeStorage;
    }

    /**
     * Reads the ID from console input and removes the matching coffee from the van.
     */
    @Override
    public void execute() throws IOException {
        Scanner sc = new Scanner(System.in);
        String id = "";
//        doing while id is not empty (validation)
        while (id.isEmpty()) {
            System.out.print("Type in ID of a product you want to remove: ");
            id = sc.nextLine().trim();
            if (id.isEmpty()) {
                LOGGER.warn("ID cannot be empty! Try again.");
            }
        }
        boolean isRemoved = coffeeVan.removeCoffeeById(id);
        if (isRemoved) {
            try {
                coffeeStorage.saveToFile(coffeeVan.getCargo(),false);
                LOGGER.info("Cargo updated file (after removal).");
            } catch (IOException e) {
                LOGGER.error("Failed to update after removal.", e);
                throw e;
            }

        }
    }
}


