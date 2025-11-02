package coffee;


import coffee.enums.ConcentrationLevel;
import packaging.Packaging;
import qualityparams.QualityParams;

/**
 * Represents a type of instant coffee.
 * Contains information about the concentration level.
 */
public class InstantCoffee extends Coffee {
    private final ConcentrationLevel concentrationLevel;

    /**
     * Constructs a new {@code InstantCoffee} instance.
     *
     * @param name               the coffee name
     * @param weight             the coffee weight in grams
     * @param price              the coffee price in USD
     * @param quality            the quality parameters
     * @param packaging          the packaging details
     * @param concentrationLevel the concentration level of the instant coffee
     */
    public InstantCoffee(String name, double weight, double price, QualityParams quality, Packaging packaging, ConcentrationLevel concentrationLevel, String id) {
        super(name, weight, price, quality, packaging, id);
        this.concentrationLevel = concentrationLevel;
    }

    public InstantCoffee(String name, double weight, double price, QualityParams quality, Packaging packaging, ConcentrationLevel concentrationLevel) {
        super(name, weight, price, quality, packaging);
        this.concentrationLevel = concentrationLevel;
    }

    /**
     * Returns a detailed description of this coffee, including all properties.
     *
     * @return a string representation of the coffee details
     */
    @Override
    public String getInfo() {
        return "InstantCoffee {" +
                "ID='" + getId() + '\'' +
                ", Name='" + getName() + '\'' +
                ", Weight=" + getWeight() + "g" +
                ", Price=" + getPrice() + "$" +
                ", Concentration Level=" + getConcentrationLevel() +
                ", Aroma=" + getQuality().getAromaScore() +
                ", Taste=" + getQuality().getTasteScore() +
                ", Freshness=" + getQuality().getFreshnessScore() +
                ", " + getPackaging().getInfo() +
                '}';
    }

    /**
     * @return the concentration level of the instant coffee
     */
    public ConcentrationLevel getConcentrationLevel() {
        return concentrationLevel;
    }

    /**
     * Converts this {@code InstantCoffee} object into a line of text for saving to a file.
     * <p>
     * Format:
     * <pre>
     * INSTANT;ID;Name;Weight;Price;Aroma;Taste;Freshness;Material;Volume;ConcentrationLevel
     * </pre>
     * </p>
     *
     * @return a formatted string representing this {@code InstantCoffee}
     */
    @Override
    public String toFileString() {
        return String.join(";",
                "BEAN",
                getId(),
                getName(),
                String.valueOf(getWeight()),
                String.valueOf(getPrice()),
                String.valueOf(getQuality().getAromaScore()),
                String.valueOf(getQuality().getTasteScore()),
                String.valueOf(getQuality().getFreshnessScore()),
                getPackaging().getMaterial(),
                String.valueOf(getPackaging().getVolume()),
                String.valueOf(concentrationLevel)
        );
    }
}
