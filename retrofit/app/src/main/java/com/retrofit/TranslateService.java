package com.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TranslateService {
    String api_key = "891c1008d9a84e9c90fbe6e59fd1d62b";

    @POST("/translate?api-version=3.0")
    @Headers(
            {"Ocp-Apim-Subscription-Key: "+api_key
                    ,"Content-Type: application/json; charset=UTF-8"
            ,"Ocp-Apim-Subscription-Region: westeurope"
            }
    )
    Call<ResponseTranslate[]> getTranslate(@Query("to") String to, @Body TranslateBody [] translateBodies);
}
