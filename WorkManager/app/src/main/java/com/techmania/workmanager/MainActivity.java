package com.techmania.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /* Sending and Receiving data using WorkManager*/
    public static final String KEY_COUNTER_VALUE = "key_count";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Once the work is defined,it must be scheduled with the WorkManager service in order to run.
            1- You can schedule it to run periodically over an interval time
            2- or you can schedule it to run only one time
            Always use a WorkRequest.
            A Worker defines the unit of work.
            A WorkRequest defines how and when it should run.
         */

        //Working with constraints
        //Running the work under certain conditions : device is connected to WiFi, or device is charging...
        Constraints constraints = new Constraints.Builder().setRequiresCharging(true).build();

        //Data Creation
        Data data = new Data.Builder().putInt(KEY_COUNTER_VALUE,200).build();

        //定義 WorkRequest
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(DemoWorker.class)
                .setConstraints(constraints)
                .setInputData(data).build();

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加入任務隊列
                WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
            }
        });

        //Display WorkStatus
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(workRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null) {
                            Toast.makeText(getApplicationContext(),
                                    "Status : " + workInfo.getState().name(),
                                    Toast.LENGTH_SHORT).show();

                            if(workInfo.getState().isFinished()) {
                                Data data1 = workInfo.getOutputData();
                                String msg = data1.getString(DemoWorker.KEY_WORKER);
                                Toast.makeText(getApplicationContext(),
                                        ""+msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}