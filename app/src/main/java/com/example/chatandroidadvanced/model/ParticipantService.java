package com.example.chatandroidadvanced.model;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ParticipantService {

    @GET("/api/participants")
    Call<ResponseBody> getPosts();

    @POST("/api/participants")
    Call<Participant> createParticipant(@Body Participant participant);

    @PUT("/api/participants")
    Call<Participant> updateParticipant();

    @GET("/api/participants/count")
    Call<Participant> countParticipants();

    @DELETE("/api/participants/{id}")
    Call<Participant> deleteParticipant(@Path("id") int postId);

    @GET("/api/participants/{id}")
    Call<Participant> getParticipant(@Path("id") int postId);
}
