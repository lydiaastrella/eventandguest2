package com.example.eventandguest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editName;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.edit_name);
        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btn_next){
                    Intent moveWithDataIntent = new Intent(MainActivity.this, SecondActivity.class);
                    moveWithDataIntent.putExtra(SecondActivity.EXTRA_NAME, editName.getText().toString());
                    startActivity(moveWithDataIntent);
                }
            }
        });

    }
}
