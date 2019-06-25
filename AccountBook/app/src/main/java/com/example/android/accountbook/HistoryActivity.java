package com.example.android.accountbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
