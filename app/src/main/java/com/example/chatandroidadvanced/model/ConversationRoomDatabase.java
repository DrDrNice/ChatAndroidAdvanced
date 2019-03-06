package com.example.chatandroidadvanced.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Conversation.class}, version = 5, exportSchema = false)
public abstract class ConversationRoomDatabase extends RoomDatabase {

    public abstract ConversationDao conversationDao();
    private static ConversationRoomDatabase INSTANCE;

    static ConversationRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ConversationRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ConversationRoomDatabase.class, "conversation_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
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
      //  String[] topic = {"etopic", "CrocoTopic", "CATTopic" };


        PopulateDbAsync(ConversationRoomDatabase db) {
            mDao = db.conversationDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

         /*   if (mDao.getAnyConversation().length < 1) {
                for (int i = 0; i <= topic.length - 1; i++) {
                    Conversation conversation = new Conversation(topic[i],"3");
                    mDao.insert(conversation);
                }
            }*/
            return null;
        }
    }
}
