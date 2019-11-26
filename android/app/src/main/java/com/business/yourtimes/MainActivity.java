package com.business.yourtimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.business.yourtimes.News.NewsCard;
import com.business.yourtimes.News.NewsListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView maintext;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<NewsCard> mDataset;

    private LinearLayout mSignout;
    private LinearLayout mRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* set the recyclerview */
        mDataset = new ArrayList<>();
        mDataset.add(new NewsCard("CRIME", "There Were 2 Mass Shootings In Texas Last Week, But Only 1 On TV", "Melissa Jeltsen", "https://www.huffingtonpost.com/entry/texas-amanda-painter-mass-shooting_us_5b081ab4e4b0802d69caad89", "She left her husband. He killed their children. Just another day in America.", "2018-05-26"));

        /* set sign out button function */
        mSignout = (LinearLayout) findViewById(R.id.signout_layout);
        mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GlobalClass) getApplicationContext()).initialize();
                Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
            }
        });

        /* set refresh button function */
        mRefresh = (LinearLayout) findViewById(R.id.refresh_layout);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataset = new ArrayList<>();
                mDataset.add(new NewsCard("CRIME", "There Were 2 Mass Shootings In Texas Last Week, But Only 1 On TV", "Melissa Jeltsen", "https://www.huffingtonpost.com/entry/texas-amanda-painter-mass-shooting_us_5b081ab4e4b0802d69caad89", "She left her husband. He killed their children. Just another day in America.", "2018-05-26"));

                /* 새로운 뉴스를 받아오는 코드 */
                mAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.news_list);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NewsListAdapter(getApplicationContext(), mDataset);
        mRecyclerView.setAdapter(mAdapter);

    }
}
