package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPetActivity extends AppCompatActivity {

    private TextInputEditText petNameEditText, categoryEditText, petAgeEditText, ownerNameEditText;
    private String itemId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);

        // Initialize your EditText fields
        petNameEditText = findViewById(R.id.EtxtpetNme);
        categoryEditText = findViewById(R.id.EpetCatgory);
        petAgeEditText = findViewById(R.id.Etxtage);
        ownerNameEditText = findViewById(R.id.EtxtOwnerNme);

        // Get itemId from the intent
        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");

        // Fetch pet details and populate EditText fields
        getPetDetails();

        // Implement your save/update logic here when the user clicks the save button.
        // You may want to add a button in your layout for saving the changes.
        // For example:
        Button btnSaveChanges = findViewById(R.id.btn_edit);
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });
    }

    private void getPetDetails() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pets").child(itemId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Pet pet = dataSnapshot.getValue(Pet.class);

                    if (pet != null) {
                        // Populate the EditText fields with the retrieved data
                        petNameEditText.setText(pet.getPetName());
                        categoryEditText.setText(pet.getCategory());
                        petAgeEditText.setText(String.valueOf(pet.getPetAge()));
                        ownerNameEditText.setText(pet.getOwnerName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(EditPetActivity.this, "Failed to fetch pet details.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveChanges() {
        // Retrieve the edited data from EditText fields
        String editedPetName = petNameEditText.getText().toString();
        String editedCategory = categoryEditText.getText().toString();
        String editedPetAgeStr = petAgeEditText.getText().toString();
        String editedOwnerName = ownerNameEditText.getText().toString();

        // Perform validation and save the changes to Firebase
        // ...

        // Example: Update the pet details in Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pets").child(itemId);
        databaseReference.child("petName").setValue(editedPetName);
        databaseReference.child("category").setValue(editedCategory);
        databaseReference.child("petAge").setValue(Integer.parseInt(editedPetAgeStr));
        databaseReference.child("ownerName").setValue(editedOwnerName);

        // You may want to add a success message or navigate back to the previous activity
        Toast.makeText(EditPetActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(EditPetActivity.this, PetDetails.class);
        startActivity(intent);
    }
}
