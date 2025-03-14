package com.example.calorietracker;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.io.Serializable;

public class User implements Serializable {
    public String email;
    public String password;
    public String name;
    public Personal personal;

    public User(String email, String password, String name, Personal personal) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.personal = personal;
    }

    public static class Personal {
        public double weight;
        public double height;
        public int age;
        public String birthDate;
        public int gender;
        public String location;
        public double tarWeight;
        public double dayCal;

        public Personal(double weight, double height, int age, String birthDate, int gender, String location, double tarWeight, double dayCal) {
            this.weight = weight;
            this.height = height;
            this.age = age;
            this.birthDate = birthDate;
            this.gender = gender;
            this.location = location;
            this.tarWeight = tarWeight;
            this.dayCal = dayCal;
        }
    }
}
