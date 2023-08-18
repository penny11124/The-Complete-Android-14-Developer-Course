package com.techmania.viewmodelapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private int counter = 0;

    private MutableLiveData<Integer> countLiveData = new MutableLiveData<>();

    //When the app first launched(without LiveData)
//    public int getInitialCounter(){
//        return counter;
//    }

    //When user clicks the button(without LiveData)
//    public int getCounter(){
//        counter++;
//        return counter;
//    }

    //When the app first launched(with LiveData)
    public MutableLiveData getInitialCounter(){
        countLiveData.setValue(counter);
        return countLiveData;
    }

    //When user clicks the button(with LiveData)
    public void getCounter(){
        counter++;
        countLiveData.setValue(counter);
    }
}
