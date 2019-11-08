package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {

    Button btnUpdatePassword;
    EditText edtPassword, edtOldPassword;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        getSupportActionBar().hide();

        btnUpdatePassword = findViewById(R.id.btn_update_password);
        edtPassword = findViewById(R.id.edt_new_password);
        auth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnUpdatePassword.setText("Loading...");
                String newPassword = edtPassword.getText().toString();

                if (TextUtils.isEmpty(newPassword)){
                    edtOldPassword.setError("Kolom ini harus diisi");
                }

                if (!TextUtils.isEmpty(newPassword)){

                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                btnUpdatePassword.setText("Update Password");
                                Intent intent = new Intent(UpdatePasswordActivity.this, ProfilActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Update Password Sukses", Toast.LENGTH_LONG).show();
                            } else {
                                btnUpdatePassword.setText("Update Password");
                                Toast.makeText(getApplicationContext(), "Update Password Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("error", e.getMessage());
                        }
                    });
                }

            }
        });


    }

    public void backToProfil(View view) {
        Intent intent = new Intent(UpdatePasswordActivity.this, ProfilActivity.class);
        startActivity(intent);
    }
}
