package com.techmania.movieproapp.service;

import com.techmania.movieproapp.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {
    //Base URL
    //https://api.themoviedb.org/3/
    //End-Point URL
    //movie/popular?api_key=fddad814f29ca1f1be2e7a351a49f1bc
    @GET("movie/popular")
    Call<Result> getPopularMovie(@Query("api_key") String apiKey);
}
