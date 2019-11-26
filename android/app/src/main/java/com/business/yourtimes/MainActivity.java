package com.business.yourtimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.business.yourtimes.News.NewsCard;
import com.business.yourtimes.News.NewsListAdapter;
import com.business.yourtimes.item.CategoryItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView maintext;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<NewsCard> mDataset;

    private LinearLayout mSignout;
    private ImageView mSignoutIcon;
    private LinearLayout mRefresh;
    private ImageView mRefreshIcon;

    private ServiceApi service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        /* set the recyclerview */
        mRecyclerView = (RecyclerView) findViewById(R.id.news_list);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataset = new ArrayList<>();
        //mDataset.add(new NewsCard("CRIME", "There Were 2 Mass Shootings In Texas Last Week, But Only 1 On TV", "Melissa Jeltsen", "https://www.huffingtonpost.com/entry/texas-amanda-painter-mass-shooting_us_5b081ab4e4b0802d69caad89", "She left her husband. He killed their children. Just another day in America.", "2018-05-26"));

        /* set sign out button function */
        mSignout = (LinearLayout) findViewById(R.id.signout_layout);
        mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "Sign out button has been clicked");
                ((GlobalClass) getApplicationContext()).initialize();
                Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
            }
        });

        mSignoutIcon = (ImageView) findViewById(R.id.signout_icon);
        mSignoutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "Sign out button has been clicked");
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
                Log.d("MainActivity", "Refresh button has been clicked");
                mDataset.clear();
                mDataset.add(new NewsCard("COMEDY", "Trump's New 'MAGA'-Themed Swimwear Sinks On Twitter", "Ed Mazza", "https://www.huffingtonpost.com/entry/trump-maga-swimsuits_us_5b07ae38e4b0802d69c9aae9", "\"Does this swimsuit make me look racist?\"", "2018-05-25"));
                mDataset.add(new NewsCard("ENTERTAINMENT", "Samantha Bee's Parents Think America Is Basically A War Zone", "Andy McDonald", "https://www.huffingtonpost.com/entry/samantha-bee-parents-canadians-america_us_5b083ea4e4b0568a880b29d2", "Her dad even sent her a Kevlar vest.", "2018-05-25"));

                setAdapter();
            }
        });

        mRefreshIcon = (ImageView) findViewById(R.id.refresh_icon);
        mRefreshIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "Refresh button has been clicked");
                mDataset.clear();
                mDataset.add(new NewsCard("COMEDY", "Trump's New 'MAGA'-Themed Swimwear Sinks On Twitter", "Ed Mazza", "https://www.huffingtonpost.com/entry/trump-maga-swimsuits_us_5b07ae38e4b0802d69c9aae9", "\"Does this swimsuit make me look racist?\"", "2018-05-25"));
                mDataset.add(new NewsCard("ENTERTAINMENT", "Samantha Bee's Parents Think America Is Basically A War Zone", "Andy McDonald", "https://www.huffingtonpost.com/entry/samantha-bee-parents-canadians-america_us_5b083ea4e4b0568a880b29d2", "Her dad even sent her a Kevlar vest.", "2018-05-25"));

                setAdapter();
            }
        });

        getExplicitNews();
        //setAdapter();
    }

    private void setAdapter() {
        mAdapter = new NewsListAdapter(getApplicationContext(), mDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getExplicitNews() {
        ArrayList<CategoryItem> data = new ArrayList<>();
        data.add(new CategoryItem("ENTERTAINMENT"));
        data.add(new CategoryItem("CRIME"));
        data.add(new CategoryItem("PARENTS"));

        service.explicitMethod(data).enqueue(new Callback<ArrayList<NewsCard>>() {
            @Override
            public void onResponse(Call<ArrayList<NewsCard>> call, Response<ArrayList<NewsCard>> response) {
                //mDataset.clear();
                mDataset = response.body();

                setAdapter();
            }

            @Override
            public void onFailure(Call<ArrayList<NewsCard>> call, Throwable t) {
                Log.e("MainActivity", "getExplicitNews server error");
            }
        });


    }
}
