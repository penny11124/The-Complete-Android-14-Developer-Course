package com.techmania.luckynumberapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    TextView textView;
    Button buttonShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textViewNumber);
        buttonShare = findViewById(R.id.buttonShare);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");

        int number = getRandomNumber();
        textView.setText("" + number);

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData(userName,number);
            }
        });
    }

    public int getRandomNumber() {
        Random random = new Random();
        int upper_int = 1000;
        return random.nextInt(upper_int);
    }

    public void shareData(String userName, int randomNum) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_SUBJECT,userName+" got lucky today!");
        intent.putExtra(Intent.EXTRA_TEXT,"His/Her lucky number is: "+randomNum);

        startActivity(Intent.createChooser(intent,"Choose a platform"));
    }
}