package com.techmania.viewmodelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MainActivityViewModel mainActivityViewModel;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        //Get the initial count(without LiveData)
//      textView.setText("You Clicked : " + mainActivityViewModel.getInitialCounter() + " times");

        //Use LiveData to get the counter
        LiveData<Integer> count = mainActivityViewModel.getInitialCounter();
        count.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textView.setText("You Clicked : " + integer + " times");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the current count
//              textView.setText("You Clicked : " + mainActivityViewModel.getCounter() + " times");
                mainActivityViewModel.getCounter();
            }
        });
    }
}