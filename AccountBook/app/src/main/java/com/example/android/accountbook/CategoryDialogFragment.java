package com.example.android.accountbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class CategoryDialogFragment extends DialogFragment {
    public static final String EXTRA_CATEGORY_ID =
            "com.example.android.accountbook.category_id";
    private static final String ARG_CATEGORY_ID = "category_id";
    private static final String ARG_IS_INCOME = "is_income";

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
        int a = getArguments().getInt(ARG_IS_INCOME);
        Boolean isIncome = (1 == getArguments().getInt(ARG_IS_INCOME));

        View view =  LayoutInflater.from(getActivity()).inflate(R.layout.dialog_category, null);

        mCatRecyclerView = view.findViewById(R.id.category_recycler_view);
        mRecyclerViewLayoutManager = new GridLayoutManager(getActivity(), 2);
        mCatRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

        CategoryList catList = CategoryList.get(getActivity());
        List<Category> categories;
        if (isIncome) {
            categories = catList.getIncomeCategories();
        } else {
            categories = catList.getExpenseCategories();
        }
        mCategoryDialogAdapter = new CategoryDialogAdapter(categories);
        mCatRecyclerView.setAdapter(mCategoryDialogAdapter);
        return new AlertDialog.Builder(getActivity())
                .setView(mCatRecyclerView)
                .create();
    }

    private class CategoryDialogHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameTextView;
        private ImageView mIconImageView;
        private Category mCategory;

        public CategoryDialogHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.dialog_item_category, parent, false));
            mNameTextView = itemView.findViewById(R.id.cat_name);
            mIconImageView = itemView.findViewById(R.id.cat_icon);
            itemView.setOnClickListener(this);
        }

        public void bind(Category category) {
            mCategory = category;

            mNameTextView.setText(mCategory.getName());
            mIconImageView.setImageResource(mCategory.getIcon());
        }

        @Override
        public void onClick(View view) {
            sendResult(Activity.RESULT_OK, mCategory);
            dismiss();
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

    private void sendResult(int resultCode, Category category) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CATEGORY_ID, category.getId());

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
