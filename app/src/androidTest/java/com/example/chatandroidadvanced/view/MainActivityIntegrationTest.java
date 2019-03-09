package com.example.chatandroidadvanced.view;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import net.bytebuddy.matcher.CollectionOneToOneMatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class MainActivityIntegrationTest {

    private MainActivity mMainActivity = mock(MainActivity.class);

    private RetrofitInstance mRetrofitInstance;// = mock(RetrofitInstance.class);

    private Context mContext;

    private View mView;

    @Before
    public void setUp() throws Exception {
        mContext = InstrumentationRegistry.getContext();
        View rootView = ((Activity)mContext).getWindow().getDecorView().findViewById(android.R.id.content);

        mView = rootView.findViewById(R.layout.activity_main);
    }

    @Test
    public void createUserClicked() {
        //when createUser is clicked
        mMainActivity.createUserClicked(mView);
        verify(mMainActivity).getUserInput();
        //no valid user input is available and getParticipantService will not be called
        //verify(mRetrofitInstance, never()).getParticipantService();
    }
}