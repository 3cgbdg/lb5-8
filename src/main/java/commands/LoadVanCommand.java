package commands;

import coffee.BeanCoffee;
import coffee.Coffee;
import coffee.GroundCoffee;
import coffee.InstantCoffee;
import coffee.enums.ConcentrationLevel;
import coffee.enums.GrindSize;
import coffee.enums.RoastLevel;
import coffeevan.CoffeeVan;
import packaging.Packaging;
import qualityparams.QualityParams;

import java.util.Scanner;

/**
 * Command that allows the user to load new {@link Coffee} objects into the {@link CoffeeVan}.
 * <p>
 * It interacts with the user via console input and validates:
 * <ul>
 *     <li>Available van volume and budget</li>
 *     <li>Correct enum values for coffee properties</li>
 *     <li>Proper numeric and text inputs</li>
 * </ul>
 */
public class LoadVanCommand implements Command {
    private final CoffeeVan coffeeVan;

    /**
     * Constructs a new command for loading coffee into a van.
     *
     * @param coffeeVan the {@link CoffeeVan} instance to operate on
     */

    public LoadVanCommand(CoffeeVan coffeeVan) {
        this.coffeeVan = coffeeVan;
    }

    /**
     * Reads coffee data from the console, validates it, and adds the coffee item to the van.
     * <p>
     * Supports three coffee types:
     * <ul>
     *     <li>Bean Coffee ({@link BeanCoffee})</li>
     *     <li>Ground Coffee ({@link GroundCoffee})</li>
     *     <li>Instant Coffee ({@link InstantCoffee})</li>
     * </ul>
     */
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        // if not enough volumes or budget returning void
        while (true) {
            if (coffeeVan.getRemainingVolume() <= 0 || coffeeVan.getRemainingBudget() <= 0) {
                System.out.println(coffeeVan.getRemainingVolume() <= 0 ? "There is no volume in the van!" : "There is no budget left!");
                return;
            }

            String name = "";
            double weight = -1, price = -1;

            while (name.isEmpty()) {
                System.out.print("Enter coffee name: ");
                name = sc.nextLine().trim();
                if (name.isEmpty()) System.out.println("Name cannot be empty");

            }
            // weight
            //validating weight in case of negative value
            while (weight <= 0) {
                System.out.print("Enter weight (>0): ");
                try {
                    weight = Double.parseDouble(sc.nextLine());
                    if (weight <= 0) System.out.println("Weight must be positive!");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Enter a number.");
                }
            }

            // price
            //validating price in case of negative value

            while (price <= 0) {
                System.out.print("Enter price (>0): ");
                try {
                    price = Double.parseDouble(sc.nextLine());
                    if (price <= 0) System.out.println("Price must be positive!");
                    else if (price > coffeeVan.getRemainingBudget()) {
                        System.out.println("There is not enough budget to afford it!");
                        price = -1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Enter a number.");
                }
            }

            // coffee type
            String coffeeType = "";
            //validating a coffee type until getting true value
            while (!(coffeeType.equalsIgnoreCase("bean") || coffeeType.equalsIgnoreCase("ground") || coffeeType.equalsIgnoreCase("instant"))) {
                System.out.print("Type in coffee type (bean | ground | instant): ");
                coffeeType = sc.nextLine().trim().toLowerCase();
                if (!(coffeeType.equals("bean") || coffeeType.equals("ground") || coffeeType.equals("instant")))
                    System.out.println("Invalid type! Please enter bean, ground or instant.");
            }

            // spec fields
            RoastLevel roastLevel = null;
            GrindSize grindSize = null;
            ConcentrationLevel concentrationLevel = null;
            String origin = null;

            if (coffeeType.equals("bean")) {
                //validating spec fields
                while (roastLevel == null || origin == null) {
                    System.out.println("Enter origin and roast level <origin roastLevel(LIGHT/MEDIUM/DARK)>:");
                    //getting split input for getting parts [0] - origin, [1] - roast level
                    String[] parts = sc.nextLine().trim().split(" ");
                    if (parts.length < 2) {
                        System.out.println("Please enter both origin and roast level!");
                        continue;
                    }
                    origin = parts[0];
                    try {
                        //parsing into enum value
                        roastLevel = RoastLevel.valueOf(parts[1].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid roast level! Try again.");
                        roastLevel = null;
                    }
                }
            } else if (coffeeType.equals("ground")) {
                while (grindSize == null) {
                    System.out.println("Enter grind size (FINE/MEDIUM/COARSE):");
                    String input = sc.nextLine().trim().toUpperCase();
                    try {
                        grindSize = GrindSize.valueOf(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid grind size! Try again.");
                    }
                }
            } else {
                while (concentrationLevel == null) {
                    System.out.println("Enter concentration level (LOW/MEDIUM/HIGH):");
                    String input = sc.nextLine().trim().toUpperCase();
                    try {
                        concentrationLevel = ConcentrationLevel.valueOf(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid concentration level! Try again.");
                    }
                }
            }
            //quality scores
            double aromaScore = readScore(sc, "Aroma score (1-10): ");
            double tasteScore = readScore(sc, "Taste score (1-10): ");
            double freshnessScore = readScore(sc, "Freshness score (1-10): ");

            //packaging
            String material;
            double volume = -1;

            System.out.print("Enter packaging material: ");
            material = sc.nextLine().trim();

            while (volume <= 0) {
                System.out.print("Enter packaging volume (>0): ");
                try {
                    volume = Double.parseDouble(sc.nextLine());
                    if (volume <= 0) System.out.println("Volume must be positive!");
                    else if (volume > coffeeVan.getRemainingVolume()) {
                        System.out.println("There is not enough volume in the van!");
                        volume = -1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Enter a number.");
                }
            }

            //object creation
            QualityParams quality = new QualityParams(aromaScore, tasteScore, freshnessScore);
            Packaging packaging = new Packaging(material, volume);

            boolean isLoaded;

            if (coffeeType.equals("bean")) {
                isLoaded = coffeeVan.addCoffee(new BeanCoffee(name, weight, price, quality, packaging, roastLevel, origin));
            } else if (coffeeType.equals("ground")) {
                isLoaded = coffeeVan.addCoffee(new GroundCoffee(name, weight, price, quality, packaging, grindSize));
            } else {
                isLoaded = coffeeVan.addCoffee(new InstantCoffee(name, weight, price, quality, packaging, concentrationLevel));
            }

            System.out.println(isLoaded ? "Item has been successfully loaded!" : "Something went wrong!");

            System.out.print("Want to stop (y/n)? ");
            String answer = sc.nextLine();
            if (answer.equalsIgnoreCase("y")) break;
        }
    }

    /**
     * Reads Scores data from the console, validates it.
     *
     * @return score --- if validated value
     */
    private double readScore(Scanner sc, String prompt) {
        double score;
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            try {
                //parsing line for getting double value
                score = Double.parseDouble(line);
                if (score >= 1 && score <= 10) break;
                else System.out.println("Score must be between 1 and 10!");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
        return score;
    }
}


