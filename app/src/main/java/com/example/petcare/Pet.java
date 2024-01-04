package com.example.petcare;

import java.io.Serializable;

public class Pet implements Serializable {
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


    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

