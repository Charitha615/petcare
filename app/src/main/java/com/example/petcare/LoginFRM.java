package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFRM extends AppCompatActivity {

    Button LbtnLogin;
    TextInputEditText LtxtEmail, LtxtPassword;
    TextView LtxtRegister;

    public static final String ERROR_EMPTY_USERNAME = "Username cannot be empty";
    public static final String ERROR_EMPTY_PASSWORD = "Password cannot be empty";
    public static final String ERROR_INVALID_CREDENTIALS = "Invalid username or password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_frm);

        LbtnLogin = findViewById(R.id.LbtnLogin);
        LtxtEmail = findViewById(R.id.LtxtEmail);
        LtxtPassword = findViewById(R.id.LtxtPassword);
        LtxtRegister = findViewById(R.id.txtbuttonCreateAccount);

        LtxtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFRM.this, FRMRegister.class);
                startActivity(intent);
            }
        });

        LbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameError = validateUsername();
                String passwordError = validatePassword();

                if (usernameError != null) {
                    LtxtEmail.setError(usernameError);
                }

                if (passwordError != null) {
                    LtxtPassword.setError(passwordError);
                }

                if (usernameError == null && passwordError == null) {
                    // Validation successful, proceed with login
                    checkUser();
                }
            }
        });
    }
    public String validateUsername() {
        String val = LtxtEmail.getText().toString();
        if (val.isEmpty()) {
            return ERROR_EMPTY_USERNAME;
        } else {
            return null; // No error
        }
    }

    public String validatePassword() {
        String val = LtxtPassword.getText().toString();
        if (val.isEmpty()) {
            return ERROR_EMPTY_PASSWORD;
        } else {
            return null; // No error
        }
    }

    public void checkUser() {
        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");

        String userUsername = LtxtEmail.getText().toString().trim();
        String userPassword = LtxtPassword.getText().toString().trim();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean loginSuccessful = false;

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    HelperClass user = userSnapshot.getValue(HelperClass.class);

                    if (user != null && user.getEmail().equals(userUsername) && user.getPassword().equals(userPassword)) {
                        // Login successful
                        loginSuccessful = true;

                        String name = user.getName();
                        String email = user.getEmail();
                        String username = user.getUsername();
                        String randomUserId = userSnapshot.getKey();

                        Log.d("YourTag", "Random User ID: " + randomUserId);



                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("randomUserId", randomUserId);
                        editor.apply();

                        // Pass this data to the MainActivity using Intent
                        Intent intent = new Intent(LoginFRM.this, NewPet.class);
                        intent.putExtra("name", name);
                        intent.putExtra("email", email);
                        intent.putExtra("username", username);
                        intent.putExtra("randomUserId", randomUserId);
                        startActivity(intent);
                        finish(); // Finish the LoginActivity to prevent going back to it with the back button
                        break; // Exit the loop since we found a match
                    }
                }

                if (!loginSuccessful) {
                    // Display an error message for invalid credentials
                    LtxtPassword.setError(ERROR_INVALID_CREDENTIALS);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });
    }
}