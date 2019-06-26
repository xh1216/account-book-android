package com.example.android.accountbook;

import android.support.v4.app.Fragment;

public class SettingActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SettingFragment();
    }
}
