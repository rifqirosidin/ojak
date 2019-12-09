package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class FragmentStatistik extends Fragment {

    LineChartView lineChartViewPelecehanSosial, kekerasanKeluarga, kekerasanAnak;
    String[] tahun = {"april", "mei", "juni", "juli", "Agt", "Sept", "OKt", "Nov"};
    int[] yAxisData = {50, 20, 15, 30, 20, 60, 15};
    int[] yKeluaga = {80, 10, 15, 30, 80, 60, 25};
    int[] yAnak = {40, 50, 15, 70, 80, 20, 25};

    List yAxisValues = new ArrayList();
    List yKeluargaValue = new ArrayList();
    List yAnakValue = new ArrayList();

    List axisValues = new ArrayList();

    Spinner spinner;
    TextView tvSosial, tvKeluarga, tvAnak;

    public FragmentStatistik() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_statistik, container, false);
         lineChartViewPelecehanSosial = view.findViewById(R.id.pelecehat_sosial_grafik);
         kekerasanKeluarga = view.findViewById(R.id.kekerasan_keluarga_grafik);
         kekerasanAnak = view.findViewById(R.id.kekerasan_anak_grafik);
         spinner = view.findViewById(R.id.spinner_grafik);



        Line line = new Line(yAxisValues);
        Line lineKeluaga = new Line(yKeluargaValue);
        Line lineAnak = new Line(yAnakValue);
        for(int i = 0; i < tahun.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(tahun[i]));
        }

        for (int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
//            yKeluargaValue.add(new PointValue(i, yKeluaga[i]));
//            yAnakValue.add(new PointValue(i, yAnak[i]));
        }

        for (int i = 0; i < yKeluaga.length; i++){
            yKeluargaValue.add(new PointValue(i, yKeluaga[i]));//
        }

        for (int i = 0; i < yAnak.length; i++){
            yAnakValue.add(new PointValue(i, yAnak[i]));
        }

        List lines = new ArrayList();
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

//        //keluarga/////////////////////
//
        List linesKeluarga = new ArrayList();
        linesKeluarga.add(lineKeluaga);
        LineChartData keluarga = new LineChartData();
        keluarga.setLines(linesKeluarga);

        Axis axisKeluarga = new Axis();
        axisKeluarga.setValues(axisValues);
        keluarga.setAxisXBottom(axisKeluarga);

        Axis yAxisKeluarga = new Axis();
        keluarga.setAxisYLeft(yAxisKeluarga);

//
//        //anak
        List linesAnak= new ArrayList();
        linesAnak.add(lineAnak);
        LineChartData anak = new LineChartData();
        anak.setLines(linesAnak);

        Axis axisAnak = new Axis();
        axisAnak.setValues(axisValues);
        anak.setAxisXBottom(axisAnak);

        Axis yAxisAnak= new Axis();
        anak.setAxisYLeft(yAxisAnak);


        lineChartViewPelecehanSosial.setLineChartData(data);
        kekerasanKeluarga.setLineChartData(keluarga);
        kekerasanAnak.setLineChartData(anak);

        spinner();

        return view;
    }

    public void spinner()
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0){
                    kekerasanAnak.setVisibility(View.GONE);
                    kekerasanKeluarga.setVisibility(View.GONE);
                    lineChartViewPelecehanSosial.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    kekerasanAnak.setVisibility(View.GONE);
                    kekerasanKeluarga.setVisibility(View.VISIBLE);
                    lineChartViewPelecehanSosial.setVisibility(View.GONE);
                } else if (position == 2) {
                    kekerasanAnak.setVisibility(View.VISIBLE);
                    kekerasanKeluarga.setVisibility(View.GONE);
                    lineChartViewPelecehanSosial.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }



}
