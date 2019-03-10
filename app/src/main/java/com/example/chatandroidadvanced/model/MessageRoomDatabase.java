package com.example.chatandroidadvanced.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Message.class}, version = 42, exportSchema = false)
public abstract class MessageRoomDatabase extends RoomDatabase {

    public abstract MessageDao messageDao();
    private static MessageRoomDatabase INSTANCE;

    static MessageRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MessageRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MessageRoomDatabase.class, "message_database")
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
        PopulateDbAsync(MessageRoomDatabase db) {
            mDao = db.messageDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}