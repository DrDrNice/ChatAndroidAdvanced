package com.example.chatandroidadvanced.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Participant.class}, version = 2, exportSchema = false)
public abstract  class ParticipantRoomDatabase extends RoomDatabase {

    public abstract ParticipantDao participantDao();

    private static ParticipantRoomDatabase INSTANCE;

    static ParticipantRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ParticipantRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ParticipantRoomDatabase.class, "participant_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
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
        String[] email = {"email@e", "Croco", "CAT" };
        String[] firstname = {"Peter", "Hans", "Udo" };
        String[] lastName = {"Stach", "Stiftinger", "Stadler" };

        PopulateDbAsync(ParticipantRoomDatabase db) {
            mDao = db.participantDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (mDao.getAnyParticipant().length < 1) {
                for (int i = 0; i <= email.length - 1; i++) {
                    Participant participant = new Participant("1",email[i], firstname[i], lastName[i]);
                    mDao.insert(participant);
                }
            }
            return null;
        }
    }
}
