package com.example.cnm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    private ActionBar toolbar;
    String phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Thông tin cá nhân");
        phonenumber = getIntent().getStringExtra("phonenumber");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,FragProfile.newInstance(phonenumber)).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,FragProfile.newInstance(phonenumber)).commit();
                    toolbar.setTitle("Thông tin cá nhân");
                    return true;
                case R.id.navigation_DanhBa:
                    fragment = new Danhba();
                    loadFragment(fragment);
                    toolbar.setTitle("Danh bạ");
                    return true;
                case R.id.navigation_mess:

                    toolbar.setTitle("Tin nhắn");
                    return true;
                case R.id.navigation_group:

                    toolbar.setTitle("group");
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}