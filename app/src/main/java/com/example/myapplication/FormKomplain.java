package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.Laporan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class FormKomplain extends AppCompatActivity {

    EditText edtDeskripsi;
    Spinner spinnerBulan;
    TextView tvKategori;
    String deskripsi, bulan, kategori;
    DatabaseReference myRef;
    FirebaseDatabase mDatabase;
    FirebaseUser user;
    Button btnSubmit;
    private StorageReference mStorageRef;
    private final int PICK_IMAGE_REQUEST = 100;
    private ImageView addImage;
    private Uri filePath;
    String urlImage = "";
    StorageReference imageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_komplain);

        edtDeskripsi = findViewById(R.id.edt_descripsi_problem);
        spinnerBulan = findViewById(R.id.spinner_bulan);
        tvKategori = findViewById(R.id.tv_kategori);
        mDatabase = FirebaseDatabase.getInstance();
        btnSubmit = findViewById(R.id.btn_form_submit);
        addImage = findViewById(R.id.add_image);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef = mDatabase.getReference("Laporan");
        user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        kategori = intent.getStringExtra("KATEGORI");
        tvKategori.setText(kategori);

    }

    public void submitForm(View view) {
        deskripsi = edtDeskripsi.getText().toString().trim();
        bulan = spinnerBulan.getSelectedItem().toString();

        if (TextUtils.isEmpty(deskripsi)) {
            Toast.makeText(getApplicationContext(), "Kolom deskripsi harus diisi !!", Toast.LENGTH_SHORT).show();
        }
        if (filePath == null) {
            Toast.makeText(getApplicationContext(), "Gambar harus dilampirkan !!", Toast.LENGTH_SHORT).show();
        }
        if (!TextUtils.isEmpty(deskripsi) && filePath != null) {
            try {
                btnSubmit.setText("Loading...");
                sendToFirebase();


            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
        }

    }


    public void addImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                addImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendToFirebase() {



        imageRef = mStorageRef.child("images/" + UUID.randomUUID().toString());
        imageRef.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urlImage = uri.toString().trim();
                                Laporan laporan = new Laporan(user.getEmail(), bulan, deskripsi, kategori, urlImage);
                                myRef.child(bulan).push().setValue(laporan).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        addImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_a_photo_black_24dp));
                                        edtDeskripsi.setText("");
                                        filePath = null;
                                        btnSubmit.setText("Submit");
                                        Toast.makeText(FormKomplain.this, "Sukses", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        btnSubmit.setText("Submit");
                                        Log.e("error", e.getMessage());
                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        btnSubmit.setText("Submit");
                        Toast.makeText(FormKomplain.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
