package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.myapplication.fragment.FragmentLapor;
import com.example.myapplication.fragment.FragmentStatistik;
import com.example.myapplication.fragment.FragmentTabNews;

public class TabAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    Context context;

    public TabAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentTabNews();
            case 1:
                return new FragmentLapor();
            case 2:
                return new FragmentStatistik();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }



}
