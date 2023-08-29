package com.techmania.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DemoWorker extends Worker {
    public static final String KEY_WORKER = "Sent";
    public DemoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    //doWork() runs asynchronously on a background thread provided by WorkManager
    @NonNull
    @Override
    public Result doWork() {
        //Do the work here : in this case, count to the passed number

        //Getting data from InputData
        Data data = getInputData();
        int countingLimit = data.getInt(MainActivity.KEY_COUNTER_VALUE,0);

        for(int i=0;i<countingLimit;i++) {
            Log.i("TAG","Count is : " + i);
        }

        //Sending data and done info
        Data dataToSend = new Data.Builder().putString(KEY_WORKER,"Task has done successfully.").build();

        return Result.success(dataToSend);
    }
}
