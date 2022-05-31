package com.retrofit;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpaceService{
    @GET("/planetary/apod")
    Call<ResponseSpace> getSpaceInfo(@Query("api_key") String api_key);
}