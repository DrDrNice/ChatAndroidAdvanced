package com.example.chatandroidadvanced.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ConversationRepository {

    private ConversationDao mConversationDao;
    private LiveData<List<Conversation>> mAllConversations;


    public ConversationRepository(Application application) {
        ConversationRoomDatabase db = ConversationRoomDatabase.getDatabase(application);
        mConversationDao = db.conversationDao();
        mAllConversations = mConversationDao.getAllConversations();
    }

    public LiveData<List<Conversation>> getmAllConversations() {
        return mAllConversations;
    }

    public void insert(Conversation conversation) {
        new ConversationRepository.insertAsyncTask(mConversationDao).execute(conversation);
    }


    public void deleteAll() {
        new ConversationRepository.deleteAllConversationAsyncTask(mConversationDao).execute();
    }

    public void deleteWord(Conversation conversation) {
        new ConversationRepository.deleteConversationAsyncTask(mConversationDao).execute(conversation);
    }

    public void update(Conversation conversation)  {
        new ConversationRepository.updateConversationAsyncTask(mConversationDao).execute(conversation);
    }


    private static class insertAsyncTask extends AsyncTask<Conversation, Void, Void> {

        private ConversationDao mAsyncTaskDao;

        insertAsyncTask(ConversationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Conversation... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    private static class deleteAllConversationAsyncTask extends AsyncTask<Void, Void, Void> {
        private ConversationDao mAsyncTaskDao;

        deleteAllConversationAsyncTask(ConversationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteConversationAsyncTask extends AsyncTask<Conversation, Void, Void> {
        private ConversationDao mAsyncTaskDao;

        deleteConversationAsyncTask(ConversationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Conversation... conversations) {
            mAsyncTaskDao.deleteConversation(conversations[0]);
            return null;
        }
    }



    private static class updateConversationAsyncTask extends AsyncTask<Conversation, Void, Void> {
        private ConversationDao mAsyncTaskDao;

        updateConversationAsyncTask(ConversationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Conversation... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}

