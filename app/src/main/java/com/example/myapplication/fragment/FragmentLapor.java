package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myapplication.FormKomplain;
import com.example.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentLapor.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentLapor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLapor extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView linkSeksual, linkKeluarga, linkAnak;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentLapor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLapor.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_fragment_lapor, container, false);
            linkSeksual = view.findViewById(R.id.linkSeksual);
            linkKeluarga = view.findViewById(R.id.linkkekearanKeluarga);
            linkAnak = view.findViewById(R.id.linksKekerasanAnak);

            linkSeksual.setOnClickListener(this);
            linkKeluarga.setOnClickListener(this);
            linkAnak.setOnClickListener(this);
         return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

        }
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
}
