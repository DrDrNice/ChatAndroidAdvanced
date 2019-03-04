package com.example.chatandroidadvanced.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantService;
import com.example.chatandroidadvanced.view.ConversationActivity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private final Retrofit retrofit;
    private final ParticipantService participantService;

   /* private final MessageService messageService;
    private final ConversationService conversationService;*/

    public RetrofitInstance(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.16:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        participantService = retrofit.create(ParticipantService.class);

    /*    messageService = retrofit.create(MessageService.class);

        conversationService = retrofit.create(ConversationService.class);*/
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
    public void deleteParticipant(Integer participantId){
        Call<Participant> call = participantService.deleteParticipant(participantId);
        call.enqueue(new Callback<Participant>() {
            @Override
            public void onResponse(Call<Participant> call, Response<Participant> response) {
                if(!response.isSuccessful()){
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

    /*public void createParticipant(Participant participant, final Context context){
        Call<Participant> call = participantService.createParticipant(participant);
        call.enqueue(new Callback<Participant>() {
            @Override
            public void onResponse(Call<Participant> call, Response<Participant> response) {
                if(!response.isSuccessful()){
                    Log.d("create participant not successfull", String.valueOf(response.code()));
                    Toast.makeText(context ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(context != null) {
                    //Log.d("created participant", response.body().getfirstName());
                    Toast.makeText(context, "firstName: " + response.body().getfirstName() + " lastName: " + response.body().getlastName() + " email: " + response.body().getEmail(), Toast.LENGTH_LONG).show();
                    Intent intentConversations = new Intent(context, ConversationActivity.class);
                    //todo add current participant object
                    intentConversations.putExtra("currentUser", response.body());
                    context.startActivity(intentConversations);
                    ((Activity) context).finish();
                }
            }

            @Override
            public void onFailure(Call<Participant> call, Throwable t) {
                Log.d("create participant not successfull", t.toString());
                Toast.makeText(context ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }*/

//-------------------------------------

    /*public void getAllParticipants(final Participant currentUser, List<Participant> contactList, final RecyclerView recyclerview){
        Call<List<Participant>> call = participantService.getAllParticipants();
        call.enqueue(new Callback<List<Participant>>() {
            @Override
            public void onResponse(Call<List<Participant>> call, Response<List<Participant>> response) {
                if(!response.isSuccessful()){
                    Log.d("get participants not successfull", String.valueOf(response.code()));
                    return;
                }

                List<Participant> posts = response.body();
                //Log.d("get last participant", response.body().get(posts.size()-1).getfirstName() + " " + response.body().get(posts.size()-1).getlastName());
                for (Participant participant : posts) {
                    if (!participant.getEmail().equals(currentUser.getEmail()) &&
                            !participant.getfirstName().equals(currentUser.getfirstName()) && !participant.getlastName().equals(currentUser.getlastName())) {
                        //Log.d("get participant", participant.getfirstName() + " " + participant.getlastName());
                        contactList.addLast(participant);
                    }
                }

                int wordListSize = contactList.size();
                recyclerview.getAdapter().notifyItemInserted(wordListSize);
                recyclerview.smoothScrollToPosition(wordListSize);
            }

            @Override
            public void onFailure(Call<List<Participant>> call, Throwable t) {
                Log.d("get participants failed", t.toString());
            }
        });
    }*/
}
