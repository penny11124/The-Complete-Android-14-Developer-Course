package com.techmania.quizapp.retrofit;

import com.techmania.quizapp.model.QuestionList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface QuestionsAPI {
    @GET("questionapi.php")
    Call<QuestionList> getQuestions();

}
