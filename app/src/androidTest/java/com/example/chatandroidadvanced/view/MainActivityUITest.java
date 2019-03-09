package com.example.chatandroidadvanced.view;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class MainActivityUITest {

    //this test only works if no user is logged in.
    //if user is logged in delete the app to run test successfull
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    MainActivity mMainActivity;

    @Before
    public void setUp() throws Exception {
        mMainActivity = activityTestRule.getActivity();
        Intents.init();
    }

    private String mFirstName = "Tony";
    private String mLastName = "Bratt";
    private String mEmail = "TonyBratt@gmx.at";

    @Test
    public void testUserInputCanceled(){
        //delete contained text in all fields
        Espresso.onView(withId(R.id.btnCancelCreateUser)).perform(click());
        //input text in all fields
        Espresso.onView(withId(R.id.textInputEmail)).perform(typeText(mEmail));
        Espresso.onView(withId(R.id.textInputFirstName)).perform(typeText(mFirstName));
        Espresso.onView(withId(R.id.textInputLastName)).perform(typeText(mLastName));
        //close soft keyboard
        Espresso.closeSoftKeyboard();
        //perform button click cancel
        Espresso.onView(withId(R.id.btnCancelCreateUser)).perform(click());
        //checking the text in the fields if it is reset to empty
        Espresso.onView(withId(R.id.textInputEmail)).check(matches(withText("")));
        Espresso.onView(withId(R.id.textInputFirstName)).check(matches(withText("")));
        Espresso.onView(withId(R.id.textInputLastName)).check(matches(withText("")));
    }
}