package com.business.yourtimes.News;

import com.google.gson.annotations.SerializedName;

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

    public NewsCard(String category, String headline, String author, String url, String desc, String date) {
        this.category = category;
        this.headline = headline;
        this.authors = author;
        this.url = url;
        this.desc = desc;
        this.date = date;
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
}
