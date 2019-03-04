package com.example.chatandroidadvanced.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ConversationService {

    @GET("/api/conversations")
    Call<List<Conversation>> getAllConversations();

    @POST("/api/conversations")
    Call<Conversation> createConversation(@Body Conversation conversation);

    @PUT("/api/conversations")
    Call<Conversation> updateConversation();

    @GET("/api/conversations/count")
    Call<Conversation> countConversations();

    @DELETE("/api/conversations/{id}")
    Call<Conversation> deleteConversation(@Path("id") int postId);

    @GET("/api/conversations/{id}")
    Call<Conversation> getConversation(@Path("id") int postId);
}


