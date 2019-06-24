package com.example.android.accountbook;

import android.content.Intent;
import android.os.Bundle;
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

public class HistoryActivity extends AppCompatActivity {

    private static final String EXTRA_DATE = "com.example.android.accountbook.date";

    private Date mDate;
    private TextView mDateTextView;
    private RecyclerView mHistoryRecyclerView;
    private HistoryAdapter mHistoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mHistoryRecyclerView = findViewById(R.id.history_recycler_view);
        mHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        mDateTextView = findViewById(R.id.history_date);
        mDate = new Date();
        mDate.setTime(getIntent().getLongExtra(EXTRA_DATE,-1));
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(mDate);
        String dayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(mDate);
        mDateTextView.setText("History  -  " + date + " (" + dayOfWeek + ") ");
        updateUI();
    }

    private void updateUI() {
        List<Record> records = RecordList.get(this).getRecordsByDate(mDate);

        mHistoryAdapter = new HistoryAdapter(records);
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);
    }
    private class HistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mCategoryTextView;
        private TextView mAmountTextView;
        private Record mRecord;

        public HistoryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_history, parent, false));
            mAmountTextView = itemView.findViewById(R.id.history_amount);
            mCategoryTextView = itemView.findViewById(R.id.history_category);
            itemView.setOnClickListener(this);
        }

        public void bind(Record record) {
            mRecord = record;
            mAmountTextView.setText(String.valueOf(record.getAmount()));
            mCategoryTextView.setText(record.getCategory().getName());
        }

        @Override
        public void onClick(View view) {
            //go to history activity and pass this date
//            Intent intent = new Intent(getActivity(), HistoryActivity.class);
//            intent.putExtra(EXTRA_DATE, mDate.getTime());
//            startActivity(intent);
        }
    }

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {

        private List<Record> mRecords;

        public HistoryAdapter(List<Record> records) {
            mRecords = records;
        }

        @Override
        public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(HistoryActivity.this);

            return new HistoryHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(HistoryHolder holder, int position) {
            Record record = mRecords.get(position);
            holder.bind(record);
        }

        @Override
        public int getItemCount() {
            return mRecords.size();
        }
    }
}
