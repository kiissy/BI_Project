package com.business.yourtimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.business.yourtimes.News.NewsCard;
import com.business.yourtimes.News.NewsListAdapter;
import com.business.yourtimes.item.CategoryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

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
    private String tempData;


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

        tempData = ((GlobalClass) getApplicationContext()).temp;
        //getExplicitNews();
        //setAdapter();
        getImplicitNews();
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
        JSONArray jArray = new JSONArray();

        try {
            for (int i = 0; i < data.size(); i++) {
                JSONObject sObject = new JSONObject();
                sObject.put("category", data.get(i).getCategory());
                jArray.put(sObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ContentValues values = null;
        //values.put("category", jArray.toString());
        String postBody = jArray.toString();
        Log.d("JSON",postBody);
        String url = "http://192.168.43.13:8080/e_recommend";
        NetworkTask networkTask = new NetworkTask(url, null,  postBody);
        networkTask.execute();

    }


    private void getImplicitNews() {
        tempData = "[{\"category\": \"ENTERTAINMENT\", \"headline\": \"Chelsea Clinton Wrote To Ronald Reagan About Nazis When She Was 5\", \"authors\": \"Lee Moran\", \"link\": \"https://www.huffingtonpost.com/entry/chelsea-clinton-letter-nazis-ronald-reagan_us_5ae19172e4b02baed1b6cb45\", \"short_description\": \"The former president's failure to respond to Clinton's letter ended up changing White House policy.\", \"date\": \"2018-04-26\", \"index\": 26370}, {\"category\": \"CRIME\", \"headline\": \"Retirement Community Resident Allegedly Tested Homemade Poison On Neighbors\", \"authors\": \"Hilary Hanson\", \"link\": \"https://www.huffingtonpost.com/entry/ricin-retirement-community-neighbors_us_5a22ea6ce4b0a02abe918890\", \"short_description\": \"A 70-year-old woman is accused of sprinkling ricin on the food of other seniors.\", \"date\": \"2017-12-02\", \"index\": 27207}, {\"category\": \"PARENTS\", \"headline\": \"How To Tell Your Kids They're Going To Have A Sibling\", \"authors\": \"Taylor Pittman\", \"link\": \"https://www.huffingtonpost.com/entry/how-to-tell-your-kids-theyre-going-to-have-a-sibling_us_5a4fa362e4b003133ec77673\", \"short_description\": \"What to say, what to avoid and what to anticipate.\", \"date\": \"2018-01-08\", \"index\": 18022}, {\"category\": \"BLACK VOICES\", \"headline\": \"Mississippi School Finds No Evidence Principal Cut Student's Hair Without Permission (UPDATE)\", \"authors\": \"David Moye\", \"link\": \"https://www.huffingtonpost.com/entry/mississippi-boy-hair-locs-cut-principal_us_5abbfa33e4b03e2a5c78e34d\", \"short_description\": \"\\u201c[W]e found absolutely no evidence ... that his allegations of having his hair cut at school exist.\\\"\", \"date\": \"2018-03-28\", \"index\": 35170}]";
        Log.d("JSON", tempData);
        String url = "http://192.168.43.13:8080/i_recommend";
        NetworkTask networkTask = new NetworkTask(url, null,  tempData);
        networkTask.execute();
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        String url;
        ContentValues values;
        String body;

        NetworkTask(String url, ContentValues values, String postBody) {
            this.url = url;
            this.body = postBody;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected  String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values,body);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Log.d("Result", result);
        }

    }



}
