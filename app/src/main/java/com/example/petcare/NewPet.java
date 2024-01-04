package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewPet extends AppCompatActivity {


    private TextInputEditText petNameEditText,categoryEditText,petAgeEditText,ownerNameEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);

        petNameEditText = findViewById(R.id.txtpetNme);
        categoryEditText = findViewById(R.id.petCatgory);
        petAgeEditText = findViewById(R.id.txtage);
        ownerNameEditText = findViewById(R.id.txtOwnerNme);
        saveButton = findViewById(R.id.btn_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePetData();
            }
        });
    }
    private void savePetData() {
        // Get data from EditText fields
        String petName = petNameEditText.getText().toString();
        String category = categoryEditText.getText().toString();
        String petAgeStr = petAgeEditText.getText().toString();
        String ownerName = ownerNameEditText.getText().toString();
        String userId = "001";

        // Check if any field is empty
        if (petName.isEmpty() || category.isEmpty() || petAgeStr.isEmpty() || ownerName.isEmpty() || userId.isEmpty()) {
            // Handle the error, for example, show a Toast
            return;
        }

        // Convert petAge from String to int
        int petAge;
        try {
            petAge = Integer.parseInt(petAgeStr);
        } catch (NumberFormatException e) {
            // Handle the case where petAge is not a valid integer
            e.printStackTrace(); // Print the stack trace for debugging
            // You might want to show a Toast or log an error message here
            return;
        }

        // Create a reference to the "pets" node in Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pets");

        // Create a new Pet object with the entered data
        Pet pet = new Pet(petName, category, petAge, ownerName, userId);

        // Use the reference to push the data to the database
        databaseReference.push().setValue(pet);

        // Optionally, you can also add an OnSuccessListener and OnFailureListener to handle the result
    }

}