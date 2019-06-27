package com.example.android.accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryFragment extends Fragment {

    private static final String ARG_DATE = "date";

    private Date mDate;
    private TextView mCategoryTextView;
    private TextView mDateTextView;
    private RecyclerView mHistoryRecyclerView;
    private HistoryAdapter mHistoryAdapter;

    public static HistoryFragment newInstance(Long time) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, time);

        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate = new Date();
        mDate.setTime(getArguments().getLong(ARG_DATE, -1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        mHistoryRecyclerView = v.findViewById(R.id.history_recycler_view);
        mHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDateTextView = v.findViewById(R.id.history_date);

        String dateFormat = Setting.get(getActivity()).getDateFormat();
        String date = new SimpleDateFormat(dateFormat, Locale.getDefault()).format(mDate);
        String dayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(mDate);
        mDateTextView.setText("History  -  " + date + " (" + dayOfWeek + ") ");

        HistoryActivity activity = (HistoryActivity) getActivity();
        if (activity != null) {
            activity.hideBottomBar(true);
        }

        updateUI();

        return v;
    }

    private void updateUI() {
        List<Record> records = RecordList.get(getActivity()).getRecordsByDate(mDate);

        mHistoryAdapter = new HistoryAdapter(records);
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HistoryActivity activity = (HistoryActivity) getActivity();
        if (activity != null) {
            activity.hideBottomBar(true);
        }
    }

    private class HistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mIconImageView;
        private TextView mCategoryTextView;
        private TextView mAmountTextView;
        private Record mRecord;

        public HistoryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_history, parent, false));
            mIconImageView = itemView.findViewById(R.id.history_icon);
            mCategoryTextView = itemView.findViewById(R.id.history_category);
            mAmountTextView = itemView.findViewById(R.id.history_amount);
            itemView.setOnClickListener(this);
        }

        public void bind(Record record) {
            mRecord = record;
            mIconImageView.setImageResource(record.getCategory().getIcon());
            mCategoryTextView.setText(record.getCategory().getName());
            String amountText = getString(R.string.amount_label, record.getAmount());
            if (! record.isIncome()) {
                amountText = "- " + amountText;
            }
            mAmountTextView.setText(amountText);
        }

        @Override
        public void onClick(View view) {
            Intent intent = RecordEditActivity.newIntent(getActivity(), mRecord.getId());
            startActivity(intent);
        }
    }

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {

        private List<Record> mRecords;

        public HistoryAdapter(List<Record> records) {
            mRecords = records;
        }

        @Override
        public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

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
