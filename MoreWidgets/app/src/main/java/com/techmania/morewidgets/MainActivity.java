package com.techmania.morewidgets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TimePicker timePicker;
    Button buttonToast,buttonDialog,buttonDate;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        buttonToast = findViewById(R.id.buttonToast);

        buttonToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentTime = "Time: " + timePicker.getHour() + " : " + timePicker.getMinute();
                Toast.makeText(getApplicationContext(),""+currentTime,Toast.LENGTH_SHORT).show();
            }
        });

        buttonDialog = findViewById(R.id.buttonDialog);
        buttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFrag = new TimePickerFragment();
                timePickerFrag.show(getSupportFragmentManager(),"Pick Time Now: ");
            }
        });

        buttonDate = findViewById(R.id.buttonDate);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(),"Pick A Date: ");
            }
        });

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(50);
//      progressBar.incrementProgressBy(10);
    }
}