package com.business.yourtimes;


import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.business.yourtimes.News.NewsCard;
import com.business.yourtimes.item.CategoryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GlobalClass extends Application {
    private ArrayList<CategoryItem> categories;

    /* 처음 접속하는 유저인지 확인하는 전역 변수 */
    private SharedPreferences appData;
    private String[] keys;
    private int[] history_indices;

    private boolean[] explicitly_selected;

    private String url;

    public String temp;

    final static int history_num = 10;


    @Override
    public void onCreate() {
        super.onCreate();

        categories = new ArrayList<>();

        categories.add(new CategoryItem("POLITICS"));
        categories.add(new CategoryItem("WELLNESS"));
        categories.add(new CategoryItem("ENTERTAINMENT"));
        categories.add(new CategoryItem("TRAVEL"));
        categories.add(new CategoryItem("STYLE & BEAUTY"));
        categories.add(new CategoryItem("PARENTING"));
        categories.add(new CategoryItem("HEALTHY LIVING"));
        categories.add(new CategoryItem("QUEER VOICEES"));
        categories.add(new CategoryItem("FOOD & DRINK"));
        categories.add(new CategoryItem("BUSINESS"));

        categories.add(new CategoryItem("COMEDY"));
        categories.add(new CategoryItem("SPORTS"));
        categories.add(new CategoryItem("BLACK VOICEES"));
        categories.add(new CategoryItem("HOME & LIVING"));
        categories.add(new CategoryItem("PARENTS"));
        categories.add(new CategoryItem("THE WORLDPOST"));
        categories.add(new CategoryItem("WEDDINGS"));
        categories.add(new CategoryItem("WOMEN"));
        categories.add(new CategoryItem("IMPACT"));
        categories.add(new CategoryItem("DIVORCE"));

        categories.add(new CategoryItem("CRIME"));
        categories.add(new CategoryItem("MEDIA"));
        categories.add(new CategoryItem("WEIRD NEWS"));
        categories.add(new CategoryItem("GREEN"));
        categories.add(new CategoryItem("WORLDPOST"));
        categories.add(new CategoryItem("RELIGION"));
        categories.add(new CategoryItem("STYLE"));
        categories.add(new CategoryItem("SCIENCE"));
        categories.add(new CategoryItem("WORLD NEWS"));
        categories.add(new CategoryItem("TASTE"));

        categories.add(new CategoryItem("TECH"));
        categories.add(new CategoryItem("MONEY"));
        categories.add(new CategoryItem("ARTS"));
        categories.add(new CategoryItem("FIFTY"));
        categories.add(new CategoryItem("GOOD NEWS"));
        categories.add(new CategoryItem("ARTS & CULTURE"));
        categories.add(new CategoryItem("ENVIRONMENT"));
        categories.add(new CategoryItem("COLLEGE"));
        categories.add(new CategoryItem("LATINO VOICES"));
        categories.add(new CategoryItem("CULTURE & ARTS"));

        categories.add(new CategoryItem("EDUCATION"));

        explicitly_selected = new boolean[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            explicitly_selected[i] = false;
        }

        appData = getSharedPreferences("appData", MODE_PRIVATE);

        keys = new String[history_num];
        for (int i = 0; i < history_num; i++) {
            keys[i] = "history" + i;
            Log.d("GlobalClass", "keys(" + i + ") " + keys[i]);
        }


        history_indices = new int[history_num];
        for (int i = 0; i < history_num; i++) {
            history_indices[i] = -1;
        }
    }

    /* for signing out */
    public void initialize() {
        SharedPreferences.Editor editor = appData.edit();
        editor.clear();
        editor.apply();

        for (int i = 0; i < explicitly_selected.length; i++) {
            explicitly_selected[i] = false;
        }

        for (int i = 0; i < history_num; i++) {
            history_indices[i] = -1;
        }
    }

    /* after signing in */
    public void register() {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("OLD", true);
        editor.putString("EXPLICIT_SELECTS", saveExplicitSelects());
        editor.apply();
    }

    public void setImplicit() {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("IMPLICIT", true);
        editor.apply();
    }

    public boolean getImplicit() {
        return appData.getBoolean("IMPLICIT", false);
    }

    public String getExplicitSelects() {
        return appData.getString("EXPLICIT_SELECTS", null);
    }

    public String saveExplicitSelects() {
        JSONArray jArray = new JSONArray();

        try {
            for (int i = 0; i < categories.size(); i++) {
                if (explicitly_selected[i]) {
                    JSONObject sObject = new JSONObject();
                    sObject.put("category", categories.get(i).getCategory());
                    jArray.put(sObject);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jArray.toString();
    }

    /* 해당 뉴스 기 사가 이미 저장된 기사인지 검사 */
    public boolean isInHistory(int index) {
        for (int i = 0; i < history_indices.length; i++) {
            if (history_indices[i] == index)
                return true;
        }
        return false;
    }

    public void setHistory(NewsCard data) {
//        if (isInHistory(data.getIndex())){
//            Log.d("GlobalClass", "setHistory: this one is already in History");
//            return;
//        }

        int indicator = getIndicator();
        String key = keys[indicator];
        JSONObject sObject = data.getJSONObject();

        SharedPreferences.Editor editor = appData.edit();
        editor.putString(key, sObject.toString());
        history_indices[indicator] = data.getIndex();
        Log.d("GlobalClass", "setHistory(" + indicator + "): " + sObject.toString());
        editor.apply();
        indicator = indicator + 1;
        if (indicator == history_num) {
            setImplicit(); // 30개가 저장되면 implicit으로 전환
            indicator = indicator % history_num;
        }
        setIndicator(indicator);
    }

    public String getHistory(int indicator) {
        String key = keys[indicator];
        return appData.getString(key, null);
    }

    public String getTenHistory() {
        String data = "[" + getHistory(0);

        for (int i = 1; i < history_num; i++) {
            data = data + ", " + getHistory(i);
        }

        data = data + "]";

        Log.d("GlobalClass", "getTenHistory: " + data);
        return data;
    }

    public void setIndicator(int i) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putInt("INDICATOR", i);
        editor.apply();
    }

    public int getIndicator() {
        return appData.getInt("INDICATOR", 0);
    }

    public boolean isOld() {
        return appData.getBoolean("OLD", false);
    }

    public void setSelected(int i, boolean value) {
        if (i >= 0 && i <= categories.size())
            explicitly_selected[i] = value;
    }

    public int numSelected() {
        int num = 0;

        for (int i = 0; i < explicitly_selected.length; i++) {
            if (explicitly_selected[i])
                num++;
        }

        return num;
    }

    public boolean[] getSelectedArray() {
        return explicitly_selected;
    }

    public boolean getSelected(int i) {
        return explicitly_selected[i];
    }

    public ArrayList<CategoryItem> getCategories() {
        return categories;
    }

    public CategoryItem getCategory(int i) {
        return categories.get(i);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
