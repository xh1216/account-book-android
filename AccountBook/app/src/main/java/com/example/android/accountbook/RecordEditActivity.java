package com.example.android.accountbook;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class RecordEditActivity extends SingleFragmentActivity {
    public static final String EXTRA_RECORD_ID =
            "com.bignerdranch.android.criminalintent.record_id";

    public static Intent newIntent(Context packageContext, UUID recordId) {
        Intent intent = new Intent(packageContext, RecordEditActivity.class);
        intent.putExtra(EXTRA_RECORD_ID, recordId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID recordId = (UUID) getIntent().getSerializableExtra(EXTRA_RECORD_ID);
        return RecordEditFragment.newInstance(recordId);
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}