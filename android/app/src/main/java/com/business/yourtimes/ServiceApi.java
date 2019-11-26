package com.business.yourtimes;

import com.business.yourtimes.News.NewsCard;
import com.business.yourtimes.item.CategoryItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    /* Explicit Method */
    @POST("/e_recommend")
    Call<ArrayList<NewsCard>> explicitMethod(@Body ArrayList<CategoryItem> data);

    /* Implicit Method */
    @POST("/i_recommend")
    Call<ArrayList<NewsCard>> implicitMethod(@Body ArrayList<NewsCard> data);
}
