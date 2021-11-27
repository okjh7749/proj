package com.example.pet.interfaces;

import com.example.pet.dto.CategoryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("v2/local/search/keyword.json")
    Call<CategoryResult> getSearchKeyword(
            @Header("Authorization") String token,
            @Query("query") String query,
            @Query("x") String x,
            @Query("y") String y,
            @Query("radius") int radius,
            @Query("page") int page,
            @Query("sort") String sort
    );
}
