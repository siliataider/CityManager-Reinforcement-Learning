package com.example.BackSimulation.Model.API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIopenRouteService {
    public static final BigDecimal deltaPoints = BigDecimal.valueOf(0.000025F);
    private  static final String domainName = "https://api.openrouteservice.org/v2/directions/foot-walking";
    private static final String tocken = "5b3ce3597851110001cf624898d73d0c7634461abc58ae5564b8c48d";

    private static Map<Map< Map<Double, Double>, Map<Double,Double> >, List<List<BigDecimal>> > cachedPath;

    public static List<List<BigDecimal>> getPathAPI(double startLong, double startLat, double endLong, double endLat){

        String URL = domainName
                + "?api_key=" + tocken
                + "&start="+ startLong + "," + startLat
                + "&end=" + endLong + "," + endLat;

        System.out.println(URL);

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

        JSONObject json = new JSONObject(response.body());

        System.out.println(json);

        JSONArray a = (JSONArray) json.get("features");
        JSONObject b = (JSONObject) a.get(0);
        JSONObject c = (JSONObject) b.get("geometry");
        JSONArray d = (JSONArray) c.get("coordinates");

        List<List<BigDecimal>> cordsList = (List<List<BigDecimal>>)(List<?>)d.toList();

        /**
        Map<Map<Double,Double>, Map<Double,Double> > key = new HashMap<>();
        key.put( new HashMap<>(), new HashMap<>());


        this.cachedPath.put();
        */
        return cordsList;
    }

}
