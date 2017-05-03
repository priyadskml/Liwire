package com.example.anusha.LiWire;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.anusha.today.R;

import android.app.ActionBar;

public class MainActivity extends TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();

        Resources ressources = getResources();
        TabHost tabHost = getTabHost();

        Intent intentWeather = new Intent().setClass(this, WeatherActivity.class);
        TabSpec tabSpecWeather = tabHost
                .newTabSpec("Weather")
                .setIndicator("", ressources.getDrawable(R.drawable.sun))
                .setContent(intentWeather);

        Intent intentNews = new Intent().setClass(this, NewsActivity.class);
        TabSpec tabSpecNews = tabHost
                .newTabSpec("News")
                .setIndicator("", ressources.getDrawable(R.drawable.news))
                .setContent(intentNews);

        Intent intentHashTags = new Intent().setClass(this, HashTags.class);
        TabSpec tabSpecHashTags = tabHost
                .newTabSpec("HashTags")
                .setIndicator("", ressources.getDrawable(R.drawable.hashtag))
                .setContent(intentHashTags);

        Intent intentAccount = new Intent().setClass(this, profile.class);
        TabSpec tabSpecAccount = tabHost
                .newTabSpec("Account")
                .setIndicator("", ressources.getDrawable(R.drawable.user))
                .setContent(intentAccount);

        // add all tabs
        tabHost.addTab(tabSpecWeather);
        tabHost.addTab(tabSpecNews);
        tabHost.addTab(tabSpecHashTags);
        tabHost.addTab(tabSpecAccount);

        //set Windows tab as default (zero based)
        tabHost.setCurrentTab(0);
    }

}
