package com.example.petcare;

import static androidx.core.content.ContentProviderCompat.requireContext;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

                Intent intent = new Intent(NewPet.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void savePetData() {
        // Get data from EditText fields
        String petName = petNameEditText.getText().toString();
        String category = categoryEditText.getText().toString();
        String petAgeStr = petAgeEditText.getText().toString();
        String ownerName = ownerNameEditText.getText().toString();

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("randomUserId", "");

        Log.d("randomUserIduserId",userId);
        Log.d("petName",petName);


        if (petName.isEmpty() || category.isEmpty() || petAgeStr.isEmpty() || ownerName.isEmpty() || userId.isEmpty()) {
            return;
        }

        int petAge;
        try {
            petAge = Integer.parseInt(petAgeStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pets");

        Pet pet = new Pet(petName, category, petAge, ownerName, userId);

        databaseReference.push().setValue(pet);

    }

}