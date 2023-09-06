package com.techmania.quizapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.techmania.quizapp.model.QuestionList;
import com.techmania.quizapp.retrofit.QuestionsAPI;
import com.techmania.quizapp.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizRepository {
    private QuestionsAPI questionsAPI;

    public QuizRepository() {
        questionsAPI = new RetrofitInstance().getRetrofitInstance().create(QuestionsAPI.class);
    }

    public LiveData<QuestionList> getQuestionFromAPI() {
        MutableLiveData<QuestionList> data = new MutableLiveData<>();

        Call<QuestionList> response = questionsAPI.getQuestions();
        response.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {
                //saving data to the list
                QuestionList list = response.body();
                data.postValue(list);
                Log.i("TAG",""+data.getValue());
            }

            @Override
            public void onFailure(Call<QuestionList> call, Throwable t) {

            }
        });
        return data;
    }
}
