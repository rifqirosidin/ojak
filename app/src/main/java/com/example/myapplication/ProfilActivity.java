package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class ProfilActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    TextView tvDetailProfil, tvEmail, tvUsername, tvBirhtday, tvGender, tvAlamat;
    FirebaseUser user;
    View dialogView;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        getSupportActionBar().hide();


        mAuth = FirebaseAuth.getInstance();
        tvDetailProfil = findViewById(R.id.tv_detail_profil);
        tvEmail = findViewById(R.id.tv_email_profil);
        tvUsername = findViewById(R.id.tv_username_profil);
        tvBirhtday = findViewById(R.id.tv_tgl_lahir);
        tvGender = findViewById(R.id.tv_jenis_kelamin);
        tvAlamat = findViewById(R.id.tv_alamat);
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        checkUserAndDisplayProfil();


    }

    public void logout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah Anda Yakin?");
        builder.setMessage("keluar dari akun ini");

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

    public void displayProfil() {
        tvEmail.setText(user.getEmail());
        userWithLoginGoogle();
//        tvDetailProfil.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua");
        mRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid());

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                try {
                    tvDetailProfil.setText(user.getAbout());
                    if (!user.getUsername().isEmpty()){
                        tvUsername.setText(user.getUsername());
                        tvBirhtday.setText(user.getBirthday());
                        tvGender.setText(user.getGender());
                        tvAlamat.setText(user.getAddress());
                    }

                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", databaseError.getMessage());
            }
        });
    }

    public void userWithLoginGoogle() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getDisplayName() != null) {
            tvUsername.setText(user.getDisplayName());
        }

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

    public void checkUserAndDisplayProfil() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(ProfilActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), "Anda belum login", Toast.LENGTH_SHORT).show();
        } else {
            displayProfil();
        }
    }

    public void tglLahir(View view) {
        showDateDialog();
    }

    private void showDateDialog(){

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date = dateFormatter.format(newDate.getTime());
                tvBirhtday.setText(dateFormatter.format(newDate.getTime()));
                mRef = database.getReference("users");
                HashMap<String, Object> profil = new HashMap<>();
                profil.put("birthday", date);
                mRef.child(user.getUid()).updateChildren(profil);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }


    public void jenisKelamin(View view) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.spinner_jenis_kelamin, null);
        dialog.setView(dialogView);

        final Spinner spinnerJenisKelamin = dialogView.findViewById(R.id.spinner_gender);
        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String jenisKelamin = spinnerJenisKelamin.getSelectedItem().toString();
                tvGender.setText(jenisKelamin);
                mRef = database.getReference("users");
                HashMap<String, Object> profil = new HashMap<>();
                profil.put("gender", jenisKelamin);
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

    public void Alamat(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.update_alamat_profil, null);
        dialog.setView(dialogView);

        final EditText edtALamat = dialogView.findViewById(R.id.edt_alamat);
        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String alamat = edtALamat.getText().toString().trim();
                mRef = database.getReference("users");
                HashMap<String, Object> profil = new HashMap<>();
                profil.put("address", alamat);
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
