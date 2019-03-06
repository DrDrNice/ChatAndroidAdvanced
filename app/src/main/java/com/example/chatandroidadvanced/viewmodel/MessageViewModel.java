package com.example.chatandroidadvanced.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;


import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.MessageRepository;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantRepository;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    private MessageRepository mRepository;
    private LiveData<List<Message>> mAllMessages;

    public MessageViewModel (Application application){
        super(application);
        mRepository = new MessageRepository(application);
        mAllMessages = mRepository.getmAllMessages();
    }

    public LiveData<List<Message>> getAllMessages() {return mAllMessages;}


    public LiveData<List<Message>> getAllMessagesbyID(int recID, int convID) {return mRepository.getAllMessagesID(recID,convID);}

    public LiveData<List<Message>> getAllMessagesbyRecSendID(int recID, int sendID) {return mRepository.getAllMessagesRecSendID(recID,sendID);}



    public void insert(Message message){
        mRepository.insert(message);}

    public void deleteAll(){
        mRepository.deleteAll();}

    public void deleteParticipant(Message message){
        mRepository.deleteWord(message);}

    public void update(Message message) {
        mRepository.update(message);
    }
}
