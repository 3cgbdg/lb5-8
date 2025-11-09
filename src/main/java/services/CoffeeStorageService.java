package services;
import coffee.Coffee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeStorageService {
    private  String filename;
    private static final Logger LOGGER = LogManager.getLogger(CoffeeStorageService.class);


    /**
     * Constructor that defines the name of the file we are going to handle data from
     * @param filename filepath for the file
     */
    public CoffeeStorageService(String filename) {
        this.filename = filename;
        LOGGER.info("StorageService initialized. File: {}", filename);
    }
    public void saveToFile(List<Coffee> coffeeList, boolean toNotReplaceFully) throws IOException {
        LOGGER.info("Saving {} coffee items to file {}...", coffeeList.size(), filename);
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename,toNotReplaceFully))) {
            for (Coffee coffee : coffeeList) {
                writer.println(coffee.toFileString());
            }
            LOGGER.info("Successfully saved to file {}.", filename);
        }
    }

    public List<Coffee> getFromFile()throws IOException {
        LOGGER.info("Reading coffee from file {}...", filename);
        // temporary arrayList as returning value
        List<Coffee> loadedCoffee = new ArrayList<>();
        try (
                BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Coffee coffee = Coffee.fromFileString(line);
                if (coffee != null) {
                    loadedCoffee.add(coffee);
                }
            }

        }
        LOGGER.info("Successfully loaded {} items from file {}.", loadedCoffee.size(), filename);
        return loadedCoffee;
    }




}
