package com.example.alioth.ultimaversion.Todo.Adapter;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Alioth on 25/06/2015.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
    List<Fragment> fragments;
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<>();
    }




    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int arg0) {
        return this.fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
