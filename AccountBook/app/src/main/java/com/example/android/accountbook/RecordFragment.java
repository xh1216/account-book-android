package com.example.android.accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecordFragment extends Fragment {

    private TextView mDateTextView;
    private TextView mIncomeTextView;
    private TextView mExpensesTextView;
    private TextView mTotalTextView;
    private Button mAddRecordButton;

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View view = inflator.inflate(R.layout.fragment_record, container, false);

        mDateTextView = view.findViewById(R.id.date_text);
        mIncomeTextView = view.findViewById(R.id.income_text);
        mExpensesTextView = view.findViewById(R.id.expenses_text);
        mTotalTextView = view.findViewById(R.id.total_text);
        mAddRecordButton = view.findViewById(R.id.add_record_button);
        mAddRecordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Record record = new Record();
                RecordList.get(getActivity()).addRecord(record);
                Intent intent = RecordInputActivity.newIntent(getActivity(), record.getId());
                startActivity(intent);
            }
        });
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        String dateFormat = Setting.get(getActivity()).getDateFormat();
        String date = new SimpleDateFormat(dateFormat, Locale.getDefault()).format(new Date());
        String dayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(new Date());
        mDateTextView.setText(date + " (" + dayOfWeek + ") ");

        List<Record> mRecords = RecordList.get(getActivity()).getThisMonthRecords();
        int income = 0;
        int exp = 0;
        for (Record rec: mRecords) {
            if (rec.isIncome()) {
                income += rec.getAmount();
            } else {
                exp += rec.getAmount();
            }
        }
        mIncomeTextView.setText(getString(R.string.income_label, income));
        mExpensesTextView.setText(getString(R.string.expenses_label, exp));
        String amountText;
        if (income - exp < 0) {
            amountText = "- " + getString(R.string.amount_label, -(income - exp));
        } else {
            amountText = getString(R.string.amount_label, income - exp);
        }
        mTotalTextView.setText("This month total       " + amountText);
    }
}
