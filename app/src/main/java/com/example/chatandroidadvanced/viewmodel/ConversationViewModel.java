package com.example.chatandroidadvanced.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ConversationRepository;

import java.util.List;

public class ConversationViewModel extends AndroidViewModel {

    private ConversationRepository mRepository;
    private LiveData<List<Conversation>> mAllConversation;


    public ConversationViewModel (Application application){
        super(application);
        mRepository = new ConversationRepository(application);
        mAllConversation = mRepository.getmAllConversations();
    }

    public LiveData<List<Conversation>> getAllConversations() {return mAllConversation;}

    public void insert(Conversation conversation){
        mRepository.insert(conversation);}

    public void deleteAll(){
        mRepository.deleteAll();}

    public void deleteConversation(Conversation conversation){
        mRepository.deleteWord(conversation);}

    public void update(Conversation conversation) {
        mRepository.update(conversation);
    }
}

