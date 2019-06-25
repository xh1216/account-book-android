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

import com.savvyapps.togglebuttonlayout.Toggle;
import com.savvyapps.togglebuttonlayout.ToggleButtonLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class RecordInputFragment extends Fragment {
    private static final String ARG_RECORD_ID = "record_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Record mRecord;
    private ToggleButtonLayout mToggleButtonLayout;
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

        mToggleButtonLayout = v.findViewById(R.id.toggle_button_layout);
        mToggleButtonLayout.setToggled(R.id.toggle_expense, true);

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
                List<Toggle> toggleList =  mToggleButtonLayout.selectedToggles();
                System.out.println(toggleList.get(0).getTitle());
                if (toggleList.get(0).getId() == R.id.toggle_expense) {
                    mRecord.setIncome(false);
                } else {
                    mRecord.setIncome(true);
                }
                mRecord.setAmount(Integer.parseInt(mAmountField.getText().toString()));
                RecordList.get(getActivity()).addRecord(mRecord);
                getActivity().onBackPressed();
            }
        });

        RecordInputActivity activity = (RecordInputActivity) getActivity();
        if (activity != null) {
            activity.hideBottomBar(true);
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RecordInputActivity activity = (RecordInputActivity) getActivity();
        if (activity != null) {
            activity.hideBottomBar(true);
        }
    }

    private void updateDate() {
        mDateField.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(mRecord.getDate()));
    }
}
