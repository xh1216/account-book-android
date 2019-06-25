package com.example.android.accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class HistoryListFragment extends Fragment {

    private static final String EXTRA_DATE = "com.example.android.accountbook.date";

    private TextView mEmptyRecordText;
    private RecyclerView mHistoryListRecyclerView;
    private HistoryListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history_list, container, false);

        mEmptyRecordText = view.findViewById(R.id.empty_record_text);
        mEmptyRecordText.setVisibility(View.GONE);
        mHistoryListRecyclerView = view.findViewById(R.id.history_list_recycler_view);
        mHistoryListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        RecordList recordList = RecordList.get(getActivity());
        List<Record> records = recordList.getRecords();
        if (records.isEmpty()) {
            mEmptyRecordText.setVisibility(View.VISIBLE);
        }
        HashSet<Date> dateSet = new HashSet<>();
        for (Record rec: records) {
            dateSet.add(rec.getDate());
        }
        List<Date> dateList = new ArrayList<>(dateSet);
        Collections.sort(dateList, Collections.<Date>reverseOrder());
        mAdapter = new HistoryListAdapter(dateList);
        mHistoryListRecyclerView.setAdapter(mAdapter);
    }

    private class HistoryListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mAmountTextView;
        private TextView mDateTextView;
        private Date mDate;

        public HistoryListHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_history, parent, false));
            mAmountTextView = itemView.findViewById(R.id.history_record_amount);
            mDateTextView = itemView.findViewById(R.id.history_record_date);
            itemView.setOnClickListener(this);
        }

        public void bind(Date date) {
            mDate = date;
            int totalAmount = 0;
            List<Record> recList = RecordList.get(getActivity()).getRecordsByDate(mDate);
            for (Record rec : recList) {
                if (rec.isIncome()) {
                    totalAmount += rec.getAmount();
                } else {
                    totalAmount -= rec.getAmount();
                }
            }
            mAmountTextView.setText(String.valueOf(totalAmount));
            mDateTextView.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date));
        }

        @Override
        public void onClick(View view) {
            //go to history activity and pass this date
            Intent intent = new Intent(getActivity(), HistoryActivity.class);
            intent.putExtra(EXTRA_DATE, mDate.getTime());
            startActivity(intent);
        }
    }

    private class HistoryListAdapter extends RecyclerView.Adapter<HistoryListHolder> {

        private List<Date> mDates;

        public HistoryListAdapter(List<Date> dates) {
            mDates = dates;
        }

        @Override
        public HistoryListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new HistoryListHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(HistoryListHolder holder, int position) {
            Date date = mDates.get(position);
            holder.bind(date);
        }

        @Override
        public int getItemCount() {
            return mDates.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
