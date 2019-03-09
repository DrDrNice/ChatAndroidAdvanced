package com.example.chatandroidadvanced.viewmodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

public class RetrofitInstanceUnitTest {

    private RetrofitInstance retrofitInstance;

    @Before
    public void setUp() throws Exception {
        retrofitInstance = new RetrofitInstance();
    }

    @Test
    public void getParticipantService() {
        assertNotNull(retrofitInstance.getParticipantService());
    }

    @Test
    public void getMessageService() {
        assertNotNull(retrofitInstance.getMessageService());
    }

    @Test
    public void getConversationService() {
        assertNotNull(retrofitInstance.getConversationService());
    }
}