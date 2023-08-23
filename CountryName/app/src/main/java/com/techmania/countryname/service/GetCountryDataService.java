package com.techmania.countryname.service;

import com.techmania.countryname.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetCountryDataService {
    //Retrofit Interface
    @GET("countries")
    Call<Result> getResult();
}
