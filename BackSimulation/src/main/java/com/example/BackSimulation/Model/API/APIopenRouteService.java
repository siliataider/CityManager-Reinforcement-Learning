package com.example.BackSimulation.Model.API;

import com.example.BackSimulation.Model.MapObjects.Building;
import com.example.BackSimulation.Model.MouvableObject.CoordBigDecimal;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.aop.scope.ScopedObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIopenRouteService {
    public static final BigDecimal deltaPoints = BigDecimal.valueOf(0.000025F);
    private  static final String domainName = "https://api.openrouteservice.org/v2/directions/foot-walking";
    //private static final String tocken = "5b3ce3597851110001cf624898c7eb581706416a9ab7df828faeaae3";
    private static final String tocken = "5b3ce3597851110001cf624898d73d0c7634461abc58ae5564b8c48d";

    private static Map<List<CoordBigDecimal>, List<List<BigDecimal> >> cachedPath = new HashMap<>();

    public static List<List<BigDecimal>> getPathAPI(Building startBuilding, Building endbuilding){
        List<CoordBigDecimal> testKeyCach = new ArrayList<>();
        testKeyCach.add(startBuilding.getCoords());
        testKeyCach.add(endbuilding.getCoords());

        System.out.println(startBuilding);
        System.out.println(endbuilding);
        System.out.println(cachedPath);

        if( cachedPath.containsKey(testKeyCach)){
            System.out.println("Cached call");
            return cachedPath.get(testKeyCach);
        }
        System.out.println("Not cached");


        cachedPath.put(testKeyCach, getPathAPI(startBuilding.coords.lng.floatValue(), startBuilding.coords.lat.floatValue()
                , endbuilding.coords.lng.floatValue(), endbuilding.coords.lat.floatValue()));

        return cachedPath.get(testKeyCach);
    }

    public static List<List<BigDecimal>> getPathAPI(float startLong, float startLat, float endLong, float endLat){

        String tailURL = "&start="+ startLong + "," + startLat + "&end=" + endLong + "," + endLat;


        String URL = domainName
                + "?api_key=" + tocken
                + tailURL;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create( URL ))
                .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        List<List<BigDecimal>> cordsList;
        JSONObject json = new JSONObject(response.body());

        if (json.has("features")) {
            JSONArray a = (JSONArray) json.get("features");
            JSONObject b = (JSONObject) a.get(0);
            JSONObject c = (JSONObject) b.get("geometry");
            JSONArray d = (JSONArray) c.get("coordinates");

            cordsList = (List<List<BigDecimal>>) (List<?>) d.toList();

        }else{
            // This part cheats a little !
            // if the API is overloaded will give some dummy coords to the agents
            System.out.println("No Feature ! ");

            List<BigDecimal> coords = new ArrayList<>();
            coords.add(BigDecimal.valueOf(startLong));
            coords.add( BigDecimal.valueOf(startLat));

            cordsList = new ArrayList<>();
            cordsList.add( coords );
        }

        return cordsList;
    }

}
