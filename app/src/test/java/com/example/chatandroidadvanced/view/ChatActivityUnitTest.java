package com.example.chatandroidadvanced.view;

import android.support.transition.Transition;
import android.view.View;

import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ChatActivityUnitTest {


    @Before
    public void setUp() throws Exception { MockitoAnnotations.initMocks(this); }

    @Mock
    private ChatActivity mChatActivity = mock(ChatActivity.class);

    @Mock
    private View mView;

    @Mock
    private RetrofitInstance mRetrofitInstance;

    @Test
    public void sendText() {
        //when sendText is clicked
        mChatActivity.sendText(mView);
        //getConversationsService is never called
        verify(mRetrofitInstance, never()).getConversationService();
    }
}