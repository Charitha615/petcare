package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CardView Newpetcard, PetDetailscard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Newpetcard = findViewById(R.id.newpetcard);
        PetDetailscard = findViewById(R.id.petDetailscard);

        Intent intent = getIntent();
        String randomUserId = intent.getStringExtra("randomUserId");


        Newpetcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewPet.class);
                intent.putExtra("randomUserId", randomUserId);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"Add New Pets", Toast.LENGTH_SHORT).show();
            }
        });

        PetDetailscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PetDetails.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"Find Your Pets Details", Toast.LENGTH_SHORT).show();

            }
        });
    }
}