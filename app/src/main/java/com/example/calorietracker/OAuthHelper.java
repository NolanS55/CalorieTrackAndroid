package com.example.calorietracker;
import android.util.Base64;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OAuthHelper {
    private static final String CLIENT_ID = "d4218564c82c4d96bb9a239d7509e2b9";
    private static final String CLIENT_SECRET = "83aaf68c0a9242b7aac6b019846cd9a6";
    private static final String TOKEN_URL = "https://oauth.fatsecret.com/connect/token";
    private static String accessToken = null;

    public static void getAccessToken(final logFood activity) {
        String url = TOKEN_URL + "?grant_type=client_credentials&scope=basic";

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            accessToken = jsonResponse.getString("access_token");
                            activity.onAccessTokenReceived(accessToken);  // Notify activity with access token
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> {
            error.printStackTrace();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
                String basicAuth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", basicAuth);
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }

    public static String getAccessToken() {
        return accessToken;
    }
}
