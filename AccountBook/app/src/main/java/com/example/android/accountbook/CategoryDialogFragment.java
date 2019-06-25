package com.example.android.accountbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class CategoryDialogFragment extends DialogFragment {
    public static final String EXTRA_CATEGORY_ID =
            "com.bignerdranch.android.criminalintent.category_id";
    private static final String ARG_CATEGORY_ID = "category_id";

    private RecyclerView mCatRecyclerView;
    private GridLayoutManager mRecyclerViewLayoutManager;
    private CategoryDialogAdapter mCategoryDialogAdapter;

    public static CategoryDialogFragment newInstance(UUID categoryId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY_ID, categoryId);

        CategoryDialogFragment fragment = new CategoryDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Category mCategory = CategoryList.get(getActivity()).getCategory((UUID) getArguments().getSerializable(ARG_CATEGORY_ID));

        View view =  LayoutInflater.from(getActivity()).inflate(R.layout.dialog_category, null);

        mCatRecyclerView = view.findViewById(R.id.category_recycler_view);

        mRecyclerViewLayoutManager = new GridLayoutManager(getActivity(), 2);

        mCatRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

        CategoryList catList = CategoryList.get(getActivity());
        List<Category> categories = catList.getCategories();
        System.out.println(categories);
        mCategoryDialogAdapter = new CategoryDialogAdapter(categories);
        mCatRecyclerView.setAdapter(mCategoryDialogAdapter);
        return new AlertDialog.Builder(getActivity())
                .setView(mCatRecyclerView)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .create();
    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
//
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View view =  inflater.inflate(R.layout.dialog_category, container, false);
//
//        return view;
//    }

    private class CategoryDialogHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameTextView;
        private TextView mIconTextView; //imageview
        private Category mCategory;

        public CategoryDialogHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.dialog_item_category, parent, false));
            mNameTextView = itemView.findViewById(R.id.cat_name);
            mIconTextView = itemView.findViewById(R.id.cat_icon);
            itemView.setOnClickListener(this);
        }

        public void bind(Category category) {
            mCategory = category;

            mNameTextView.setText(mCategory.getName());
            mIconTextView.setText(mCategory.getIcon());
        }

        @Override
        public void onClick(View view) {
            //return category id to previous fragment
//            Intent intent = new Intent(getActivity(), HistoryActivity.class);
//            intent.putExtra(EXTRA_DATE, mDate.getTime());
//            startActivity(intent);
        }
    }

    private class CategoryDialogAdapter extends RecyclerView.Adapter<CategoryDialogHolder> {

        private List<Category> mCategories;

        public CategoryDialogAdapter(List<Category> categories) {
            mCategories = categories;
        }

        @Override
        public CategoryDialogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CategoryDialogHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CategoryDialogHolder holder, int position) {
            Category cat = mCategories.get(position);
            holder.bind(cat);
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }
    }
}
