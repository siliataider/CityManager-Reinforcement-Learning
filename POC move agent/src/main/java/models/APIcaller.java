package models;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.lang.module.FindException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * SEE : https://openrouteservice.org/dev/#/api-docs/v2/directions/{profile}/get
 */
public class APIcaller {

    public static final BigDecimal deltaPoints = BigDecimal.valueOf(0.000025F);
    private  static final String domainName = "https://api.openrouteservice.org/v2/directions/foot-walking";
    private static final String tocken = "5b3ce3597851110001cf624898d73d0c7634461abc58ae5564b8c48d";

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

        JSONArray a = (JSONArray) json.get("features");
        JSONObject b = (JSONObject) a.get(0);
        JSONObject c = (JSONObject) b.get("geometry");
        JSONArray d = (JSONArray) c.get("coordinates");

        List<List<BigDecimal>> cordsList = (List<List<BigDecimal>>)(List<?>)d.toList();
        return cordsList;
    }



    public static void main(String[] args) {

        getPathAPI(8.681495, 49.41461, 8.687872, 49.420318);

        /**
            String URL = "?api_key=5b3ce3597851110001cf624898d73d0c7634461abc58ae5564b8c48d&start=8.681495,49.41461&end=8.687872,49.420318";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(domainName+URL))
                    .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = null;
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(response.body());**/
        }
    }

