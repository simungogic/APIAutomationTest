package TaskTwo.Model;

public class GenerationMix {
    private String fuel;
    private double perc;

    public GenerationMix() {

    }
    public GenerationMix(String fuel, double perc) {
        this.fuel = fuel;
        this.perc = perc;
    }

    public String getFuel() {
        return fuel;
    }

    public double getPerc() {
        return perc;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setPerc(double perc) {
        this.perc = perc;
    }
}
