package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText edtEmail;
    Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        edtEmail = findViewById(R.id.edt_email_reset);
        reset = findViewById(R.id.btn_reset_password);

    }

    public void resetPassword(View view) {
        reset.setText("Loading...");
        String email = edtEmail.getText().toString().trim();
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "reset password sukses silahkan cek email anda", Toast.LENGTH_SHORT).show();
                            reset.setText("SEND CODE");
                            edtEmail.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "reset password gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
