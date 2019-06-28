package com.example.android.accountbook;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import java.util.UUID;

public class RecordEditActivity extends SingleFragmentActivity {
    public static final String EXTRA_RECORD_ID =
            "com.example.android.accountbook.record_id";

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
