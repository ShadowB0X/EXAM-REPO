package app.services;

import app.entities.SkiInstruction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SkiInstructionService {

    private static final String BASE_URL = "https://apiprovider.cphbusinessapps.dk/skilesson/";
    private static final Gson gson = new Gson();

    public static List<SkiInstruction> getInstructionsByLevel(String level) throws Exception {
        String endpoint = BASE_URL + level.toLowerCase();

        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed to fetch instructions. HTTP error code: " + conn.getResponseCode());
        }

        InputStreamReader reader = new InputStreamReader(conn.getInputStream());
        Type listType = new TypeToken<List<SkiInstruction>>() {}.getType();
        List<SkiInstruction> instructions = gson.fromJson(reader, listType);

        conn.disconnect();
        return instructions;
    }
}