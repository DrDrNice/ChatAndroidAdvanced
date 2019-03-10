package com.example.chatandroidadvanced.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Conversation.class}, version = 42, exportSchema = false)
public abstract class ConversationRoomDatabase extends RoomDatabase {

    public abstract ConversationDao conversationDao();
    private static ConversationRoomDatabase INSTANCE;

    static ConversationRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ConversationRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ConversationRoomDatabase.class, "conversation_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sConversationRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static MessageRoomDatabase.Callback sConversationRoomDatabaseCallback = new ConversationRoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new ConversationRoomDatabase.PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final ConversationDao mDao;
        PopulateDbAsync(ConversationRoomDatabase db) {
            mDao = db.conversationDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
