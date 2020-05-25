package com.example.cecyapp.clases;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class tabsViewPagerAdapter extends FragmentPagerAdapter {
    private  final List<Fragment> fragmentList = new ArrayList<>();
    private  final List<String> lista = new ArrayList<>();

    public tabsViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
