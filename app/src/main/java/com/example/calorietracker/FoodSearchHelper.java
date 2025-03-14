package com.example.calorietracker;
import android.widget.Toast;

import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.VolleyError;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodSearchHelper {
    // Method to search for food
    public static void searchFood(final String query, final String accessToken, final logFood activity) {
        String url = "https://platform.fatsecret.com/rest/food/autocomplete/v2?method=foods.autocomplete.v2&expression=" + query;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Process the response (parse JSON, etc.)
                        // Assuming the response is a JSON string with food suggestions
                        List<String> suggestions = parseFoodSuggestions(response);
                        activity.updateAutocomplete(suggestions);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error properly
                        Toast.makeText(activity, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            // Overriding getHeaders() to include the authorization header
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };

        // Add the request to the RequestQueue
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    // Helper method to parse the food suggestions from the API response
    private static List<String> parseFoodSuggestions(String response) {
        List<String> suggestions = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray suggestionsArray = jsonResponse.getJSONArray("suggestions");
            for (int i = 0; i < suggestionsArray.length(); i++) {
                suggestions.add(suggestionsArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return suggestions;
    }
}
