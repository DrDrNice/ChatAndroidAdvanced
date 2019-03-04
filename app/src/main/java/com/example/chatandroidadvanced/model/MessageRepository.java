package com.example.chatandroidadvanced.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;


import java.util.List;

public class MessageRepository {

    private MessageDao mMessageDao;
    private LiveData<List<Message>> mAllMessages;


    public MessageRepository(Application application) {
        MessageRoomDatabase db = MessageRoomDatabase.getDatabase(application);
        mMessageDao = db.messageDao();
        mAllMessages = mMessageDao.getAllMessage();
    }

    public LiveData<List<Message>> getmAllMessages() {
        return mAllMessages;
    }

    public void insert(Message message) {
        new MessageRepository.insertAsyncTask(mMessageDao).execute(message);
    }

    public void deleteAll() {
        new MessageRepository.deleteAllMessageAsyncTask(mMessageDao).execute();
    }

    public void deleteWord(Message message) {
        new MessageRepository.deleteMessageAsyncTask(mMessageDao).execute(message);
    }

    public void update(Message message)  {
        new MessageRepository.updateMessageAsyncTask(mMessageDao).execute(message);
    }


    private static class insertAsyncTask extends AsyncTask<Message, Void, Void> {

        private MessageDao mAsyncTaskDao;

        insertAsyncTask(MessageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Message... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    private static class deleteAllMessageAsyncTask extends AsyncTask<Void, Void, Void> {
        private MessageDao mAsyncTaskDao;

        deleteAllMessageAsyncTask(MessageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteMessageAsyncTask extends AsyncTask<Message, Void, Void> {
        private MessageDao mAsyncTaskDao;

        deleteMessageAsyncTask(MessageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            mAsyncTaskDao.deleteMessage(messages[0]);
            return null;
        }
    }



    private static class updateMessageAsyncTask extends AsyncTask<Message, Void, Void> {
        private MessageDao mAsyncTaskDao;

        updateMessageAsyncTask(MessageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Message... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}

