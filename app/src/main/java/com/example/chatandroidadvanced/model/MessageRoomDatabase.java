package com.example.chatandroidadvanced.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Message.class}, version = 19, exportSchema = false)
public abstract class MessageRoomDatabase extends RoomDatabase {

    public abstract MessageDao messageDao();
    private static MessageRoomDatabase INSTANCE;

    static MessageRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MessageRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MessageRoomDatabase.class, "message_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sMessageRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static MessageRoomDatabase.Callback sMessageRoomDatabaseCallback = new MessageRoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new MessageRoomDatabase.PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MessageDao mDao;
       // String[] content = {"email@e", "Croco", "CAT" };


        PopulateDbAsync(MessageRoomDatabase db) {
            mDao = db.messageDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

          /*  if (mDao.getAnyMessage().length < 1) {
                for (int i = 0; i <= content.length - 1; i++) {
                    Message message = new Message(content[i],"3","5","");
                    mDao.insert(message);
                }
            }*/
            return null;
        }
    }
}