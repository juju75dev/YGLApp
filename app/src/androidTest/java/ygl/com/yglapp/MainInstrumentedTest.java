package ygl.com.yglapp;

/**
 * Created by juju on 18/07/2017.
 */

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ygl.com.yglapp.Activity.MainActivity;
import ygl.com.yglapp.Model.Candidat;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainInstrumentedTest {

    private FirebaseIdlingResource fireIdlingResource;
    Candidat c1 = new Candidat("juju","soso","soso@so.so");
    public MainInstrumentedTest(){

    }
    @Before
    public void registerIntentServiceIdlingResource() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        fireIdlingResource = new FirebaseIdlingResource(instrumentation.getTargetContext());
        Espresso.registerIdlingResources(fireIdlingResource);
    }

    @After
    public void unregisterIntentServiceIdlingResource() {
        Espresso.unregisterIdlingResources(fireIdlingResource);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(
            MainActivity.class){
                @Override
                protected Intent getActivityIntent() {

                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent intent = new Intent(targetContext, MainActivity.class);
                    intent.putExtra("candidat", c1);
                    return intent;
                }
    };



    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
       /* Intent intent = new Intent();
        Candidat c1 = new Candidat("juju","soso","soso@so.so");
        intent.putExtra("candidat", c1);

        mActivityRule.launchActivity(intent);*/

        //myObj = MyObject.mockObject();
        /*
        Intent i = new Intent();
        i.putExtra("candidat", c1);
        mActivityRule.launchActivity(i);
*/
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }


        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));


    }


}

