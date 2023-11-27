package com.myfirstapp.mentdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText email,username,password,confirmPassword,age;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth= FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        username = findViewById(R.id.uname);
        confirmPassword = findViewById(R.id.conpassword);
        age = findViewById(R.id.age);
        Button btnReg = findViewById(R.id.btnReg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dEmail = String.valueOf(email.getText());
                String dUsername = String.valueOf(username.getText());
                String dPassword =  String.valueOf(password.getText());
                String dConfirmPassword =  String.valueOf(confirmPassword.getText());
                String dAge =  String.valueOf(age.getText());

                if (TextUtils.isEmpty(dEmail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(dPassword)) {
                    Toast.makeText(getApplicationContext(), "Enter password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dPassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!dPassword.equals(dConfirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Password and Confirm Password Doesn't match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(dEmail, dPassword)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();


                                } else {
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

    }

    public void GoToLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}