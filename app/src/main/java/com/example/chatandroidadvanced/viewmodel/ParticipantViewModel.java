package com.example.chatandroidadvanced.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantRepository;

import java.util.List;

public class ParticipantViewModel extends AndroidViewModel {

    private ParticipantRepository mRepository;
    private LiveData<List<Participant>> mAllParticipant;

    public ParticipantViewModel (Application application){
        super(application);
        mRepository = new ParticipantRepository(application);
        mAllParticipant = mRepository.getAllParticipants();
    }

   public LiveData<List<Participant>> getAllParticipants() {return mAllParticipant;}

    public void insert(Participant participant){
        mRepository.insert(participant);}
    public void deleteAll(){
        mRepository.deleteAll();}

    public void deleteParticipant(Participant participant){
        mRepository.deleteWord(participant);}

    public void update(Participant participant) {
        mRepository.update(participant);
    }
}
