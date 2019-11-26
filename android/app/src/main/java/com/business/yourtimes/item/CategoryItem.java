package com.business.yourtimes.item;

import com.google.gson.annotations.SerializedName;

public class CategoryItem {
    @SerializedName("category")
    private String category;

    public CategoryItem(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
