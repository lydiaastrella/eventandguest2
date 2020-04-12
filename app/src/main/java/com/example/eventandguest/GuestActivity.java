package com.example.eventandguest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GuestActivity extends AppCompatActivity {

    RecyclerView rv;
    private GridGuestAdapter gridGuestAdapter;

    public static String EXTRA_SELECTED_VALUE = "extra_selected_value";
    public static String  EXTRA_SELECTED_ID = "extra_selected_id";
    public static int RESULT_CODE = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        rv = findViewById(R.id.rv_guest);
        rv.setHasFixedSize(true);

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        gridGuestAdapter = new GridGuestAdapter();
        gridGuestAdapter.notifyDataSetChanged();
        GuestViewModel guestViewModel = ViewModelProviders.of(this).get(GuestViewModel.class);
        guestViewModel.setGuest();
        guestViewModel.getGuests().observe(this, getGuest);

        rv.setAdapter(gridGuestAdapter);

        gridGuestAdapter.setOnItemClickCallback(new GridGuestAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Guest data) {
                showSelectedGuest(data);
            }
        });
    }

    private Observer<ArrayList<Guest>> getGuest = new Observer<ArrayList<Guest>>() {
        @Override
        public void onChanged(ArrayList<Guest> guestItems) {
            if (guestItems != null) {
                gridGuestAdapter.setData(guestItems);
            }
        }
    };

    private void showSelectedGuest(Guest guest){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SELECTED_VALUE, guest.getFirst_name() + " " + guest.getLast_name());
        resultIntent.putExtra(EXTRA_SELECTED_ID, guest.getId());
        setResult(RESULT_CODE, resultIntent);
        finish();
    }

}
