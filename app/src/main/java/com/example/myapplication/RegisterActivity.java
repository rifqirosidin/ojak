package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtNoHp, edtPassword;
    Button btnRegister;
    String username, email,noHP, password;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("users");

        register();



    }

    public void initialization(){
        edtUsername = findViewById(R.id.edt_username);
        edtEmail = findViewById(R.id.edt_email);
        edtNoHp = findViewById(R.id.edt_nohp);
        edtPassword = findViewById(R.id.edt_password);
        btnRegister = findViewById(R.id.btn_register);
    }

    public void register(){

        initialization();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edtUsername.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                noHP = edtNoHp.getText().toString().trim();
                password = edtPassword.getText().toString().trim();

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

               validasi();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)){
                    btnRegister.setText("Loading...");
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    btnRegister.setText("Register");

                                    if (!task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Email Already Exist", Toast.LENGTH_SHORT).show();

                                    } else {
                                        biodata(username, noHP);
                                        Toast.makeText(getApplicationContext(), "Register Sukses", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    public void validasi()
    {
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("harus diisi");

        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("harus diisi");

        }

        if (TextUtils.isEmpty(username)) {
            edtUsername.setError("harus diisi");

        }
        if (TextUtils.isEmpty(noHP)) {
            edtNoHp.setError("harus diisi");

        }



    }

    public void biodata(String username, String nohp)
    {
        String uuid = mAuth.getUid();
        User user = new User(username, nohp);

        myRef.child(uuid).setValue(user);

    }

    public void formLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void back(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
