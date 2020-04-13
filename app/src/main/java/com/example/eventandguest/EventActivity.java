package com.example.eventandguest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class EventActivity extends AppCompatActivity {

    //private Toolbar eventToolbar;
    private ArrayList<Event> list;
    RecyclerView rv;
    final String STATE_LIST = "state_list";

    public static String EXTRA_SELECTED_VALUE = "extra_selected_value";
    public static int RESULT_CODE = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        rv = findViewById(R.id.rv_category);
        rv.setHasFixedSize(true);

        list = new ArrayList<>();

        if(savedInstanceState == null){
            list.addAll(EventData.getListData());
        }else{
            ArrayList<Event> stateList = savedInstanceState.getParcelableArrayList(STATE_LIST);
            if (stateList != null) {
                list.addAll(stateList);
            }
        }

        rv.setLayoutManager(new LinearLayoutManager(this));
        ListEventAdapter listEventAdapter = new ListEventAdapter(this);
        listEventAdapter.setListPresident(list);
        rv.setAdapter(listEventAdapter);

        listEventAdapter.setOnItemClickCallback(new ListEventAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Event event) {
                showSelectedEvent(event);
            }
        });

        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView

        tv.setLayoutParams(lp);

        // Set text to display in TextView
        tv.setText("MESSAGE FROM CODI"); // ActionBar title text
        tv.setTextColor(Color.GREEN);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(0xffffffff));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_backarticle_normal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showSelectedEvent(Event event){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SELECTED_VALUE, event.getName());
        setResult(RESULT_CODE, resultIntent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_LIST, list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setActionBarTitle (String title){
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        setMode(item.getItemId());

        return super.onOptionsItemSelected(item);
    }

    public void setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.action_list:
                break;
            case R.id.action_map:
                break;
        }
    }
}
