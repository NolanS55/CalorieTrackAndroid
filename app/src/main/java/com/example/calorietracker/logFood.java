package com.example.calorietracker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class logFood extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_food);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        // Get OAuth access token
        OAuthHelper.getAccessToken(this);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String query = charSequence.toString();
                if (query.length() > 2 && accessToken != null) {
                    // Call API to search for food
                    FoodSearchHelper.searchFood(query, accessToken, logFood.this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    // Method called when access token is received
    public void onAccessTokenReceived(String token) {
        this.accessToken = token;
    }

    // Method to update AutoCompleteTextView with the suggestions
    public void updateAutocomplete(List<String> foodSuggestions) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, foodSuggestions);
        autoCompleteTextView.setAdapter(adapter);
    }
}