package packaging;

/**
 * Represents the packaging used for coffee storage and transport.
 */
public class Packaging {
    /**
     * The material of the packaging (e.g., paper, plastic, metal).
     */
    private final String material;
    /**
     * The volume capacity of the packaging in milliliters.
     */
    private final double volume;

    /**
     * Constructs a new Packaging instance.
     *
     * @param material the packaging material type
     * @param volume   the volume capacity of the package
     */
    public Packaging(String material, double volume) {
        this.material = material;
        this.volume = volume;
    }

    /**
     * Returns the packaging material.
     *
     * @return the material type
     */
    public String getMaterial() {
        return material;
    }


    /**
     * Returns the volume capacity of the package.
     *
     * @return the volume in milliliters
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Returns a formatted string containing the packaging information.
     *
     * @return the string representation of this Packaging
     */
    public String getInfo() {
        return "Packaging{" +
                "material='" + getMaterial() + '\'' +
                ", volume=" + getVolume() +
                '}';
    }


}
