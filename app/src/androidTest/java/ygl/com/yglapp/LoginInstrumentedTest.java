package ygl.com.yglapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ygl.com.yglapp.Activity.LoginActivityExample;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginInstrumentedTest {

    public LoginInstrumentedTest(){

    }

    @Rule
    public ActivityTestRule<LoginActivityExample> mActivityRule = new ActivityTestRule<>(
            LoginActivityExample.class);


    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.pseudo))
                .perform(typeText("julien.sombie@gmail.com"), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(typeText("azerty"), closeSoftKeyboard());

        onView(withId(R.id.sign_in_button)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.password))
                .check(matches(withText("azerty")));

        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        onView(withId(R.id.form_candidat_fragment))
                .check(matches(isDisplayed()));
    }
/*
    @Before
    public void registerIntentServiceIdlingResource() {
        Instrumentation instrumentation
                = InstrumentationRegistry.getInstrumentation();
        idlingResource = new IntentServiceIdlingResource(
                instrumentation.getTargetContext());
        Espresso.registerIdlingResources(idlingResource);
    }


    @After
    public void unregisterIntentServiceIdlingResource() {
        Espresso.unregisterIdlingResources(idlingResource);
    }*/
}
