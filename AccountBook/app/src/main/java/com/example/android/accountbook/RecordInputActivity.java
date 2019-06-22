package com.example.android.accountbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

public class RecordInputActivity extends AppCompatActivity {

    private static final String EXTRA_RECORD_ID =
            "com.example.android.accountbook.record_id";
    private static final String ARG_RECORD_ID = "record_id";
    private static final String ARG_CATEGORY_ID = "category_id";

    private Record mRecord;
    private Button mDateButton;
    private EditText mAmountField;
    private Button mCategoryButton;
    private EditText mMemoField;
    private Button mConfirmButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_input);
        UUID recordId = (UUID) getIntent().getSerializableExtra(EXTRA_RECORD_ID);
        mRecord = RecordList.get(this).getRecord(recordId);

        mDateButton = findViewById(R.id.date_button);
        mDateButton.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(mRecord.getDate()));
        mDateButton.setEnabled(false);

        mAmountField = findViewById(R.id.amount_field);
        mAmountField.setText(String.valueOf(mRecord.getAmount()));

        mMemoField = findViewById(R.id.memo_field);
        mMemoField.setText(mRecord.getMemo());

        mCategoryButton = findViewById(R.id.category_button);
        mCategoryButton.setText(mRecord.getCategory().getName());
        mCategoryButton.setEnabled(false);

        mConfirmButton = findViewById(R.id.confirm_button);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecord.setAmount(Integer.parseInt(mAmountField.getText().toString()));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //remove record
        finish();
    }
}
