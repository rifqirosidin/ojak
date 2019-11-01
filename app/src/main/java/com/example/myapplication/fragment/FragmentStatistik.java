package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    String[] tahun = {"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017"};
    int[] yAxisData = {50, 20, 15, 30, 20, 60, 15};
    List yAxisValues = new ArrayList();
    List axisValues = new ArrayList();


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

        Line line = new Line(yAxisValues);
        for(int i = 0; i < tahun.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(tahun[i]));
        }

        for (int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
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

        lineChartViewPelecehanSosial.setLineChartData(data);
        kekerasanKeluarga.setLineChartData(data);
        kekerasanAnak.setLineChartData(data);


        return view;
    }



}
