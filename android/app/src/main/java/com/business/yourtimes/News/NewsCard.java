package com.business.yourtimes.News;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsCard {
    @SerializedName("category")
    private String category;

    @SerializedName("headline")
    private String headline;

    @SerializedName("authors")
    private String authors;

    @SerializedName("link")
    private String url;

    @SerializedName("short_description")
    private String desc;

    @SerializedName("date")
    private String date;

    @SerializedName("index")
    private int index;

    public NewsCard(String category, String headline, String author, String url, String desc, String date, int index) {
        this.category = category;
        this.headline = headline;
        this.authors = author;
        this.url = url;
        this.desc = desc;
        this.date = date;
        this.index = index;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthors() {
        return authors;
    }

    public String getDesc() {
        return desc;
    }

    public String getHeadline() {
        return headline;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public int getIndex() {
        return index;
    }

    public JSONObject getJSONObject() {
        JSONObject sObject = new JSONObject();

        try {
            sObject.put("category", this.getCategory());
            sObject.put("headline", this.getHeadline());
            sObject.put("authors", this.getAuthors());
            sObject.put("link", this.getUrl());
            sObject.put("short_description", this.getDesc());
            sObject.put("date", this.getDate());
            sObject.put("index", this.getIndex());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sObject;
    }
}
