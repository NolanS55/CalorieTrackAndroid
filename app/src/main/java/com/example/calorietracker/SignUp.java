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

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button getStartBtn = findViewById(R.id.nextBtn1);
        getStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nameBox = findViewById(R.id.displayNameTxt);
                String name = nameBox.getText().toString();

                EditText emailBox = findViewById(R.id.emailTxt);
                String email = emailBox.getText().toString();

                EditText passwordBox = findViewById(R.id.passwordTxt);
                String password = passwordBox.getText().toString();

                String[] loginDetails = {name, email, password};

                Intent intent = new Intent(SignUp.this, Personal.class);
                intent.putExtra("LoginDetails", loginDetails);
                startActivity(intent);
            }
        });
    }
}