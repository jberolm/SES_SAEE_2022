package es.unex.asee.frojomar.asee_ses.addappointment.instrumentation;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.activities.LoginActivity;
import es.unex.asee.frojomar.asee_ses.activities.appointments.AddAppointmentActivity;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddAppointmentActivityUITest {

    @Rule
    public ActivityTestRule<AddAppointmentActivity> mActivityRule =
            new ActivityTestRule<>(AddAppointmentActivity.class,
                    true,
                    false); // Activity is not launched immediately, to mock preferences previously

    //let's mock the preferences
    SharedPreferences.Editor preferencesEditor;

    @Before
    public void before() throws Exception {
        //take shared preferences, if necessary
        Context targetContext = getInstrumentation().getTargetContext();
        preferencesEditor = targetContext.getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE).edit();
    }


    @Test
    public void addAppointmentTest(){
        //load the mock values in the preferences
        preferencesEditor.putString("dni", "11111111X");
        preferencesEditor.commit();
        //load the Activity
        mActivityRule.launchActivity(new Intent());


        String testingNumber = "666666666";
        String testingDescription = "Dolor de cabeza";
        // Perform a click() action on R.id.change_tlfno_button
        onView(ViewMatchers.withId(R.id.change_tlfno_button)).perform(click());
        // Perform clearText() on R.id.tlfno_appointment
        onView(withId(R.id.tlfno_appointment)).perform(clearText());
        // Perform typeText() and closeSoftKeyboard() actions on R.id.tlfno_appointment
        onView(withId(R.id.tlfno_appointment)).perform(typeText(testingNumber), closeSoftKeyboard());
        // save the new phone number, just for this appointment.
        onView(withId(R.id.submitButton)).perform(click());

        //check that the contact phone has been modified correctly
        onView(withId(R.id.telephone_appointment)).check(matches(withText(testingNumber)));

        //write description
        onView(withId(R.id.desc_appointment)).perform(typeText(testingDescription), closeSoftKeyboard());
        //we press the button to send, but it must not let us send, for not having selected day and hour
        onView(withId(R.id.submitButton)).perform(click());
        //we press the select time button, but it tells us to select the day first
        onView(withId(R.id.time_picker_button)).perform(click());
        //Select the current day
        ViewInteraction appCompatButton2 = onView(
                Matchers.allOf(withId(R.id.date_picker_button), withText("Escoger día"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        0),
                                8),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                Matchers.allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton3.perform(scrollTo(), click());

        //select the first available time
        ViewInteraction appCompatButton4 = onView(
                Matchers.allOf(withId(R.id.time_picker_button), withText("Escoger hora"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        0),
                                9),
                        isDisplayed()));
        appCompatButton4.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(Matchers.allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0)))
                .atPosition(0);
        appCompatCheckedTextView.perform(click());

        // Request the appointment
        ViewInteraction appCompatButton5 = onView(
                Matchers.allOf(withId(R.id.submitButton), withText("Solicitar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        0),
                                12),
                        isDisplayed()));
        appCompatButton5.perform(click());

        //delete mock preference
        preferencesEditor.remove("dni");
        preferencesEditor.commit();
    }


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
