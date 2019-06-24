package com.example.android.accountbook;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_record:
                    fragment = new RecordFragment();
                    break;
                case R.id.navigation_history:
                    fragment = new HistoryListFragment();
                    break;
//                case R.id.navigation_setting:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.bottom_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
}
