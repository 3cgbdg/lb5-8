package commands;

import coffee.Coffee;
import coffeevan.CoffeeVan;
import services.CoffeeStorageService;

import java.io.IOException;
import java.util.List;

/**
 * Command class responsible for loading coffee data from a text file
 * and restoring it into the {@link CoffeeVan}.
 *
 * <p>Each line in the file represents a coffee item previously saved
 * using the {@link Coffee#toFileString()} method. The static method
 * {@link Coffee#fromFileString(String)} reconstructs the correct {@link Coffee}
 * subclass (e.g., BeanCoffee, GroundCoffee, InstantCoffee) from the text data.</p>
 *
 * <p>File name: {@code coffee_data.txt}</p>
 *
 * Example file content:
 * <pre>
 * BEAN;1234;Arabica;250.0;15.99;8.0;9.0;7.0;Paper;250.0;Brazil;MEDIUM
 * GROUND;5678;Robusta;100.0;5.99;7.0;6.0;8.0;Plastic;100.0;COARSE
 * </pre>
 */
public class GetFromFileCommand implements Command {
    private final CoffeeVan coffeeVan;
    private final CoffeeStorageService storageService;
    /**
     * Constructs a new {@code GetFromFileCommand} instance.
     *
     * @param coffeeVan the coffee van into which the data will be loaded
     */
    public GetFromFileCommand(CoffeeVan coffeeVan, CoffeeStorageService storageService) {
        this.coffeeVan = coffeeVan;
        this.storageService =storageService;
    }
    /**
     * Executes the command to read coffee data from the file
     * and add each valid {@link Coffee} object to the {@link CoffeeVan}.
     *
     * @throws IOException if an I/O error occurs while reading the file
     */
    @Override
    public void execute() throws IOException {
        List<Coffee> loadedCoffee = storageService.getFromFile();
        for(Coffee coffee :loadedCoffee){
            coffeeVan.addCoffee(coffee);
        }
        System.out.println("Successfully received!\n");

    }
}


