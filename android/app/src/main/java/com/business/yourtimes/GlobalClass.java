package com.business.yourtimes;


import android.app.Application;
import android.content.SharedPreferences;

import com.business.yourtimes.item.CategoryItem;

import java.util.ArrayList;

public class GlobalClass extends Application {
    private ArrayList<CategoryItem> categories;

    /* 처음 접속하는 유저인지 확인하는 전역 변수 */
    private SharedPreferences appData;

    private boolean[] explicitly_selected;

    private String url;

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
    }


    /* for signing out */
    public void initialize() {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("NEW", true);
        editor.apply();

        for (int i = 0; i < explicitly_selected.length; i++) {
            explicitly_selected[i] = false;
        }
    }

    /* after signing in */
    public void register() {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("NEW", false);
        editor.apply();
    }

    public boolean isNew() {
        return appData.getBoolean("NEW", true);
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
