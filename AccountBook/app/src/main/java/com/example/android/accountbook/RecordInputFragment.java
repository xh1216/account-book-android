package com.example.android.accountbook;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.savvyapps.togglebuttonlayout.Toggle;
import com.savvyapps.togglebuttonlayout.ToggleButtonLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class RecordInputFragment extends Fragment {

    private static final String ARG_RECORD_ID = "record_id";
    private static final String DIALOG_DATE = "dialog_date";
    private static final String CATEGORY_ID = "category_id";
    private static final String IS_INCOME = "is_income";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CATEGORY = 1;

    private Record mRecord;
    private ToggleButtonLayout mToggleButtonLayout;
    private EditText mDateField;
    private EditText mAmountField;
    private EditText mCategoryField;
    private EditText mMemoField;
    private Button mConfirmButton;
    private Button mRemoveButton;

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
        mToggleButtonLayout.setToggled(mRecord.isIncome()? R.id.toggle_income : R.id.toggle_expense, true);

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
        mCategoryField.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                CategoryDialogFragment dialog = CategoryDialogFragment.newInstance(mRecord.getCategory().getId());
                dialog.setTargetFragment(RecordInputFragment.this, REQUEST_CATEGORY);
                Bundle args = new Bundle();
                args.putInt(IS_INCOME, mRecord.isIncome() ? 1 : 0);
                args.putSerializable(CATEGORY_ID, mRecord.getCategory().getId());
                dialog.setArguments(args);
                dialog.show(manager, CATEGORY_ID);
            }
        });

        mRemoveButton = v.findViewById(R.id.remove_button);
        mRemoveButton.setVisibility(View.GONE);

        mConfirmButton = v.findViewById(R.id.confirm_button);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(mAmountField.getText().toString()) <= 0) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Amount entry")
                            .setMessage("Enter amount more than 0")
                            .setNegativeButton(android.R.string.ok, null)
                            .show();
                } else {
                    List<Toggle> toggleList =  mToggleButtonLayout.selectedToggles();
                    System.out.println(toggleList.get(0).getTitle());
                    if (toggleList.get(0).getId() == R.id.toggle_expense) {
                        mRecord.setIncome(false);
                    } else {
                        mRecord.setIncome(true);
                    }
                    mRecord.setAmount(Integer.parseInt(mAmountField.getText().toString()));
                    mRecord.setMemo(mMemoField.getText().toString());
                    RecordList.get(getActivity()).addRecord(mRecord);
                    getActivity().onBackPressed();
                }
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
        RecordInputActivity activity = (RecordInputActivity) getActivity();
        if (activity != null) {
            activity.hideBottomBar(true);
        }
    }

    private void updateDate() {
        String dateFormat = Setting.get(getActivity()).getDateFormat();
        mDateField.setText(new SimpleDateFormat(dateFormat, Locale.getDefault()).format(mRecord.getDate()));
    }
}
