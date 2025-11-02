package coffee;


import coffee.enums.RoastLevel;
import packaging.Packaging;
import qualityparams.QualityParams;

/**
 * Represents a type of coffee made from whole beans.
 * Contains information about roast level and origin.
 */
public class BeanCoffee extends Coffee {
    private final RoastLevel roastLevel;
    private final String origin;


    /**
     * Constructs a new {@code BeanCoffee} instance.
     *
     * @param name       the coffee name
     * @param weight     the coffee weight in grams
     * @param price      the coffee price in USD
     * @param quality    the quality parameters
     * @param packaging  the packaging details
     * @param roastLevel the roast level of the beans
     * @param origin     the country or region of origin
     */
    public BeanCoffee(String name, double weight, double price, QualityParams quality, Packaging packaging, RoastLevel roastLevel, String origin,String id) {
        super(name, weight, price, quality, packaging,id);
        this.roastLevel = roastLevel;
        this.origin = origin;
    }
    public BeanCoffee(String name, double weight, double price, QualityParams quality, Packaging packaging, RoastLevel roastLevel, String origin) {
        super(name, weight, price, quality, packaging);
        this.roastLevel = roastLevel;
        this.origin = origin;
    }

    /**
     * Returns a detailed description of this coffee, including all properties.
     *
     * @return a string representation of the coffee details
     */
    @Override
    public String getInfo() {
        return "BeanCoffee {" +
                "ID='" + getId() + '\'' +
                ", Name='" + getName() + '\'' +
                ", Weight=" + getWeight() + "g" +
                ", Price=" + getPrice() + "$" +
                ", Roast Level=" + getRoastLevel() +
                ", Origin='" + getOrigin() + '\'' +
                ", Aroma=" + getQuality().getAromaScore() +
                ", Taste=" + getQuality().getTasteScore() +
                ", Freshness=" + getQuality().getFreshnessScore() +
                ", " + getPackaging().getInfo() +
                '}';
    }

    /**
     * @return the roast level of the coffee beans
     */
    public RoastLevel getRoastLevel() {
        return roastLevel;
    }

    /**
     * @return the origin (country or region) of the coffee beans
     */
    public String getOrigin() {
        return origin;
    }
    /**
     * Converts this {@code BeanCoffee} object into a line of text for saving to a file.
     * <p>
     * Format:
     * <pre>
     * BEAN;ID;Name;Weight;Price;Aroma;Taste;Freshness;Material;Volume;Origin;RoastLevel
     * </pre>
     * </p>
     *
     * @return a formatted string representing this {@code BeanCoffee}
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
                origin,
                String.valueOf(roastLevel)
        );
    }
}
