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

import java.util.List;

public class HistoryListFragment extends Fragment {

    private RecyclerView mRecordRecyclerView;
    private HistoryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history_list, container, false);

        mRecordRecyclerView = view.findViewById(R.id.history_recycler_view);
        mRecordRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        RecordList recordList = RecordList.get(getActivity());
        List<Record> records = recordList.getRecords();

        mAdapter = new HistoryAdapter(records);
        mRecordRecyclerView.setAdapter(mAdapter);
    }

    private class HistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mAmountTextView;
        private Record mRecord;

        public HistoryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_history, parent, false));
            mAmountTextView = itemView.findViewById(R.id.history_record_amount);
            itemView.setOnClickListener(this);
        }

        public void bind(Record record) {
            mRecord = record;
            mAmountTextView.setText(String.valueOf(mRecord.getAmount()));
        }

        @Override
        public void onClick(View view) {
            //go to history activity
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
