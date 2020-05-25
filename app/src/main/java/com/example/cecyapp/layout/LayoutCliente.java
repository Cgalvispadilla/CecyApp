package com.example.cecyapp.layout;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cecyapp.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class LayoutCliente extends AppCompatActivity {
    public Toolbar toolbar;
    public TabItem tabItem;
    public TabLayout tabLayout;
    public ScrollView Scrollview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cliente);

}
}
