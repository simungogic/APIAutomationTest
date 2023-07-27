package TaskTwo;

import Utility.CarbonIntensityHelper;
import TaskTwo.Model.GenerationMix;
import TaskTwo.Model.Region;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class TaskTwo {
    private Response response;
    private Region region;
    private ArrayList<Region> regions;

    public TaskTwo() {
        this.regions = new ArrayList<>();
    }

    @Test
    public void firstScenario() {
        regions = CarbonIntensityHelper.getRegions();
        regions.sort(Comparator.reverseOrder());

        System.out.println("Carbon intensity forecast sorted: ");
        CarbonIntensityHelper.printRegions(regions);
    }

    @Test
    public void secondScenario() {
        double sumPerc;
        regions = CarbonIntensityHelper.getRegions();
        for (Region region : regions) {
            sumPerc = 0.0;
            for (GenerationMix gm : region.getGenerationMix()) {
                sumPerc += gm.getPerc();
            }
            System.out.println("Region " + region.getShortName() + " has generation mix sum " + sumPerc);
            Assert.assertEquals(100.0, sumPerc, 0.0);
        }
    }

    @Test
    public void thirdScenario() {
        regions = CarbonIntensityHelper.getRegions();

        LinkedHashMap<String, Double> sortedRegionsByBiomass = CarbonIntensityHelper.sortRegionsByFuelType(regions, "biomass");
        LinkedHashMap<String, Double> sortedRegionsByCoal = CarbonIntensityHelper.sortRegionsByFuelType(regions, "coal");
        LinkedHashMap<String, Double> sortedRegionsByImports = CarbonIntensityHelper.sortRegionsByFuelType(regions, "imports");
        LinkedHashMap<String, Double> sortedRegionsByGas = CarbonIntensityHelper.sortRegionsByFuelType(regions, "gas");
        LinkedHashMap<String, Double> sortedRegionsByNuclear = CarbonIntensityHelper.sortRegionsByFuelType(regions, "nuclear");
        LinkedHashMap<String, Double> sortedRegionsByOther = CarbonIntensityHelper.sortRegionsByFuelType(regions, "other");
        LinkedHashMap<String, Double> sortedRegionsByHydro = CarbonIntensityHelper.sortRegionsByFuelType(regions, "hydro");
        LinkedHashMap<String, Double> sortedRegionsBySolar = CarbonIntensityHelper.sortRegionsByFuelType(regions, "solar");
        LinkedHashMap<String, Double> sortedRegionsByWind = CarbonIntensityHelper.sortRegionsByFuelType(regions, "wind");

        System.out.println("Top 5 regions with highest generation percentage for biomass");
        CarbonIntensityHelper.printRegions(sortedRegionsByBiomass);

        System.out.println("Top 5 regions with highest generation percentage for coal");
        CarbonIntensityHelper.printRegions(sortedRegionsByCoal);

        System.out.println("Top 5 regions with highest generation percentage for imports");
        CarbonIntensityHelper.printRegions(sortedRegionsByImports);

        System.out.println("Top 5 regions with highest generation percentage for gas");
        CarbonIntensityHelper.printRegions(sortedRegionsByGas);

        System.out.println("Top 5 regions with highest generation percentage for nuclear");
        CarbonIntensityHelper.printRegions(sortedRegionsByNuclear);

        System.out.println("Top 5 regions with highest generation percentage for other");
        CarbonIntensityHelper.printRegions(sortedRegionsByOther);

        System.out.println("Top 5 regions with highest generation percentage for hydro");
        CarbonIntensityHelper.printRegions(sortedRegionsByHydro);

        System.out.println("Top 5 regions with highest generation percentage for solar");
        CarbonIntensityHelper.printRegions(sortedRegionsBySolar);

        System.out.println("Top 5 regions with highest generation percentage for wind");
        CarbonIntensityHelper.printRegions(sortedRegionsByWind);
    }
}
