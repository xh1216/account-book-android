package com.example.android.accountbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class RecordInputFragment extends Fragment {
    private static final String ARG_RECORD_ID = "record_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Record mRecord;
    private ToggleSwitch mTypeSwitch;
    private EditText mDateField;
    private EditText mAmountField;
    private EditText mCategoryField;
    private EditText mMemoField;
    private Button mConfirmButton;

    public static RecordInputFragment newInstance(UUID recordId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECORD_ID, recordId);

        RecordInputFragment fragment = new RecordInputFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID recordId = (UUID) getArguments().getSerializable(ARG_RECORD_ID);
        mRecord = RecordList.get(getActivity()).getRecord(recordId);
        RecordList.get(getActivity()).removeRecord(mRecord);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record_input, container, false);

        mTypeSwitch = v.findViewById(R.id.type_switch);
        mTypeSwitch.setCheckedPosition(mRecord.isIncome() ? 1 : 0);

        mDateField = v.findViewById(R.id.date_field);
        updateDate();
        mDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mRecord.getDate());
                dialog.setTargetFragment(RecordInputFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mAmountField = v.findViewById(R.id.amount_field);
        mAmountField.setText(String.valueOf(mRecord.getAmount()));

        mMemoField = v.findViewById(R.id.memo_field);
        mMemoField.setText(mRecord.getMemo());

        mCategoryField = v.findViewById(R.id.category_field);
        mCategoryField.setText(mRecord.getCategory().getName());

        mConfirmButton = v.findViewById(R.id.confirm_button);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mTypeSwitch.getCheckedPosition();
                System.out.println(pos);
                mRecord.setIncome(pos != 0 ? true : false);
                mRecord.setAmount(Integer.parseInt(mAmountField.getText().toString()));
                RecordList.get(getActivity()).addRecord(mRecord);
                getActivity().onBackPressed();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mRecord.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateField.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(mRecord.getDate()));
    }
}
