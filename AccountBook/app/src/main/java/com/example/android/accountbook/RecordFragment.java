package com.example.android.accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecordFragment extends Fragment {

    private static final String EXTRA_RECORD_ID =
            "com.example.android.accountbook.record_id";

    private TextView mDateTextView;
    private TextView mIncomeTextView;
    private TextView mExpensesTextView;
    private Button mAddRecordButton;

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View view = inflator.inflate(R.layout.fragment_record, container, false);
        List<Record> mRecords = RecordList.get(getActivity()).getRecords();

        mDateTextView = view.findViewById(R.id.date_text);
        mIncomeTextView = view.findViewById(R.id.income_text);
        mExpensesTextView = view.findViewById(R.id.expenses_text);
        mAddRecordButton = view.findViewById(R.id.add_record_button);

        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        String dayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(new Date());
        mDateTextView.setText(date + " (" + dayOfWeek + ") ");

        int income = 0;
        int exp = 0;
        for (Record rec: mRecords) {
            if (rec.isIncome()) {
                income += rec.getAmount();
            } else {
                exp += rec.getAmount();
            }
        }

        mIncomeTextView.setText(String.valueOf(income));
        mExpensesTextView.setText(String.valueOf(exp));

        mAddRecordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Record record = new Record();
                RecordList.get(getActivity()).addRecord(record);
                Intent intent = new Intent(getActivity(), RecordInputActivity.class);
                intent.putExtra(EXTRA_RECORD_ID, record.getId());
                startActivity(intent);
            }
        });
        return view;
    }
}
