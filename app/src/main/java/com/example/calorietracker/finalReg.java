package com.example.calorietracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.j256.ormlite.stmt.query.In;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class finalReg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_final_reg);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent current = getIntent();

        EditText tarWeightBox = findViewById(R.id.tarWeightBox);

        EditText timeBox = findViewById(R.id.timeTxt);

        int weight = current.getIntExtra("weight", 0);
        int height = current.getIntExtra("height", 0);
        int age = current.getIntExtra("age", 0);
        int gender = current.getIntExtra("gender", 0);

        Button regBtn = findViewById(R.id.finRegBtn);
        regBtn.setOnClickListener(v -> {
            String tarWeightStr = tarWeightBox.getText().toString().trim();
            String timeStr = timeBox.getText().toString().trim();

            // Validate input
            if (tarWeightStr.isEmpty() || timeStr.isEmpty()) {
                Toast.makeText(finalReg.this, "Please enter valid target weight and time.", Toast.LENGTH_SHORT).show();
                return;
            }

            int tarWeight;
            int time;

            try {
                tarWeight = Integer.parseInt(tarWeightStr);
                time = Integer.parseInt(timeStr);
            } catch (NumberFormatException e) {
                Toast.makeText(finalReg.this, "Invalid number format.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (time <= 0) {
                Toast.makeText(finalReg.this, "Time must be greater than zero.", Toast.LENGTH_SHORT).show();
                return;
            }

            double lost = weight - tarWeight;
            double days = time * 7;
            double totalCal = Math.round(3500 * lost);

            double calBurned = Math.round(weight * 0.48 * 24);
            double calDef = totalCal / days;
            double dayCal = calBurned - calDef;
            dayCal = Math.round(dayCal);

            User.Personal personal = new User.Personal(weight, height, age, "1999-01-01", gender, "New York", tarWeight, dayCal);
            User newUser = new User(current.getStringExtra("email"), current.getStringExtra("password"), current.getStringExtra("name"), personal);

            ApiService apiService = RetrofitClient.getApiService();
            Call<ApiResponse> call = apiService.registerUser(newUser);

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(finalReg.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(finalReg.this, Home.class);
                        intent.putExtra("curUser", newUser);
                        startActivity(intent);
                    } else {
                        Toast.makeText(finalReg.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(finalReg.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", t.getMessage());
                }
            });
        });






    }

}