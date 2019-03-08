package com.example.chatandroidadvanced.view;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.example.chatandroidadvanced.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class MainActivityUITest {

    @Before
    public void setUp() throws Exception {}

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private String mFirstName = "Tony";
    private String mLastName = "Brendt";

    @Test
    public void testUserInputFirstName(){
        //delete contained text in firstname
        Espresso.onView(withId(R.id.btnCancelCreateUser)).perform(click());
        //input text in the edit text
        Espresso.onView(withId(R.id.textInputFirstName)).perform(typeText(mFirstName));
        //lose soft keyboard
        Espresso.closeSoftKeyboard();
        //perform button click cancel
        Espresso.onView(withId(R.id.btnCancelCreateUser)).perform(click());
        //checking the text in the textview if it is reset to empty
        Espresso.onView(withId(R.id.textInputFirstName)).check(matches(withText("")));
    }

    @Test
    public void testUserInputLastName(){
        //delete contained text in lastname
        Espresso.onView(withId(R.id.btnCancelCreateUser)).perform(click());
        //input text in the edit text
        Espresso.onView(withId(R.id.textInputLastName)).perform(typeText(mLastName));
        //lose soft keyboard
        Espresso.closeSoftKeyboard();
        //perform button click cancel
        Espresso.onView(withId(R.id.btnCancelCreateUser)).perform(click());
        //checking the text in the textview if it is reset to empty
        Espresso.onView(withId(R.id.textInputLastName)).check(matches(withText("")));
    }
}