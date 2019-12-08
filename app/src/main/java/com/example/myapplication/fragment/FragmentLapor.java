package com.example.myapplication.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.FormKomplain;
import com.example.myapplication.R;
import com.example.myapplication.model.WarningReport;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FragmentLapor extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseRef;
    FirebaseUser user;
    TextView linkSeksual, linkKeluarga, linkAnak, tvAlarmSeksual, tvAlarmAnak, tvAlarmKeluarga;

    LocationManager locationManager;
    String provider;
    Location location;
    private GoogleApiClient mGoogleApiClient;
    String  latitude = "0";
    String  longitude = " 0";


    private OnFragmentInteractionListener mListener;
    boolean play = true;
    MediaPlayer mediaPlayer;

    public FragmentLapor() {
        // Required empty public constructor
    }

    public static FragmentLapor newInstance(String param1, String param2) {
        FragmentLapor fragment = new FragmentLapor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGoogleAPI();
        if (getArguments() != null) {

        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setupGoogleAPI();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_lapor, container, false);
        linkSeksual = view.findViewById(R.id.linkSeksual);
        linkKeluarga = view.findViewById(R.id.linkkekearanKeluarga);
        linkAnak = view.findViewById(R.id.linksKekerasanAnak);
        tvAlarmAnak = view.findViewById(R.id.tv_alarm_kekerasan_anak);
        tvAlarmKeluarga = view.findViewById(R.id.tv_alarm_keluarga);
        tvAlarmSeksual = view.findViewById(R.id.tv_link_alarm_seksual);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference("Laporan");
        user = FirebaseAuth.getInstance().getCurrentUser();



            linkSeksual.setOnClickListener(this);
            linkKeluarga.setOnClickListener(this);
            linkAnak.setOnClickListener(this);
            tvAlarmSeksual.setOnClickListener(this);
            tvAlarmKeluarga.setOnClickListener(this);
            tvAlarmAnak.setOnClickListener(this);
         return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void setupGoogleAPI(){
        // initialize Google API Client
        mGoogleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }


    public void alarmAndLaporan(String type)
    {
        if (play){
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.alarm_tone);
            mediaPlayer.start();
            laporan(type);
            play = false;
        } else {

            mediaPlayer.stop();
            play = true;
        }
    }

    public void laporan(String type){
        String email = user.getEmail();
        if (location != null){
            latitude =  String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
        }

        WarningReport report = new WarningReport(type, email, latitude, longitude);
        mDatabaseRef.child(user.getUid()).child(type).push().setValue(report);
        Toast.makeText(getContext(), "Laporan Sukses" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.linkSeksual:
                goToForm("Seksual");
                break;
            case R.id.linkkekearanKeluarga:
                goToForm("Kekerasan Keluarga");
                break;
            case R.id.linksKekerasanAnak:
                goToForm("Kekerasan Anak");
                break;
            case R.id.tv_alarm_kekerasan_anak:
                alarmAndLaporan("Kekerasan Anak");
                break;
            case R.id.tv_alarm_keluarga:
                alarmAndLaporan("Kekerasan Keluarga");
                break;
            case R.id.tv_link_alarm_seksual:
                alarmAndLaporan("kekerasan seksual");
                break;

        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        location = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (location != null) {
//            Toast.makeText(getContext()," Connected to Google Location API", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void goToForm(String kategori){
        Intent intent = new Intent(getContext(), FormKomplain.class);
        intent.putExtra("KATEGORI", kategori);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // connect ke Google API Client ketika start
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        // disconnect ke Google API Client ketika activity stopped
        mGoogleApiClient.disconnect();
    }


}
