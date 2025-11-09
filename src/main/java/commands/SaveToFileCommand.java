package commands;

import coffee.Coffee;
import coffeevan.CoffeeVan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.CoffeeStorageService;

import java.io.IOException;
import java.util.List;

/**
 * Command class responsible for saving the current cargo (list of coffee objects)
 * from the {@link CoffeeVan} into a text file.
 *
 * <p>Each coffee object is serialized into a text line using the
 * {@link Coffee#toFileString()} method and written to the file {@code coffee_data.txt}.
 * This allows persistent storage of van data between program runs.</p>
 *
 * <p>File format: each line contains a coffee record with its parameters separated by semicolons.</p>
 * <p>
 * Example:
 * <pre>
 * BEAN;1234;Arabica;250.0;15.99;8.0;9.0;7.0;Paper;250.0;Brazil;MEDIUM
 * </pre>
 */
public class SaveToFileCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SaveToFileCommand.class);
    private final CoffeeVan coffeeVan;
    private final CoffeeStorageService storageService;

    /**
     * Constructs a new {@code LoadFromFileCommand} instance.
     *
     * @param coffeeVan the coffee van whose cargo will be saved to file
     */
    public SaveToFileCommand(CoffeeVan coffeeVan, CoffeeStorageService storageService) {
        this.coffeeVan = coffeeVan;
        this.storageService = storageService;
    }

    /**
     * Executes the command to save coffee data to the file.
     *
     * @throws IOException if an I/O error occurs while writing the file
     */
    @Override
    public void execute() throws IOException {
        List<Coffee> cargo = coffeeVan.getCargo();
        LOGGER.info("Saving {} cargo items to file...", cargo.size());
        storageService.saveToFile(cargo, true);
        LOGGER.info("Successfully saved to file!");
    }
}


