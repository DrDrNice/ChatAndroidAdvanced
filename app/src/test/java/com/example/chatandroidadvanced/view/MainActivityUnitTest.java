package com.example.chatandroidadvanced.view;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


import static org.junit.Assert.*;

//@RunWith(MockitoJUnitRunner.class)
public class MainActivityUnitTest {

    @Mock
    private MainActivity mMainActivity;// = mock(MainActivity.class);

    @Mock
    private RetrofitInstance mRetrofitInstance;// = mock(RetrofitInstance.class);

    @Mock
    private View mView;

    @Mock
    private Context mContext;

    @Before
    public void setUp() throws Exception { MockitoAnnotations.initMocks(this); }

    @Test
    public void createUserClicked() {
        //when createUser is clicked
        mMainActivity.createUserClicked(mView);
        //no valid user input is available and getParticipantService will not be called
        verify(mRetrofitInstance, never()).getParticipantService();
    }

    @Test
    public void cancelCreateUserClicked() {
        //when createUser is clicked
        mMainActivity.cancelCreateUserClicked(mView);
        //no valid user input is available and getParticipantService will not be called
        verify(mRetrofitInstance, never()).getParticipantService();
    }


}