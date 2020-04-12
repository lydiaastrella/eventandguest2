package com.example.eventandguest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editName,editPalindrom;
    Button btnNext,btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.edit_name);
        editPalindrom = findViewById(R.id.edit_palindrom);
        btnNext = findViewById(R.id.btn_next);
        btnCheck = findViewById(R.id.btn_check_palindrom);

        btnNext.setOnClickListener(this);
        btnCheck.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                Intent moveWithDataIntent = new Intent(MainActivity.this, SecondActivity.class);
                moveWithDataIntent.putExtra(SecondActivity.EXTRA_NAME, editName.getText().toString());
                startActivity(moveWithDataIntent);
                break;
            case R.id.btn_check_palindrom:
                boolean result = isPalindrome(editPalindrom.getText().toString());
                String message;
                if (result){
                    message = getString(R.string.isPalindrome);
                }else{
                    message = getString(R.string.not_palindrome);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(message).setTitle(R.string.palindrome_checker);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }

    private boolean isPalindrome(String string){
        string = string.replace(" ","");
        int i = 0;
        int j = string.length() - 1;
        while (i<j){
            if(string.charAt(i) != string.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
