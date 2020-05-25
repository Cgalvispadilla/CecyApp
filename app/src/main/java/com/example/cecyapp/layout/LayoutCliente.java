package com.example.cecyapp.layout;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.cecyapp.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class LayoutCliente extends AppCompatActivity {
    public Toolbar toolbar;
    public TabItem tabItem;
    public TabLayout tabLayout;
    public ViewPager viewPager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cliente);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

}

    private void setUpViewPager(ViewPager viewPager) {
    }
}
