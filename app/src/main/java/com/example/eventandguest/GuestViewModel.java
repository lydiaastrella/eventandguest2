package com.example.eventandguest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class GuestViewModel extends ViewModel {
    private final ArrayList<Guest> listItems = new ArrayList<>();
    private MutableLiveData<ArrayList<Guest>> listGuests = new MutableLiveData<>();
    private MutableLiveData<Guest> guestItem = new MutableLiveData<>();

    public void setGuest() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://reqres.in/api/users";
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("data");
                    for (int i=0; i<list.length(); i++){
                        JSONObject guest = list.getJSONObject(i);
                        Guest guestItem = new Guest(guest);
                        listItems.add(guestItem);
                    }
                    listGuests.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Error read detail data", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    public void addGuest(int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://reqres.in/api/users?page=" + String.valueOf(page) + "&per_page=6";
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("data");
                    for (int i=0; i<list.length(); i++){
                        JSONObject guest = list.getJSONObject(i);
                        Guest guestItem = new Guest(guest);
                        listItems.add(guestItem);
                        Log.d ("guestItem", guestItem.getFirst_name());
                        Log.d("length arraylist", String.valueOf(listItems.size()));
                    }
                    Log.d("length arraylist", String.valueOf(listItems.size()));
                    listGuests.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Error read detail data", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    public LiveData<ArrayList<Guest>> getGuests() {
        return listGuests;
    }

    public MutableLiveData<Guest> getGuestItem(){
        return guestItem;
    }

}
