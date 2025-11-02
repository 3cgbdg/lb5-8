package commands;

import coffee.Coffee;
import coffeevan.CoffeeVan;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Command that removes a coffee item from the {@link CoffeeVan} by its unique ID.
 */
public class RemoveByIdCommand implements Command {
    private final CoffeeVan coffeeVan;

    /**
     * Constructs a new command for removing coffee by ID.
     *
     * @param coffeeVan the {@link CoffeeVan} instance to operate on
     */
    public RemoveByIdCommand(CoffeeVan coffeeVan) {
        this.coffeeVan = coffeeVan;
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
                System.out.println("ID cannot be empty! Try again.");
            }
        }
        boolean isRemoved = coffeeVan.removeCoffeeById(id);
        if(isRemoved){
            try (PrintWriter writer = new PrintWriter(new FileWriter("coffee_data.csv"))) {
                for (Coffee coffee : coffeeVan.getCargo()) {
                    writer.println(coffee.toFileString());
                }
            }
        }
        System.out.println(isRemoved ? "Item was successfully removed!" : "Item was not found!");


    }
}


