package com.example.myapplication;

import android.content.Intent;
import android.content.RestrictionEntry;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.Laporan;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormKomplain extends AppCompatActivity {

    EditText edtDeskripsi;
    Spinner spinnerBulan;
    TextView tvKategori;
    String deskripsi, bulan, kategori;
    DatabaseReference myRef;
    FirebaseDatabase mDatabase;
    FirebaseUser user;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_komplain);

        edtDeskripsi = findViewById(R.id.edt_descripsi_problem);
        spinnerBulan = findViewById(R.id.spinner_bulan);
        tvKategori = findViewById(R.id.tv_kategori);
        mDatabase = FirebaseDatabase.getInstance();
        btnSubmit = findViewById(R.id.btn_form_submit);
        myRef = mDatabase.getReference("Laporan");
        user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        kategori = intent.getStringExtra("KATEGORI");
        tvKategori.setText(kategori);

    }

    public void submitForm(View view) {
        deskripsi = edtDeskripsi.getText().toString().trim();
        bulan = spinnerBulan.getSelectedItem().toString();
        Laporan laporan = new Laporan(user.getEmail(),bulan, deskripsi, kategori);

        btnSubmit.setText("Loading...");
        try {
            myRef.child(bulan).push().setValue(laporan);
            edtDeskripsi.setText("");
            btnSubmit.setText("Submit");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT);
            btnSubmit.setText("Submit");
        }

    }

}
