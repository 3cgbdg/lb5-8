package qualityparams;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents coffee quality parameters including aroma, taste, and freshness scores.
 */
public class QualityParams {
    /**
     * Numeric score representing aroma quality (0–10).
     */
    private final double aromaScore;
    /**
     * Numeric score representing taste quality (0–10).
     */
    private final double tasteScore;
    /**
     * Numeric score representing freshness level (0–10).
     */
    private final double freshnessScore;
    private static final Logger LOGGER = LogManager.getLogger(QualityParams.class);
    /**
     * Constructs a new QualityParams object.
     *
     * @param aromaScore     the aroma score
     * @param tasteScore     the taste score
     * @param freshnessScore the freshness score
     */
    public QualityParams(double aromaScore, double tasteScore, double freshnessScore) {
        if(aromaScore >10 || tasteScore>10 || freshnessScore>10){
            String errorMsg = String.format(
                    "Invalid quality parameters: Aroma=%.1f, Taste=%.1f, Freshness=%.1f. Values cannot be > 10.",
                    aromaScore, tasteScore, freshnessScore
            );
            LOGGER.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        this.aromaScore = aromaScore;
        this.tasteScore = tasteScore;
        this.freshnessScore = freshnessScore;
    }

    /**
     * @return the aroma score
     */
    public double getAromaScore() {
        return aromaScore;
    }

    /**
     * @return the taste score
     */
    public double getTasteScore() {
        return tasteScore;
    }

    /**
     * @return the freshness score
     */
    public double getFreshnessScore() {
        return freshnessScore;
    }

    /**
     * Checks whether all quality parameters fall within specified ranges.
     *
     * @param minAroma     minimum aroma score
     * @param maxAroma     maximum aroma score
     * @param minTaste     minimum taste score
     * @param maxTaste     maximum taste score
     * @param minFreshness minimum freshness score
     * @param maxFreshness maximum freshness score
     * @return {@code true} if all parameters are within the given ranges, otherwise {@code false}
     */
    public boolean isInRange(double minAroma, double maxAroma,
                             double minTaste, double maxTaste,
                             double minFreshness, double maxFreshness) {
        return getAromaScore() >= minAroma &&
                getAromaScore() <= maxAroma &&
                getTasteScore() >= minTaste &&
                getTasteScore() <= maxTaste &&
                getFreshnessScore() >= minFreshness &&
                getFreshnessScore() <= maxFreshness;
    }

}
