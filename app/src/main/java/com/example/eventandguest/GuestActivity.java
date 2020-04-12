package com.example.eventandguest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Objects;

public class GuestActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    EditText editPrime;
    Button btnCheck;

    RecyclerView rv;
    private GridGuestAdapter gridGuestAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GuestViewModel guestViewModel;

    public static String EXTRA_SELECTED_VALUE = "extra_selected_value";
    public static String  EXTRA_SELECTED_ID = "extra_selected_id";
    public static int RESULT_CODE = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        editPrime = findViewById(R.id.edit_prime);
        btnCheck = findViewById(R.id.btn_check_prime);
        btnCheck.setOnClickListener(this);

        rv = findViewById(R.id.rv_guest);
        rv.setHasFixedSize(true);

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        gridGuestAdapter = new GridGuestAdapter();
        gridGuestAdapter.notifyDataSetChanged();
        guestViewModel = ViewModelProviders.of(this).get(GuestViewModel.class);
        guestViewModel.setGuest();
        guestViewModel.getGuests().observe(this, getGuest);

        rv.setAdapter(gridGuestAdapter);

        gridGuestAdapter.setOnItemClickCallback(new GridGuestAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Guest data) {
                showSelectedGuest(data);
            }
        });

        swipeRefreshLayout = findViewById(R.id.refresh_guest);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);

        swipeRefreshLayout.post(new Runnable(){

            @Override
            public void run() {
                //swipeRefreshLayout.setRefreshing(true);
                Objects.requireNonNull(rv.getAdapter()).notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_check_prime);
        int month = Integer.parseInt(editPrime.getText().toString());
        String message;
        if(month >= 1 && month <= 12) {
            boolean result = isPrime(month);
            if (result) {
                message = getString(R.string.prime_message);
            } else {
                message = getString(R.string.not_prime_message);
            }
        }else{
            message = getString(R.string.month_not_valid);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(R.string.prime_checker);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean isPrime(int month){
        if(month == 1){
            return false;
        }else if (month <=3){
            return true;
        }
        if (month % 2 == 0 || month % 3 == 0){
            return false;
        }

        int i = 5;
        while (i*i<=month){
            if(month % i == 0){
                return false;
            }
            i+=6;
        }
        return true;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        guestViewModel.setGuest();
        guestViewModel.getGuests().observe(this, getGuest);
        Objects.requireNonNull(rv.getAdapter()).notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
