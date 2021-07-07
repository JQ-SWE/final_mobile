package com.example.afinal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BusItemReader {
    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public List<MyItem> read(InputStream inputStream) throws JSONException {
        List<MyItem> items = new ArrayList<MyItem>();
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            String title = null;
            String snippet = null;
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("LATITUDE" );
            double lng = object.getDouble("LONGITUDE");
            if (!object.isNull("LOCATION")) {
                title = object.getString("LOCATION");
            }
            if (!object.isNull("PhysicalID")) {
                snippet = object.getString("PhysicalID");
            }
            items.add(new MyItem(lat, lng, title, snippet));
        }
        return items;
    }
}
