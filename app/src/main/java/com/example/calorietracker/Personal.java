package com.example.calorietracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Personal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button getStartBtn = findViewById(R.id.nextBtn3);
        getStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText weightBox = findViewById(R.id.WeightTxt);
                String weight = weightBox.getText().toString();

                EditText heightBox = findViewById(R.id.heightTxt);
                String height = heightBox.getText().toString();

                EditText ageBox = findViewById(R.id.ageTxt);
                String age = ageBox.getText().toString();

                EditText genderBox = findViewById(R.id.genderTxt);
                String gender = genderBox.getText().toString();

                String[] personalDetails = {weight, height, age, gender};

                Intent current = getIntent();



                Intent intent = new Intent(Personal.this, finalReg.class);
                intent.putExtra("LoginDetails", current.getStringArrayExtra("LoginDetails"));
                intent.putExtra("PersonalDetails", personalDetails);
                startActivity(intent);
            }
        });
    }
}