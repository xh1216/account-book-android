package com.example.android.accountbook;

import android.support.v4.app.Fragment;

public class DateFormatEditActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new DateFormatEditFragment();
    }
}
