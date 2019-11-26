package com.business.yourtimes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.business.yourtimes.News.NewsCard;

public class NewsDatabase {
    SQLiteDatabase db;

    public NewsDatabase() {
        db = SQLiteDatabase.openOrCreateDatabase("NEWS", null);

        db.execSQL("create table if not exists NEWS (" +
                "_id INTEGER PRIMARY KEY autoincrement," +
                "CATEGORY TEXT," +
                "HEADLINE TEXT," +
                "AUTHORS TEXT," +
                "DATE TEXT," +
                "DESCRIPTION TEXT," +
                "URL TEXT" +
                ");");
    }

//    public void insertNews(NewsCard data) {
//        db.execSQL("insert into NEWS(CATEGORY, HEADLINE, AUTHORS, DATE, DESCRIPTION, URL) values (" +
//                );
//
//    }
}
