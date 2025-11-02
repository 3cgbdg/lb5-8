package coffeevan;

import coffee.Coffee;

import java.util.*;

/**
 * Represents a coffee delivery van that stores and manages coffee cargo.
 * The van has a maximum capacity and budget, allowing operations such as
 * adding, removing, sorting, and filtering coffee products.
 */
public class CoffeeVan {
    private final double maxVolume;
    private final double maxBudget;
    //list for cargo storing
    private final List<Coffee> cargo = new ArrayList<>();

    /**
     * Constructs a new {@code CoffeeVan} instance.
     *
     * @param maxVolume the maximum cargo volume (in milliliters)
     * @param maxBudget the maximum budget (in USD)
     */
    public CoffeeVan(double maxVolume, double maxBudget) {
        this.maxVolume = maxVolume;
        this.maxBudget = maxBudget;
    }

    /**
     * Adds a coffee item to the van’s cargo.
     *
     * @param coffee the coffee item to add
     * @return {@code true} if the coffee was added successfully
     * @throws IllegalArgumentException if the provided coffee is {@code null}
     */
    public boolean addCoffee(Coffee coffee) {

        if (coffee == null) {
            throw new IllegalArgumentException("Invalid value");
        }
        cargo.add(coffee);
        return true;
    }

    /**
     * Removes a coffee item from the cargo by its ID.
     *
     * @param id the ID of the coffee to remove
     * @return {@code true} if the coffee was found and removed; {@code false} otherwise
     */

    public boolean removeCoffeeById(String id) {
        //using iterator for save deleting an item out of the array
        Iterator<Coffee> it = cargo.iterator();
        while (it.hasNext()) {
            Coffee coffee = it.next();
            // comparing by uuid ids
            if (Objects.equals(coffee.getId(), id)) {
                it.remove();

                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the total volume of all coffee items currently in the van.
     *
     * @return the total volume (in milliliters)
     */
    public double getTotalVolume() {
        double acc = 0;
        for (Coffee coffee : cargo) {
            acc += coffee.getTotalVolume();
        }
        return acc;
    }

    /**
     * Prints detailed information about the van’s cargo to the console.
     */
    public void displayCargoInfo() {
        System.out.println("CoffeeVan {");
        System.out.println("  Max Volume: " + maxVolume + " ml");
        System.out.println("  Max Budget: " + maxBudget + " $");
        System.out.println("  Current Total Volume: " + getTotalVolume() + " ml");
        System.out.println("  Current Total Cost: " + getTotalCost() + " ml");
        System.out.println("  Coffee List:");
        if (cargo.isEmpty()) {
            System.out.println("    [No coffee in the van]");
        } else {
            for (Coffee coffee : cargo) {
                System.out.println("    - " + coffee.getInfo());
            }
        }

        System.out.println("}");
    }

    /**
     * Calculates the total cost of all coffee items in the van.
     *
     * @return the total cost (in USD)
     */
    public double getTotalCost() {
        double acc = 0;
        for (Coffee coffee : cargo) {
            acc += coffee.getPrice();
        }
        return acc;
    }

    /**
     * Calculates the remaining available volume.
     *
     * @return the remaining volume (in milliliters)
     */
    public double getRemainingVolume() {
        return maxVolume - getTotalVolume();
    }

    /**
     * Calculates the remaining available budget.
     *
     * @return the remaining budget (in USD)
     */
    public double getRemainingBudget() {
        return maxBudget - getTotalCost();
    }

    /**
     * @return the list of all coffee items currently in the van
     */
    public List<Coffee> getCargo() {
        return cargo;
    }

    /**
     * Sorts the coffee cargo by price-to-weight ratio (ascending).
     */
    public void sortByPricePerKg() {
        //using Comparator interface to create a custom comparing system
        Comparator<Coffee> byPricePerKg = Comparator.comparingDouble(Coffee::getPriceToWeightRatio);
        cargo.sort(byPricePerKg);
    }

    /**
     * Finds coffee items that fall within the given quality ranges.
     *
     * @param minAroma     minimum aroma score
     * @param maxAroma     maximum aroma score
     * @param minTaste     minimum taste score
     * @param maxTaste     maximum taste score
     * @param minFreshness minimum freshness score
     * @param maxFreshness maximum freshness score
     * @return a list of coffee items matching the given quality criteria
     */
    public List<Coffee> findByQuality(double minAroma, double maxAroma, double minTaste, double maxTaste, double minFreshness, double maxFreshness) {
        List<Coffee> foundCoffeeArray = new ArrayList<>();
        for (Coffee coffee : cargo) {
            if (coffee.getQuality().isInRange(minAroma, maxAroma, minTaste, maxTaste, minFreshness, maxFreshness))
                foundCoffeeArray.add(coffee);
        }
        return foundCoffeeArray;
    }


}
