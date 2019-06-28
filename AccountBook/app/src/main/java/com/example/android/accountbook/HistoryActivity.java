package com.example.android.accountbook;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class HistoryActivity extends SingleFragmentActivity {
    private static final String EXTRA_DATE = "com.example.android.accountbook.date";

    public static Intent newIntent(Context packageContext, long time) {
        Intent intent = new Intent(packageContext, HistoryActivity.class);
        intent.putExtra(EXTRA_DATE, time);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        long time = getIntent().getLongExtra(EXTRA_DATE,-1);
        return HistoryFragment.newInstance(time);
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}
