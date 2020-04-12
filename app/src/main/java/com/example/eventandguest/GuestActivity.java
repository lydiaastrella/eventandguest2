package com.example.eventandguest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.Objects;

public class GuestActivity extends AppCompatActivity implements View.OnClickListener, SwipyRefreshLayout.OnRefreshListener{

    EditText editPrime;
    Button btnCheck;

    RecyclerView rv;
    private GridGuestAdapter gridGuestAdapter;
    private SwipyRefreshLayout swipyRefreshLayout;
    private GuestViewModel guestViewModel;
    private int page = 1;

    public static String EXTRA_SELECTED_VALUE = "extra_selected_value";
    public static String  EXTRA_SELECTED_ID = "extra_selected_id";
    public static int RESULT_CODE = 800;
    public static int FIRST_PAGE = 1;
    public static int LAST_PAGE = 2;

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

        swipyRefreshLayout = findViewById(R.id.refresh_guest);
        swipyRefreshLayout.setOnRefreshListener(this);
        swipyRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
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
        String message;
        int month = Integer.parseInt(editPrime.getText().toString());
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
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        String str_direction = (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom");
        Log.d("GuestActivity refresh", "Refresh triggered at "
                + str_direction);
        if (str_direction.equals("top")){
            Objects.requireNonNull(rv.getAdapter()).notifyDataSetChanged();
        }else{
            if(page < LAST_PAGE) {
                Log.d("masuk", "masuk");
                guestViewModel.addGuest(page+1);
                guestViewModel.getGuests().observe(this, getGuest);
                Objects.requireNonNull(rv.getAdapter()).notifyDataSetChanged();
                page += 1;
            }
        }
        swipyRefreshLayout.setRefreshing(false);
    }
}
