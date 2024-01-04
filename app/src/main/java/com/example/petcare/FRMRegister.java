package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.regex.Pattern;

public class FRMRegister extends AppCompatActivity {

    TextInputEditText txtName, txtUserName,txtemail,txtPhoneNumber,txtpassword;
    Button btnReg;

    TextView LtxtLogin;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmregister);

        txtName = findViewById(R.id.txtName);
        txtUserName = findViewById(R.id.txtUserName);
        txtemail = findViewById(R.id.txtemail);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtpassword = findViewById(R.id.txtpassword);
        btnReg = findViewById(R.id.btnReg);
        LtxtLogin = findViewById(R.id.textbuttonLogin);


        LtxtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FRMRegister.this, LoginFRM.class);
                startActivity(intent);
            }
        });



        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input fields
                if (validateInputs()) {
                    // Proceed with user registration
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference();

                    String name = txtName.getText().toString();
                    String email = txtemail.getText().toString();
                    String username = txtUserName.getText().toString();
                    String password = txtpassword.getText().toString();
                    String PhoneNumber = txtPhoneNumber.getText().toString();

                    Log.d("LoginActivity", "Checking username: " + username);
                    Log.d("LoginActivity", "Checking password: " + password);

                    String randomUserId = UUID.randomUUID().toString();

                    HelperClass helperClass = new HelperClass(name,email,PhoneNumber,username,password);
                    reference.child("users").child("user_registration").child(randomUserId).setValue(helperClass);

                    Toast.makeText(FRMRegister.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FRMRegister.this, LoginFRM.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInputs() {
        if (TextUtils.isEmpty(txtName.getText().toString())) {
            txtName.setError("Please enter your name");
            return false;
        } else if (!isAlpha(txtName.getText().toString())) {
            txtName.setError("Name should contain only alphabetic characters");
            return false;
        }

        if (TextUtils.isEmpty(txtemail.getText().toString())) {
            txtemail.setError("Please enter your email");
            return false;
        } else if (!isValidEmail(txtemail.getText().toString())) {
            txtemail.setError("Please enter a valid email address");
            return false;
        }

        if (TextUtils.isEmpty(txtUserName.getText().toString())) {
            txtUserName.setError("Please enter your username");
            return false;
        }

        if (TextUtils.isEmpty(txtpassword.getText().toString())) {
            txtpassword.setError("Please enter your password");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        // Add your email validation logic here, for example using a regular expression
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isAlpha(String name) {
        // Check if the string contains only alphabetic characters
        return name.matches("[a-zA-Z]+");
    }
}