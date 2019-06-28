package com.example.android.accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class SettingFragment extends Fragment {

    RelativeLayout mDateFormatLayout;

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View view = inflator.inflate(R.layout.fragment_setting, container, false);

        mDateFormatLayout = view.findViewById(R.id.date_format);
        mDateFormatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DateFormatEditActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
