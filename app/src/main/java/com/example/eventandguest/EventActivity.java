package com.example.eventandguest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

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
}
