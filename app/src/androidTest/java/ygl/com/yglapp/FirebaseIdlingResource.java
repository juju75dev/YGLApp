package ygl.com.yglapp;

import android.content.Context;
import android.support.test.espresso.IdlingResource;

import ygl.com.yglapp.Activity.MainActivity;

/**
 * Created by juju on 18/07/2017.
 */

public class FirebaseIdlingResource implements IdlingResource {
    private boolean isIdle;
    private MainActivity currentMainActivity;
    private ResourceCallback resourceCallback;
    private final Context context;

    public FirebaseIdlingResource(Context context) {

        this.context=context;
    }

    @Override
    public String getName() {
        return FirebaseIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) return true;

        /*
        if (getInstrumentation().getTargetContext() == null) {

            return false;

        }else{
            currentMainActivity= (MainActivity) getInstrumentation().getTargetContext();
        }*/

        isIdle = IdleTesting.espressoIdling;
        if (isIdle) {
            resourceCallback.onTransitionToIdle();
        }
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {

        this.resourceCallback = callback;

    }
/*
    @Nullable
    public static Activity getCurrentActivity() {
        final Activity[] currentActivity = {null};
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().
                        getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity[0] = resumedActivities.iterator().next();
                }
            }

        });

        return currentActivity[0];
    }*/


}

