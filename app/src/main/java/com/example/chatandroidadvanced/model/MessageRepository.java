package com.example.chatandroidadvanced.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;


import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public LiveData<List<Message>> getAllMessagesByReciverID(int recID) {
        return mMessageDao.getAllMessagebyReciverId(recID);
    }


    public LiveData<List<Message>> getAllMessagesID(int recID) {
        return mMessageDao.getAllMessagebyReciverConvId(recID);
    }

    public LiveData<List<Message>> getAllMessagesRecSendID(int recID, int sendID) {
        return mMessageDao.getAllMessagebyReciverSendId(recID, sendID);
    }

    public long insert(Message message) {
        try {
            return new insertAsyncTask(mMessageDao).execute(message).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public void deleteAll() {
        new MessageRepository.deleteAllMessageAsyncTask(mMessageDao).execute();
    }

    public void deleteWord(Message message) {
        new MessageRepository.deleteMessageAsyncTask(mMessageDao).execute(message);
    }

    public void update(Message message) {
        new MessageRepository.updateMessageAsyncTask(mMessageDao).execute(message);
    }

    public List<Message> getMessageFirst(int recId) {
        try {
            return new getMessageFirstAsyncTask(mMessageDao).execute(recId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class insertAsyncTask extends AsyncTask<Message, Void, Long> {

        private MessageDao mAsyncTaskDao;

        insertAsyncTask(MessageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Message... params) {
            return mAsyncTaskDao.insert(params[0]);
            //  return null;
        }
    }


    private static class getMessageFirstAsyncTask extends AsyncTask<Integer, Void, List<Message>> {

        private MessageDao mAsyncTaskDao;

        getMessageFirstAsyncTask(MessageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Message> doInBackground(Integer... ints) {

            return mAsyncTaskDao.getMessageFirst(ints[0]);

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


