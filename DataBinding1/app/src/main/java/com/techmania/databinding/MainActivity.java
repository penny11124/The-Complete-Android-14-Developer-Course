package com.techmania.databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.techmania.databinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandler clickHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Person p1 = new Person("Jack","Jack@gmail.com");

        //Text View
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setPerson(p1);

        //Binding the Handler
        clickHandler = new MainActivityClickHandler(this);
        activityMainBinding.setClickHandler(clickHandler);
    }

    public class MainActivityClickHandler {
        Context context;
        public MainActivityClickHandler(Context context) {
            this.context = context;
        }

        // First Button Click Handing
        public void onButton1Click(View view) {
            Toast.makeText(context,"Hello my friend.",Toast.LENGTH_SHORT).show();
        }

        // Second Button Click Handing
        public void onButton2Click(View view) {
            Toast.makeText(context,"Bye bye my friend.",Toast.LENGTH_SHORT).show();
        }
    }
}