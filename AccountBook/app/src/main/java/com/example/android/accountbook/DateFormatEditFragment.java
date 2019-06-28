package com.example.android.accountbook;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DateFormatEditFragment extends Fragment {

    private String[] mDateFormatList = {"dd-MM-yy", "dd-MM-yyyy", "dd-MMM-yy", "dd-MMM-yyyy", "dd/MM/yy", "dd/MM/yyyy", "dd/MMM/yy", "dd/MMM/yyyy"};
    private String[] mDateSampleList = {"(01-01-19)", "(01-01-2019)", "(01-Mon-19)", "(01-Mon-2019)", "(01/01/19)", "(01/01/2019)", "(01/Mon/19)", "(01/Mon/2019)"};
    private ListView mDateFormatListView;

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View view = inflator.inflate(R.layout.fragment_setting_edit, container, false);

        DateFormatListViewAdapter adapter = new DateFormatListViewAdapter(getActivity(), mDateFormatList, mDateSampleList);
        mDateFormatListView = view.findViewById(R.id.date_format_list_view);
        mDateFormatListView.setAdapter(adapter);

        DateFormatEditActivity activity = (DateFormatEditActivity) getActivity();
        if (activity != null) {
            activity.hideBottomBar(true);
        }

        return view;
    }

    public class DateFormatListViewAdapter extends BaseAdapter {

        private Context mContext;
        private String[] mDateFormatList;
        private String[] mDateSampleList;

        public DateFormatListViewAdapter(Context context, String[] dateFormatList, String[] dateSampleList) {
            mContext = context;
            mDateFormatList = dateFormatList;
            mDateSampleList = dateSampleList;
        }

        public int getCount() {
            return mDateFormatList.length;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                view =  inflater.inflate(R.layout.date_format_text_view, parent, false);
            }

            String dateFormat = mDateFormatList[position];
            if (dateFormat != null) {
                TextView dateFormatText = view.findViewById(R.id.date_format_text);
                TextView dateSampleText = view.findViewById(R.id.date_sample_text);
                ImageView dateFormatIcon = view.findViewById(R.id.date_format_icon);
                dateFormatText.setTag(new Integer(position));
                dateFormatText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer realPos = (Integer) v.getTag();
                        Setting.get(getActivity()).setDateFormat(mDateFormatList[realPos]);
                        Toast.makeText(getContext(), "Date format is set to " + mDateFormatList[realPos], Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();

                    }
                });
                dateFormatText.setText(dateFormat);
                dateSampleText.setText(mDateSampleList[position]);
                String currDateFormat = Setting.get(getActivity()).getDateFormat();
                if (currDateFormat.equals(mDateFormatList[position])) {
                    dateFormatIcon.setVisibility(View.VISIBLE);
                }
            }
            return view;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DateFormatEditActivity activity = (DateFormatEditActivity) getActivity();
        if (activity != null) {
            activity.hideBottomBar(true);
        }
    }
}
