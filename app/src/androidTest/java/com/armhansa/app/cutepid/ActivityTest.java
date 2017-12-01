package com.armhansa.app.cutepid;


import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.armhansa.app.cutepid.controller.LoginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActivityTest {


    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void likeAndMatchTest() {
        SystemClock.sleep(1000);

        // Delete firebase data
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.child("2031094127128098").child("myUserChatter").removeValue();
        mDatabase.child("2031094127128098").child("myUserFelt").removeValue();
        mDatabase.child("0909828682").child("myUserChatter").removeValue();

        onView(allOf(withId(R.id.facebookBtn))).perform(click());

        onView(allOf(childAtPosition(childAtPosition(withId(R.id.tabs), 0), 1)))
                .perform(click());
        for(int i=0; i<7; i++) {
            onView(allOf(withId(R.id.like))).perform(click());
            SystemClock.sleep(2000);
        }
        onView(allOf(childAtPosition(childAtPosition(withId(R.id.tabs), 0), 0)))
                .perform(click());

        onView(allOf(withId(R.id.logoutBtn))).perform(click());

    }

    @Test
    public void disLikeTest() {
        SystemClock.sleep(1000);

        // Delete firebase data
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.child("2031094127128098").child("myUserChatter").removeValue();
        mDatabase.child("2031094127128098").child("myUserFelt").removeValue();
        mDatabase.child("0909828682").child("myUserChatter").removeValue();

        onView(allOf(withId(R.id.facebookBtn))).perform(click());

        onView(allOf(childAtPosition(childAtPosition(withId(R.id.tabs), 0), 1)))
                .perform(click());
        onView(allOf(withId(R.id.dislike))).perform(click());
        onView(allOf(childAtPosition(childAtPosition(withId(R.id.tabs), 0), 0)))
                .perform(click());

        onView(allOf(withId(R.id.logoutBtn))).perform(click());

    }

    @Test
    public void registerWomenUserTest() {
        SystemClock.sleep(1000);

        // Remove 0999999999 from Database to register
        FirebaseDatabase.getInstance().getReference().child("users").child("0999999999")
                .removeValue();
        onView(allOf(withId(R.id.loginBtn), withText("Login"))).perform(click());
        onView(allOf(withId(R.id.phone))).perform(replaceText("0999999999")
                , closeSoftKeyboard());
        onView(allOf(withId(R.id.phoneLogin))).perform(click());
        onView(allOf(withId(R.id.firstName))).perform(replaceText("Test")
                , closeSoftKeyboard());
        onView(allOf(withId(R.id.nextBtn))).perform(click());
        onView(allOf(withClassName(is("android.support.v7.widget.AppCompatTextView"))
                , childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0)))
                .perform(click());
        onData(anything())
                .inAdapterView(allOf(withClassName(is("android.widget.YearPickerView"))))
                .atPosition(80)
                .perform(scrollTo(), click());
        onView(allOf(withId(R.id.nextBtn))).perform(click());

        onView(allOf(withId(R.id.women))).perform(click());
        onView(allOf(withId(R.id.nextBtn))).perform(click());

        onView(allOf(withId(R.id.passwordInput)))
                .perform(replaceText("12345678"), closeSoftKeyboard());
        onView(allOf(withId(R.id.phoneLoginBtn))).perform(click());
        onView(allOf(withId(R.id.logoutBtn))).perform(click());

    }

    @Test
    public void registerMenUserWithWrongTest() {
        SystemClock.sleep(1000);

        // Remove 0999999999 from Database to register
        FirebaseDatabase.getInstance().getReference().child("users").child("0999999999")
                .removeValue();

        onView(allOf(withId(R.id.loginBtn), withText("Login"))).perform(click());

        onView(allOf(withId(R.id.phoneLogin))).perform(click());
        onView(allOf(withId(R.id.phone))).perform(replaceText("0999999999")
                , closeSoftKeyboard());
        onView(allOf(withId(R.id.phoneLogin))).perform(click());

        onView(allOf(withId(R.id.nextBtn))).perform(click());
        onView(allOf(withId(R.id.firstName))).perform(replaceText("Test")
                , closeSoftKeyboard());
        onView(allOf(withId(R.id.nextBtn))).perform(click());

        onView(allOf(withId(R.id.nextBtn))).perform(click());
        onView(allOf(withClassName(is("android.support.v7.widget.AppCompatTextView"))
                , childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0),
                        0)))
                .perform(click());
        onData(anything())
                .inAdapterView(allOf(withClassName(is("android.widget.YearPickerView"))))
                .atPosition(80)
                .perform(scrollTo(), click());
        onView(allOf(withId(R.id.nextBtn))).perform(click());

        onView(allOf(withId(R.id.women))).perform(click());
        onView(allOf(withId(R.id.men))).perform(click());
        onView(allOf(withId(R.id.nextBtn))).perform(click());

        onView(allOf(withId(R.id.phoneLoginBtn))).perform(click());
        onView(allOf(withId(R.id.passwordInput)))
                .perform(replaceText("12345678"), closeSoftKeyboard());
        onView(allOf(withId(R.id.phoneLoginBtn))).perform(click());
        onView(allOf(withId(R.id.logoutBtn))).perform(click());

    }

    @Test
    public void phoneLoginTest() {
        SystemClock.sleep(1000);

        onView(allOf(withId(R.id.loginBtn))).perform(click());
        onView(allOf(withId(R.id.phone))).perform(replaceText("0911111111"), closeSoftKeyboard());
        onView(allOf(withId(R.id.phoneLogin))).perform(click());
        onView(allOf(withId(R.id.passwordInput))).perform(replaceText("87654321"), closeSoftKeyboard());
        onView(allOf(withId(R.id.phoneLoginBtn))).perform(click());
        onView(allOf(withId(R.id.passwordInput))).perform(replaceText("12345678"), closeSoftKeyboard());
        onView(allOf(withId(R.id.phoneLoginBtn))).perform(click());
        onView(allOf(withId(R.id.logoutBtn))).perform(click());

    }

    @Test
    public void facebookMenRegisterAndLoginTest() {
        SystemClock.sleep(1000);

        // Remove my database in firebase to new register
        FirebaseDatabase.getInstance().getReference().child("users").child("2031094127128098")
                .removeValue();

        onView(allOf(withId(R.id.facebookBtn))).perform(click());
        onView(allOf(withClassName(is(
                "android.support.v7.widget.AppCompatTextView")), withText("2017"),
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0),
                        0),
                isDisplayed()))
                .perform(click());
        onData(anything())
                .inAdapterView(allOf(withClassName(is("android.widget.YearPickerView"))))
                .atPosition(97)
                .perform(click());
        onView(allOf(withId(R.id.nextBtn))).perform(click());
        onView(allOf(withId(R.id.women))).perform(click());
        onView(allOf(withId(R.id.men))).perform(click());
        onView(allOf(withId(R.id.nextBtn))).perform(click());
        onView(allOf(withId(R.id.logoutBtn))).perform(click());

        SystemClock.sleep(500);

        onView(allOf(withId(R.id.facebookBtn))).perform(click());
        onView(allOf(withId(R.id.logoutBtn))).perform(click());

    }

    @Test
    public void facebookWomenRegisterAndLoginTest() {
        SystemClock.sleep(1000);

        // Remove my database in firebase to new register
        FirebaseDatabase.getInstance().getReference().child("users").child("2031094127128098")
                .removeValue();

        onView(allOf(withId(R.id.facebookBtn))).perform(click());
        onView(allOf(withClassName(is(
                "android.support.v7.widget.AppCompatTextView")), withText("2017"),
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0),
                        0),
                isDisplayed()))
                .perform(click());
        onData(anything())
                .inAdapterView(allOf(withClassName(is("android.widget.YearPickerView"))))
                .atPosition(97)
                .perform(click());
        onView(allOf(withId(R.id.nextBtn))).perform(click());
        onView(allOf(withId(R.id.women))).perform(click());
        onView(allOf(withId(R.id.nextBtn))).perform(click());
        onView(allOf(withId(R.id.logoutBtn))).perform(click());
        onView(allOf(withId(R.id.facebookBtn))).perform(click());
        onView(allOf(withId(R.id.logoutBtn))).perform(click());

    }

    @Test
    public void setFilterTest() {
        SystemClock.sleep(1000);

        onView(allOf(withId(R.id.facebookBtn))).perform(click());

        onView(allOf(withId(R.id.settingBtn))).perform(click());
        onView(allOf(withId(R.id.men))).perform(click());
        SystemClock.sleep(500);
        onView(allOf(withId(R.id.women))).perform(click());
        onView(allOf(withId(R.id.set))).perform(click());
        onView(allOf(withId(R.id.logoutBtn))).perform(click());

    }

    @Test
    public void editInfoTest() {
        SystemClock.sleep(1000);

        onView(allOf(withId(R.id.facebookBtn))).perform(click());

        onView(allOf(withId(R.id.editInfoBtn))).perform(click());
        onView(allOf(withId(R.id.firstName))).perform(click());
        onView(allOf(withId(R.id.firstName))).perform(replaceText("Ha555")
                , closeSoftKeyboard());
        onView(allOf(withId(R.id.edit))).perform(click());
        onView(allOf(withId(R.id.firstName))).perform(replaceText("Hansa")
                , closeSoftKeyboard());
        onView(allOf(withId(R.id.women))).perform(click());
        SystemClock.sleep(500);
        onView(allOf(withId(R.id.men))).perform(click());
        onView(allOf(withId(R.id.status))).perform(replaceText("ไม่โสดแล้ว")
                , closeSoftKeyboard());
        onView(allOf(withId(R.id.edit))).perform(click());

        onView(withId(R.id.logoutBtn)).perform(click());

    }

//    @Test
//    public void stateLoginTest() {
//
//        // Create Login
//        CommonSharePreference preference = new CommonSharePreference(mActivityTestRule.getActivity().getBaseContext());
//        preference.save("UserID", "2031094127128098");
//
//        SystemClock.sleep(3000);
//
//        onView(allOf(withId(R.id.logoutBtn))).perform(click());
//
//    }








    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
