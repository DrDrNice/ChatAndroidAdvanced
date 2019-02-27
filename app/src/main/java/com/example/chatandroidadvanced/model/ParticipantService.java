package com.example.chatandroidadvanced.model;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ParticipantService {

    @GET("/api/participants")
    Call<ResponseBody> getPosts();

    @POST("/api/participants")
    Call<ResponseBody> addUserToMessanger(@Body RequestBody requestBody);
}
