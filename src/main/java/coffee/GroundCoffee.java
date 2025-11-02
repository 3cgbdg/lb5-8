package coffee;


import coffee.enums.GrindSize;
import packaging.Packaging;
import qualityparams.QualityParams;

/**
 * Represents a type of coffee made from ground beans.
 * Includes information about the grind size.
 */
public class GroundCoffee extends Coffee {
    private final GrindSize grindSize;

    /**
     * Constructs a new {@code GroundCoffee} instance.
     *
     * @param name      the coffee name
     * @param weight    the coffee weight in grams
     * @param price     the coffee price in USD
     * @param quality   the quality parameters
     * @param packaging the packaging details
     * @param grindSize the grind size (fine, medium, coarse)
     */
    public GroundCoffee(String name, double weight, double price, QualityParams quality, Packaging packaging, GrindSize grindSize, String id) {
        super(name, weight, price, quality, packaging, id);
        this.grindSize = grindSize;
    }

    public GroundCoffee(String name, double weight, double price, QualityParams quality, Packaging packaging, GrindSize grindSize) {
        super(name, weight, price, quality, packaging);
        this.grindSize = grindSize;
    }

    /**
     * Returns a detailed description of this coffee, including all properties.
     *
     * @return a string representation of the coffee details
     */
    @Override
    public String getInfo() {
        return "GroundCoffee {" +
                "ID='" + getId() + '\'' +
                ", Name='" + getName() + '\'' +
                ", Weight=" + getWeight() + "g" +
                ", Price=" + getPrice() + "$" +
                ", Grind Size=" + getGrindSize() +
                ", Aroma=" + getQuality().getAromaScore() +
                ", Taste=" + getQuality().getTasteScore() +
                ", Freshness=" + getQuality().getFreshnessScore() +
                ", " + getPackaging().getInfo() +
                '}';

    }

    /**
     * @return the grind size of the coffee
     */
    public GrindSize getGrindSize() {
        return grindSize;
    }

    /**
     * Converts this {@code GroundCoffee} object into a line of text for saving to a file.
     * <p>
     * Format:
     * <pre>
     * GROUND;ID;Name;Weight;Price;Aroma;Taste;Freshness;Material;Volume;GrindSize
     * </pre>
     * </p>
     *
     * @return a formatted string representing this {@code GroundCoffee}
     */
    @Override
    public String toFileString() {
        return String.join(";",
                "GROUND",
                getId(),
                getName(),
                String.valueOf(getWeight()),
                String.valueOf(getPrice()),
                String.valueOf(getQuality().getAromaScore()),
                String.valueOf(getQuality().getTasteScore()),
                String.valueOf(getQuality().getFreshnessScore()),
                getPackaging().getMaterial(),
                String.valueOf(getPackaging().getVolume()),
                String.valueOf(grindSize)
        );
    }
}
