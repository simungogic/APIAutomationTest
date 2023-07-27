package Utility;

import TaskTwo.Model.GenerationMix;
import TaskTwo.Model.Region;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.*;

public class CarbonIntensityHelper {

    public static ArrayList<Region> getRegions() {
        ArrayList<Region> regions = new ArrayList<>();
        Response response = HttpHelper.sendGetReq("https://api.carbonintensity.org.uk/regional");
        JsonPath data = response.jsonPath().setRootPath("data[0]");
        int numOfRegions = data.getInt("regions.size()");

        for(int i=0;i<numOfRegions;i++) {
            String shortName = data.getString("regions["+i+"].shortname");
            String carbonIntensityIndex = data.getString("regions["+i+"].intensity.index");
            int carbonIntensityForecast = data.getInt("regions["+i+"].intensity.forecast");
            List<GenerationMix> generationMix = data.getList("regions["+i+"].generationmix", GenerationMix.class);
            Region region = new Region(shortName, carbonIntensityIndex, carbonIntensityForecast, generationMix);
            regions.add(region);
        }

        return regions;
    }

    public static LinkedHashMap<String, Double> sortRegionsByFuelType(List<Region> regions, String fuelType) {
        int counter = 0;
        HashMap<String, Double> regionGenPerc = new HashMap<>();
        ArrayList<Double> genPercs = new ArrayList<>();
        LinkedHashMap<String, Double> sortedRegionsByGenPerc = new LinkedHashMap<>();
        for (Region region : regions) {
            for (GenerationMix gm : region.getGenerationMix()) {
                if(gm.getFuel().equalsIgnoreCase(fuelType)) {
                    regionGenPerc.put(region.getShortName(), gm.getPerc());
                    genPercs.add(gm.getPerc());
                }
            }
        }

        Collections.sort(genPercs, new Comparator<Double>() {
            public int compare(Double genPerc1, Double genPerc2) {
                if(genPerc1 > genPerc2) return -1;
                if(genPerc1 < genPerc2) return 1;

                return 0;
            }
        });

        for (Double genPerc : genPercs) {
            for (Map.Entry<String, Double> entry : regionGenPerc.entrySet()) {
                if (entry.getValue().equals(genPerc) && counter < 5) {
                    counter++;
                    sortedRegionsByGenPerc.put(entry.getKey(), genPerc);
                }
            }
        }

        return sortedRegionsByGenPerc;
    }

    public static void printRegions(ArrayList<Region> regions) {
        for(Region r : regions) {
            System.out.printf("""
                    %4d | %10s | %s
                    """,
                    r.getCarbonIntensityForecast(),
                    r.getCarbonIntensityIndex(),
                    r.getShortName()
            );
        }
        System.out.println("-".repeat(70));
    }

    public static void printRegions(LinkedHashMap<String, Double> regions) {
        for (Map.Entry<String, Double> entry : regions.entrySet()) {
            System.out.printf("""
            %25s | %5.1f
            """,
                    entry.getKey(), entry.getValue());
        }
        System.out.println("-".repeat(70));
    }
}
