package com.example.anusha.LiWire;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.anusha.LiWire.fragment.SlidingFragment;
import com.example.anusha.today.R;
//import com.ndtv.pratap.fragment.SlidingTabsColorsFragment;


public class NewsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        if(savedInstanceState==null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingFragment fragment = new SlidingFragment();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //implement your custom logic here
    }



}
