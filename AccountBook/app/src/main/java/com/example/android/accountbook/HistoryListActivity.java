package com.example.android.accountbook;

import android.support.v4.app.Fragment;

public class HistoryListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new HistoryListFragment();
    }
}
