package com.example.chatandroidadvanced.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Telephony;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.ConversationService;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.MessageService;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantService;
import com.example.chatandroidadvanced.view.ConversationActivity;
import com.example.chatandroidadvanced.view.MainActivity;

import java.io.IOException;
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

   /* private final MessageService messageService;
    private final ConversationService conversationService;*/

    public RetrofitInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.201:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        participantService = retrofit.create(ParticipantService.class);
        mConversationService = retrofit.create(ConversationService.class);
        mMessageService = retrofit.create(MessageService.class);
    }

    public ParticipantService getParticipantService() {
        return participantService;
    }

  /*  public MessageService getMessageService() {
        return messageService;
    }

    public ConversationService getConversationService() {
        return conversationService;
    }*/

    //delete user from db
    public void deleteParticipant(Integer participantId) {
        Call<Participant> call = participantService.deleteParticipant(participantId);
        call.enqueue(new Callback<Participant>() {
            @Override
            public void onResponse(Call<Participant> call, Response<Participant> response) {
                if (!response.isSuccessful()) {
                    Log.d("delete participant not successfull", String.valueOf(response.code()));
                    return;
                }
            }

            @Override
            public void onFailure(Call<Participant> call, Throwable t) {
                Log.d("delete participant not successfull", t.toString());
            }
        });
    }

    /*public Participant createParticipantSynchron(Participant participant){
        Call<Participant> call = participantService.createParticipant(participant);

        try {
            Response<Participant> response = call.execute();
            if(response.isSuccessful()){
                Log.d("create participant synchron success", response.body().getfirstName());
                return response.body();
            } else {
                Log.d("create participant synchron no success", "");
            }
        } catch (IOException e) {
            Log.d("create participant synchron no success", "");
            e.printStackTrace();
        }
        return null;
    }*/

    public void createParticipant(Participant participant, final Context context, final boolean createSharedPreference) {
        Call<Participant> call = participantService.createParticipant(participant);
        call.enqueue(new Callback<Participant>() {
            @Override
            public void onResponse(Call<Participant> call, Response<Participant> response) {
                if (!response.isSuccessful()) {
                    Log.d("create participant not successfull", String.valueOf(response.code()));
                    return;
                }

                //save current user data in shared preferences if true
                if (createSharedPreference) {
                    SharedPreferences.Editor sharedEditor = context.getSharedPreferences(MainActivity.MY_PREFERENCES, MODE_PRIVATE).edit();
                    sharedEditor.putString(MainActivity.FIRSTNAME, response.body().mfirstName);
                    sharedEditor.putString(MainActivity.LASTNAME, response.body().mlastName);
                    sharedEditor.putString(MainActivity.EMAIL, response.body().mEmail);
                    sharedEditor.putString(MainActivity.ID, response.body().getIDServer());
                    sharedEditor.apply();
                }
            }

            @Override
            public void onFailure(Call<Participant> call, Throwable t) {
                Log.d("create participant not successfull", t.toString());
                //Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

//-------------------------------------

    public void getAllParticipants(final Context context, final ParticipantViewModel mParticipantViewModel) {
        Call<List<Participant>> call = participantService.getAllParticipants();
        call.enqueue(new Callback<List<Participant>>() {
            @Override
            public void onResponse(Call<List<Participant>> call, Response<List<Participant>> response) {
                if (!response.isSuccessful()) {
                    Log.d("get participants not successfull", String.valueOf(response.code()));
                    return;
                }

                //todo is there a better solution than to add every element from online service each time?
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

            /*    //todo is there a better solution than to add every element from online service each time?
                SharedPreferences preferences = context.getSharedPreferences(MainActivity.MY_PREFERENCES, MODE_PRIVATE);*/

                List<Conversation> posts = response.body();
                for (Conversation conversation : posts) {
                    //only insert element in room from db if it is not the current user
                    //if (!preferences.getString(MainActivity.ID, "").equals(participant.getIDServer())) {
                        mConversationViewModel.insert(conversation);
                   // }
                }
            }

            @Override
            public void onFailure(Call<List<Conversation>> call, Throwable t) {
                Log.d("get participants failed", t.toString());
            }
        });
    }

    public void deleteConversation(Integer conversationId) {
        Call<Conversation> call = mConversationService.deleteConversation(conversationId);
        call.enqueue(new Callback<Conversation>() {
            @Override
            public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                if (!response.isSuccessful()) {
                    Log.d("delete participant not successfull", String.valueOf(response.code()));
                    return;
                }
            }

            @Override
            public void onFailure(Call<Conversation> call, Throwable t) {
                Log.d("delete participant not successfull", t.toString());
            }
        });
    }

//----------------------Nacher
    public void getAllMessagesByReciverId(final Context context, final MessageViewModel mMessageViewModel, int reciverId, final String convId) {
        Call<List<Message>> call = mMessageService.getAllMessagesbyreceiverID(reciverId);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (!response.isSuccessful()) {
                    Log.d("get participants not successfull", String.valueOf(response.code()));
                    return;
                }

            /*    //todo is there a better solution than to add every element from online service each time?
                SharedPreferences preferences = context.getSharedPreferences(MainActivity.MY_PREFERENCES, MODE_PRIVATE);*/

                List<Message> posts = response.body();
                for (Message message : posts) {
                    if(convId.equals(message.getConversationId()))
                    //only insert element in room from db if it is not the current user
                    //if (!preferences.getString(MainActivity.ID, "").equals(participant.getIDServer())) {
                    mMessageViewModel.insert(message);
                    // }
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.d("get Message failed", t.toString());
            }
        });
    }


    public void getAllMessages(final Context context, final MessageViewModel mMessageViewModel) {
        Call<List<Message>> call = mMessageService.getAllMessages();
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (!response.isSuccessful()) {
                    Log.d("get participants not successfull", String.valueOf(response.code()));
                    return;
                }

            /*    //todo is there a better solution than to add every element from online service each time?
                SharedPreferences preferences = context.getSharedPreferences(MainActivity.MY_PREFERENCES, MODE_PRIVATE);*/

                List<Message> posts = response.body();
                for (Message message : posts) {
                    //only insert element in room from db if it is not the current user
                    //if (!preferences.getString(MainActivity.ID, "").equals(participant.getIDServer())) {
                    mMessageViewModel.insert(message);
                    // }
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.d("get Message failed", t.toString());
            }
        });
    }


    public ConversationService getConversationService() {
        return mConversationService;
    }

    public MessageService getMessageService() {
        return mMessageService;
    }

}
