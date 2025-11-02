package services;
import coffee.Coffee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeStorageService {
    private  String filename;


    /**
     * Constructor that defines the name of the file we are going to handle data from
     * @param filename filepath for the file
     */
    public CoffeeStorageService(String filename) {
        this.filename = filename;
    }

    public void saveToFile(List<Coffee> coffeeList) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename,true))) {
            for (Coffee coffee : coffeeList) {
                writer.println(coffee.toFileString());
            }
        }
    }

    public List<Coffee> getFromFile()throws IOException {
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
        return loadedCoffee;
    }




}
