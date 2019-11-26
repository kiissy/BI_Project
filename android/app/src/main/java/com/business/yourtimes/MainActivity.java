package com.business.yourtimes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.business.yourtimes.item.CategoryItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView maintext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
