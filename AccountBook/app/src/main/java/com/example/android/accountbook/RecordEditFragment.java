package com.example.android.accountbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class RecordEditFragment extends Fragment {

    private static final String ARG_RECORD_ID = "record_id";
    private static final String DIALOG_DATE = "dialog_date";
    private static final String CATEGORY_ID = "category_id";
    private static final String IS_INCOME = "is_income";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CATEGORY = 1;

    private Record mRecord;
    private ToggleButton mToggleButton;
    private EditText mDateField;
    private EditText mAmountField;
    private EditText mCategoryField;
    private EditText mMemoField;
    private Button mConfirmButton;
    private Button mRemoveButton;

    public static RecordEditFragment newInstance(UUID recordId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECORD_ID, recordId);

        RecordEditFragment fragment = new RecordEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID recordId = (UUID) getArguments().getSerializable(ARG_RECORD_ID);
        mRecord = RecordList.get(getActivity()).getRecord(recordId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record_input, container, false);

        mToggleButton = v.findViewById(R.id.toggle_button);
        mToggleButton.setChecked(! mRecord.isIncome());
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToggleButton.isChecked()) {
                    mRecord.setIncome(false);
                } else {
                    mRecord.setIncome(true);
                }
            }
        });

        mDateField = v.findViewById(R.id.date_field);
        updateDate();
        mDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mRecord.getDate());
                dialog.setTargetFragment(RecordEditFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mAmountField = v.findViewById(R.id.amount_field);
        mAmountField.setText(String.valueOf(mRecord.getAmount()));

        mMemoField = v.findViewById(R.id.memo_field);
        mMemoField.setText(mRecord.getMemo());

        mCategoryField = v.findViewById(R.id.category_field);
        mCategoryField.setText(mRecord.getCategory().getName());
        mCategoryField.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                CategoryDialogFragment dialog = CategoryDialogFragment.newInstance(mRecord.getCategory().getId());
                dialog.setTargetFragment(RecordEditFragment.this, REQUEST_CATEGORY);
                Bundle args = new Bundle();
                args.putInt(IS_INCOME, mRecord.isIncome() ? 1 : 0);
                args.putSerializable(CATEGORY_ID, mRecord.getCategory().getId());
                dialog.setArguments(args);
                dialog.show(manager, CATEGORY_ID);
            }
        });

        mRemoveButton = v.findViewById(R.id.remove_button);
        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Remove record")
                        .setMessage("Are you sure you want to remove this record?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                RecordList.get(getActivity()).removeRecord(mRecord);
                                Toast.makeText(getActivity(),
                                        R.string.remove_toast,
                                        Toast.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
            }
        });

        mConfirmButton = v.findViewById(R.id.confirm_button);
        mConfirmButton.setText("Update Record");
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(mAmountField.getText().toString()) <= 0) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Amount")
                            .setMessage("Enter amount more than 0")
                            .setNegativeButton(android.R.string.ok, null)
                            .show();
                } else {
                    mRecord.setAmount(Integer.parseInt(mAmountField.getText().toString()));
                    mRecord.setMemo(mMemoField.getText().toString());
                    RecordList.get(getActivity()).updateRecord(mRecord);
                    getActivity().onBackPressed();
                }
            }
        });

        RecordEditActivity activity = (RecordEditActivity) getActivity();
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
        } else if (requestCode == REQUEST_CATEGORY) {
            UUID categoryId = (UUID) data.getSerializableExtra(CategoryDialogFragment.EXTRA_CATEGORY_ID);
            Category category = CategoryList.get(getActivity()).getCategory(categoryId);
            mRecord.setCategory(category);
            mCategoryField.setText(mRecord.getCategory().getName());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RecordEditActivity activity = (RecordEditActivity) getActivity();
        if (activity != null) {
            activity.hideBottomBar(true);
        }
    }

    private void updateDate() {
        String dateFormat = Setting.get(getActivity()).getDateFormat();
        mDateField.setText(new SimpleDateFormat(dateFormat, Locale.getDefault()).format(mRecord.getDate()));
    }
}
