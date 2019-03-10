package com.example.chatandroidadvanced.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantService;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import net.bytebuddy.matcher.CollectionOneToOneMatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

//This test only works with internet connection and running server
@RunWith(AndroidJUnit4.class)
public class MainActivityIntegrationTest {

    private MainActivity mMainActivity = mock(MainActivity.class);

    private RetrofitInstance mRetrofitInstance;

    @Before
    public void setUp() throws Exception {
        mRetrofitInstance = new RetrofitInstance();
    }

    @Test
    public void testCreateParticipantServerWorking() {
        final ParticipantService participantService = mRetrofitInstance.getParticipantService();
        //participant service should not be null
        assertNotNull(participantService);

        //create participant
        final Participant participant = new Participant("Test@Integration", "Test", "Integration");
        //create participant on server
        Call<Participant> call = participantService.createParticipant(participant);
        Participant part = null;
        try {
            //make synchron server call to create user
            part = call.execute().body();

            //delete created participant
            Call<Participant> callDelete = participantService.deleteParticipant(Integer.valueOf(part.getIDServer()));
            callDelete.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //test if correct user was created on server
        assertNotNull(part);
        assertEquals(part.mfirstName, participant.mfirstName);
        assertEquals(part.mEmail, participant.mEmail);
        assertNotEquals(part.mlastName, "Brinn");
    }
}