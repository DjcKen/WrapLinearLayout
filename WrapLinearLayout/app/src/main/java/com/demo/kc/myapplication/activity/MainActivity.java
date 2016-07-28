package com.demo.kc.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.demo.kc.myapplication.R;
import com.demo.kc.myapplication.view.WrapLinearLayout;

public class MainActivity extends AppCompatActivity {

    private WrapLinearLayout mWrapLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mWrapLinearLayout = (WrapLinearLayout) findViewById(R.id.wrap_view);
        setSupportActionBar(toolbar);
        addTestData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addTestData() {
        mWrapLinearLayout.removeAllViews();
        for (int i = 0; i < 8; i++) {
            View convertView = getLayoutInflater().inflate(R.layout.layout_wrap_item, null, false);
            mWrapLinearLayout.addView(convertView);
        }
    }
}
