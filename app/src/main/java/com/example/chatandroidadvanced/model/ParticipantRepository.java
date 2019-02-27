package com.example.chatandroidadvanced.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ParticipantRepository {

    private ParticipantDao mParticipantDao;
    private LiveData<List<Participant>> mAllParticipants;


    public ParticipantRepository(Application application) {
        ParticipantRoomDatabase db = ParticipantRoomDatabase.getDatabase(application);
        mParticipantDao = db.participantDao();
        mAllParticipants = mParticipantDao.getAllParticipants();
    }

    public LiveData<List<Participant>> getAllParticipants() {
        return mAllParticipants;
    }

    public void insert(Participant participant) {
        new insertAsyncTask(mParticipantDao).execute(participant);
    }

    public void deleteAll() {
        new deleteAllWordAsyncTask(mParticipantDao).execute();
    }

    public void deleteWord(Participant participant) {
        new deleteWordAsyncTask(mParticipantDao).execute(participant);
    }

    public void update(Participant participant)  {
        new updateWordAsyncTask(mParticipantDao).execute(participant);
    }


    private static class insertAsyncTask extends AsyncTask<Participant, Void, Void> {

        private ParticipantDao mAsyncTaskDao;

        insertAsyncTask(ParticipantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Participant... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    private static class deleteAllWordAsyncTask extends AsyncTask<Void, Void, Void> {
        private ParticipantDao mAsyncTaskDao;

        deleteAllWordAsyncTask(ParticipantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteWordAsyncTask extends AsyncTask<Participant, Void, Void> {
        private ParticipantDao mAsyncTaskDao;

        deleteWordAsyncTask(ParticipantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Participant... participants) {
            mAsyncTaskDao.deleteParticipant(participants[0]);
            return null;
        }
    }



    private static class updateWordAsyncTask extends AsyncTask<Participant, Void, Void> {
        private ParticipantDao mAsyncTaskDao;

        updateWordAsyncTask(ParticipantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Participant... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
