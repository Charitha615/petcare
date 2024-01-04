package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PetDetails extends AppCompatActivity {

    private MyAdapter adapter;
    private List<Pet> petList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        petList = new ArrayList<>();
        adapter = new MyAdapter(getApplicationContext(), petList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Set item click listener
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String itemId) {
                // Handle item click, navigate to another page with the item ID
                Intent intent = new Intent(PetDetails.this, EditPetActivity.class);
                intent.putExtra("itemId", itemId);
                startActivity(intent);
            }
        });

        loadPetData();
    }
    private void loadPetData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pets");

        // Attach a ValueEventListener to read the data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                petList.clear(); // Clear existing data

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Convert each data snapshot to a Pet object
                    Pet pet = snapshot.getValue(Pet.class);

                    if (pet != null) {
                        // Add the pet to the list
                        petList.add(pet);
                    }
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(PetDetails.this, "Failed to load pet data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}