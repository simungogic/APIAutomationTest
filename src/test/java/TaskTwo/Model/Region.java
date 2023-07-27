package TaskTwo.Model;

import java.util.List;

public class Region implements Comparable<Region> {
    private String shortName;
    private String carbonIntensityIndex;
    private int carbonIntensityForecast;
    private List<GenerationMix> generationMix;

    public Region(String shortName, String carbonIntensityIndex, int carbonIntensityForecast, List<GenerationMix> generationMix) {
        this.shortName = shortName;
        this.carbonIntensityIndex = carbonIntensityIndex;
        this.carbonIntensityForecast = carbonIntensityForecast;
        this.generationMix = generationMix;
    }

    public String getShortName() {
        return shortName;
    }

    public String getCarbonIntensityIndex() {
        return carbonIntensityIndex;
    }

    public int getCarbonIntensityForecast() {
        return carbonIntensityForecast;
    }

    public List<GenerationMix> getGenerationMix() {
        return generationMix;
    }

    @Override
    public int compareTo(Region other) {

        if(this.carbonIntensityForecast < other.carbonIntensityForecast) return -1;
        else if(this.carbonIntensityForecast == other.carbonIntensityForecast) return 0;

        return 1;
    }

    @Override
    public String toString() {
        return "Region{" +
                "shortName='" + shortName + '\'' +
                ", carbonIntensityIndex='" + carbonIntensityIndex + '\'' +
                ", carbonIntensityForecast=" + carbonIntensityForecast +
                '}';
    }
}
