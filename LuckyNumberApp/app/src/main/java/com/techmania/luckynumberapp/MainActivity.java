package com.techmania.luckynumberapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editText.getText().toString().trim();

                if(userName.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please enter your name.",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                }
            }
        });
    }
}