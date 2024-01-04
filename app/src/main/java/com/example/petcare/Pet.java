package com.example.petcare;

public class Pet {
    public String petName;
    public String category;
    public int petAge;
    public String ownerName;
    public String userId;

    // Add an empty constructor for Firebase
    public Pet() {}

    public Pet(String petName, String category, int petAge, String ownerName, String userId) {
        this.petName = petName;
        this.category = category;
        this.petAge = petAge;
        this.ownerName = ownerName;
        this.userId = userId;
    }
}

