package com.business.yourtimes.Survey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.business.yourtimes.GlobalClass;
import com.business.yourtimes.R;
import com.business.yourtimes.item.CategoryItem;

import java.util.ArrayList;

public class SurveyFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CategoryItem> categories;

    public static SurveyFragment newInstance() {
        return new SurveyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_survey, container, false);

//        ArrayList<CategoryItem> temp = ((GlobalClass) getActivity().getApplication()).getCategories();
//
//        for (int i = 0; i < 41; i++) {
//            categories.add(temp.get(i).getCategoryName());
//        }

        categories = ((GlobalClass) getActivity().getApplication()).getCategories();

        mRecyclerView = (RecyclerView) fv.findViewById(R.id.survey_list);
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new SurveyListAdapter(getContext(), categories);
        mRecyclerView.setAdapter(mAdapter);

        return fv;
    }
}
