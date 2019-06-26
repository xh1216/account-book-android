package com.example.android.accountbook;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_record:
                    fragment = new RecordActivity().createFragment();
                    break;
                case R.id.navigation_history:
                    fragment = new HistoryListActivity().createFragment();
                    break;
                case R.id.navigation_setting:
                    fragment = new SettingActivity().createFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigationView = findViewById(R.id.bottom_nav_view);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        initCategoryData();

        FragmentManager fm = getSupportFragmentManager();   //ensure stay in same fragment after rotate
        Fragment fragment = fm.findFragmentById(R.id.main_container);

        if (fragment == null) {
            fragment = new RecordFragment();
            fm.beginTransaction()
                    .add(R.id.main_container, fragment)
                    .commit();
        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

//    private void initCategoryData() {
//        String[] expCatName = {"food", "transport", "entertainment"};
//        String[] incomeCatName = {"salary", "pocket money"};
//
//        for (int i = 0; i < expCatName.length; i++) {
//            Category cat = new Category();
//            cat.setName(expCatName[i]);
//            cat.setIcon("exp_cat" + i + ".png");
//            cat.setIncome(false);
//            CategoryList.get(MainActivity.this).addCategory(cat);
//        }
//
//        for (int i = 0; i < incomeCatName.length; i++) {
//            Category cat = new Category();
//            cat.setName(incomeCatName[i]);
//            cat.setIcon("income_cat" + i + ".png");
//            cat.setIncome(true);
//            CategoryList.get(MainActivity.this).addCategory(cat);
//        }
//    }
}
