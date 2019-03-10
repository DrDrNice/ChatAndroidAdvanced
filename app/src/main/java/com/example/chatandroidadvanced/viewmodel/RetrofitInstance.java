package com.example.chatandroidadvanced.viewmodel;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.ConversationService;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.MessageService;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantService;
import com.example.chatandroidadvanced.view.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class RetrofitInstance {

    private final Retrofit retrofit;
    private final ParticipantService participantService;
    private final ConversationService mConversationService;
    private final MessageService mMessageService;


    public RetrofitInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.16:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        participantService = retrofit.create(ParticipantService.class);
        mConversationService = retrofit.create(ConversationService.class);
        mMessageService = retrofit.create(MessageService.class);
    }

    public ParticipantService getParticipantService() { return participantService; }

    public ConversationService getConversationService() {
        return mConversationService;
    }

    public MessageService getMessageService() {
        return mMessageService;
    }

    public void getAllParticipants(final Context context, final ParticipantViewModel mParticipantViewModel) {
        Call<List<Participant>> call = participantService.getAllParticipants();
        call.enqueue(new Callback<List<Participant>>() {
            @Override
            public void onResponse(Call<List<Participant>> call, Response<List<Participant>> response) {
                if (!response.isSuccessful()) {
                    Log.d("get participants not successfull", String.valueOf(response.code()));
                    return;
                }

                SharedPreferences preferences = context.getSharedPreferences(MainActivity.MY_PREFERENCES, MODE_PRIVATE);
                List<Participant> posts = response.body();
                for (Participant participant : posts) {
                    //only insert element in room from db if it is not the current user
                    if (!preferences.getString(MainActivity.ID, "").equals(participant.getIDServer())) {
                        mParticipantViewModel.insert(participant);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Participant>> call, Throwable t) {
                Log.d("get participants failed", t.toString());
            }
        });
    }

    public void getAllConversations(final Context context, final ConversationViewModel mConversationViewModel) {
        Call<List<Conversation>> call = mConversationService.getAllConversations();
        call.enqueue(new Callback<List<Conversation>>() {
            @Override
            public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
                if (!response.isSuccessful()) {
                    Log.d("get participants not successfull", String.valueOf(response.code()));
                    return;
                }

                List<Conversation> posts = response.body();
                for (Conversation conversation : posts) {
                    mConversationViewModel.insert(conversation);
                }
            }

            @Override
            public void onFailure(Call<List<Conversation>> call, Throwable t) {
                Log.d("get participants failed", t.toString());
            }
        });
    }

    public void getAllMessages(final Context context, final MessageViewModel mMessageViewModel, final ConversationViewModel mConversationViewModel, final String recId) {
        Call<List<Message>> call = mMessageService.getAllMessages(0, 1000);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (!response.isSuccessful()) {
                    Log.d("get participants not successfull", String.valueOf(response.code()));
                    return;
                }

                List<Message> posts = response.body();
                for (final Message message : posts) {
                    mMessageViewModel.insert(message);
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.d("get Message failed", t.toString());
            }
        });
    }
}
