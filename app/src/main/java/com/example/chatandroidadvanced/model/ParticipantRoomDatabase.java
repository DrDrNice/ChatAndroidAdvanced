package com.example.chatandroidadvanced.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Participant.class}, version = 5, exportSchema = false)
public abstract  class ParticipantRoomDatabase extends RoomDatabase {

    public abstract ParticipantDao participantDao();

    private static ParticipantRoomDatabase INSTANCE;

    static ParticipantRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ParticipantRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ParticipantRoomDatabase.class, "participant_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sParticipantRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static ParticipantRoomDatabase.Callback sParticipantRoomDatabaseCallback = new ParticipantRoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ParticipantDao mDao;
        PopulateDbAsync(ParticipantRoomDatabase db) {
            mDao = db.participantDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
