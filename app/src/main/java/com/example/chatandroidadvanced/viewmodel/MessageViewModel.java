package com.example.chatandroidadvanced.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;


import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.MessageRepository;


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


    public LiveData<List<Message>> getAllMessagesbyID(int recID) {return mRepository.getAllMessagesID(recID);}

    public LiveData<List<Message>> getAllMessagesByReciverID(int recID) {return mRepository.getAllMessagesByReciverID(recID);}


    public LiveData<List<Message>> getAllMessagesbyRecSendID(int recID, int sendID) {return mRepository.getAllMessagesRecSendID(recID,sendID);}

public List<Message> getMessagefirst(int recID) {return mRepository.getMessageFirst(recID);}

    public long insert(Message message){
      return  mRepository.insert(message);}


    public void deleteAll(){
        mRepository.deleteAll();}

    public void deleteMessage(Message message){
        mRepository.deleteWord(message);}

    public void update(Message message) {
        mRepository.update(message);
    }
}
