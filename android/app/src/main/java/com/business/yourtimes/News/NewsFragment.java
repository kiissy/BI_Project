package com.business.yourtimes.News;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.business.yourtimes.R;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<NewsCard> mDataset;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.activity_main, container, false);


        mDataset = new ArrayList<>();
        mDataset.add(new NewsCard("CRIME", "There Were 2 Mass Shootings In Texas Last Week, But Only 1 On TV", "Melissa Jeltsen", "https://www.huffingtonpost.com/entry/texas-amanda-painter-mass-shooting_us_5b081ab4e4b0802d69caad89", "She left her husband. He killed their children. Just another day in America.", "2018-05-26"));

        mRecyclerView = (RecyclerView) fv.findViewById(R.id.news_list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NewsListAdapter(getContext(), mDataset);
        mRecyclerView.setAdapter(mAdapter);

        return fv;
    }
}
