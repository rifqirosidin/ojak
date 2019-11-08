package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfilActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    TextView tvDetailProfil, tvEmail, tvUsername;
    FirebaseUser user;
    ArrayList<User> users;
    View dialogView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        tvDetailProfil = findViewById(R.id.tv_detail_profil);
        tvEmail = findViewById(R.id.tv_email_profil);
        tvUsername = findViewById(R.id.tv_username_profil);
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();

        displayProfil();

    }

    public void logout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah Anda Yakin");
        builder.setMessage("Keluar dari akun ini");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.signOut();
                Intent intent = new Intent(ProfilActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog builder1 = builder.create();
        builder1.show();

    }

    public void displayProfil()
    {
        tvEmail.setText(user.getEmail());
        tvDetailProfil.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua");
        mRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid());

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                tvDetailProfil.setText(user.getAbout());
                tvUsername.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", databaseError.getMessage());
            }
        });
    }


    public void backPage(View view) {
        Intent intent = new Intent(ProfilActivity.this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToUPdatePassword(View view) {
        Intent intent = new Intent(ProfilActivity.this, UpdatePasswordActivity.class);
        startActivity(intent);
    }

    public void editAbout(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.update_about_profil, null);
        dialog.setView(dialogView);

        final EditText edtAbout = dialogView.findViewById(R.id.edt_about_profil);
        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String about = edtAbout.getText().toString().trim();
                mRef = database.getReference("users");
                HashMap<String, Object> profil = new HashMap<>();
                profil.put("about", about);
                mRef.child(user.getUid()).updateChildren(profil);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
