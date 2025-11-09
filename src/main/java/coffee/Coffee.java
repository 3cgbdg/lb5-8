package coffee;

import coffee.enums.ConcentrationLevel;
import coffee.enums.GrindSize;
import coffee.enums.RoastLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packaging.Packaging;
import qualityparams.QualityParams;

import java.util.UUID;

/**
 * Abstract base class representing a general coffee product.
 * Serves as the foundation for specific coffee types such as bean, ground, and instant coffee.
 */
abstract public class Coffee {
    private static final Logger LOGGER = LogManager.getLogger(Coffee.class);
    private final String name;
    private final double weight;
    private final double price;
    private final QualityParams quality;
    private final Packaging packaging;
    private final String id;

    /**
     * Constructs a new {@code Coffee} instance.
     *
     * @param name      the coffee name
     * @param weight    the coffee weight in grams
     * @param price     the coffee price in USD
     * @param quality   the quality parameters (aroma, freshness, taste)
     * @param packaging the packaging details
     */
    public Coffee(String name, double weight, double price, QualityParams quality, Packaging packaging) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.quality = quality;
        this.packaging = packaging;
        this.id = UUID.randomUUID().toString();
    }

    public Coffee(String name, double weight, double price, QualityParams quality, Packaging packaging, String id) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.quality = quality;
        this.packaging = packaging;
        this.id = id;
    }

    /**
     * @return the weight of the coffee in grams
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return the name of the coffee
     */
    public String getName() {
        return name;
    }

    /**
     * @return the price of the coffee in USD
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return a unique identifier (UUID) for this coffee
     */
    public String getId() {
        return id;
    }

    /**
     * @return the quality parameters of the coffee
     */
    public QualityParams getQuality() {
        return quality;
    }

    /**
     * @return the packaging information
     */
    public Packaging getPackaging() {
        return packaging;
    }

    /**
     * Returns the total packaging volume.
     *
     * @return the volume of the packaging in milliliters
     */
    public double getTotalVolume() {
        Packaging packaging = getPackaging();
        return packaging.getVolume();
    }

    /**
     * Calculates the price-to-weight ratio.
     *
     * @return the ratio of price per gram
     */
    public double getPriceToWeightRatio() {
        return price / weight;
    }

    /**
     * Returns detailed information about the coffee.
     * Each subclass provides its own formatted description.
     *
     * @return a string with detailed coffee information
     */
    public abstract String getInfo();

    /**
     * Converts this coffee object into a text line suitable for file storage.
     * <p>
     * Each subclass of {@code Coffee} implements its own version of this method
     * to include specific fields (e.g., roast level, grind size, or concentration level).
     * The resulting string always uses semicolon (;) as a separator.
     * </p>
     *
     * <p>Example (for BeanCoffee):</p>
     * <pre>
     * BEAN;123e4567;Arabica;250.0;15.99;8.0;9.0;7.0;Paper;250.0;Brazil;MEDIUM
     * </pre>
     *
     * @return a string representation of the coffee suitable for saving to file
     */
    public abstract String toFileString();

    /**
     * Reconstructs a {@link Coffee} object from a single line of text.
     * <p>
     * This static method determines the coffee type (bean, ground, instant)
     * based on the first field of the line and creates the corresponding subclass.
     * </p>
     *
     * <p>Expected format:</p>
     * <pre>
     * TYPE;ID;Name;Weight;Price;Aroma;Taste;Freshness;Material;Volume;[ExtraFields...]
     * </pre>
     *
     * <ul>
     *   <li><b>TYPE</b> — "BEAN", "GROUND", or "INSTANT"</li>
     *   <li><b>ExtraFields</b> — depend on the coffee type:
     *       <ul>
     *         <li>BeanCoffee → origin;roastLevel</li>
     *         <li>GroundCoffee → grindSize</li>
     *         <li>InstantCoffee → concentrationLevel</li>
     *       </ul>
     *   </li>
     * </ul>
     *
     * @param s the line read from the file
     * @return a {@link Coffee} object created from the data, or {@code null} if invalid
     */
    public static Coffee fromFileString(String s) {
        try {
            String[] parts = s.split(";");
            if (parts.length < 10) {
                throw new IllegalArgumentException("Invalid data format: " + s);
            }

            String type = parts[0];
            String id = parts[1];
            String name = parts[2];
            double weight = Double.parseDouble(parts[3]);
            double price = Double.parseDouble(parts[4]);
            double aroma = Double.parseDouble(parts[5]);
            double taste = Double.parseDouble(parts[6]);
            double freshness = Double.parseDouble(parts[7]);
            String material = parts[8];
            double volume = Double.parseDouble(parts[9]);

            QualityParams q = new QualityParams(aroma, taste, freshness);
            Packaging p = new Packaging(material, volume);

            //after creating spec coffee object
            switch (type.toLowerCase()) {
                case "bean":
                    String origin = parts[10];
                    RoastLevel roast = RoastLevel.valueOf(parts[11].toUpperCase());
                    return new BeanCoffee(name, weight, price, q, p, roast, origin, id);
                case "ground":
                    GrindSize grind = GrindSize.valueOf(parts[10].toUpperCase());
                    return new GroundCoffee(name, weight, price, q, p, grind, id);
                case "instant":
                    ConcentrationLevel level = ConcentrationLevel.valueOf(parts[10].toUpperCase());
                    return new InstantCoffee(name, weight, price, q, p, level, id);
                default:
                    throw new IllegalArgumentException("Unknown coffee type: " + type);
            }
        } catch (Exception e) {
            LOGGER.error("Critical Error: Failed to parse line from file. Line: '{}'", s, e);
            return null;
        }
    }
}
