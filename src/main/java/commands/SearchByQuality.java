package commands;

import coffee.Coffee;
import coffeevan.CoffeeVan;

import java.util.List;
import java.util.Scanner;

/**
 * Command that searches for coffee items within a range of quality parameters.
 * <p>
 * Filters {@link Coffee} objects by aroma, taste, and freshness values.
 */
public class SearchByQuality implements Command {
    private final CoffeeVan coffeeVan;

    /**
     * Constructs a new command for searching coffee by quality.
     *
     * @param coffeeVan the {@link CoffeeVan} instance to operate on
     */
    public SearchByQuality(CoffeeVan coffeeVan) {
        this.coffeeVan = coffeeVan;
    }

    /**
     * Prompts the user for minimum and maximum quality values and displays all matching coffee.
     */
    @Override
    public void execute() {
        List<Coffee> coffees = coffeeVan.getCargo();
//        if empty returning void
        if (coffees.isEmpty()) {
            System.out.println("There is no coffee yet!");
            return;
        }
        Scanner sc = new Scanner(System.in);

        double minAroma, maxAroma;
        while (true) {

            minAroma = readDouble(sc, "Enter minimum aroma (1-10): ");
            maxAroma = readDouble(sc, "Enter maximum aroma (1-10): ");
            if (minAroma <= maxAroma) break;
            System.out.println("Minimum cannot be greater than maximum. Try again.");
        }

        double minTaste, maxTaste;
        while (true) {
            minTaste = readDouble(sc, "Enter minimum taste (1-10): ");
            maxTaste = readDouble(sc, "Enter maximum taste (1-10): ");
            if (minTaste <= maxTaste) break;
            System.out.println("Minimum cannot be greater than maximum. Try again.");
        }

        double minFreshness, maxFreshness;
        while (true) {
            minFreshness = readDouble(sc, "Enter minimum freshness (1-10): ");
            maxFreshness = readDouble(sc, "Enter maximum freshness (1-10): ");
            if (minFreshness <= maxFreshness) break;
            System.out.println("Minimum cannot be greater than maximum. Try again.");
        }
    // creating a list of found coffees with such a quality-range
        List<Coffee> foundItems = coffeeVan.findByQuality(
                minAroma, maxAroma, minTaste, maxTaste, minFreshness, maxFreshness
        );

        if (foundItems.isEmpty()) {
            System.out.println("No coffee found matching these quality parameters.");
        } else {
            System.out.println("Found items:");
            for (Coffee item : foundItems) {
                System.out.println(item.getInfo());
            }
        }
    }


    private double readDouble(Scanner sc, String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            try {
                //parsing value from string to double
                value = Double.parseDouble(sc.nextLine());
                if (value >= 1 && value <= 10) return value;
                else System.out.println("Value must be between 1 and 10.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
            }
        }
    }
}
