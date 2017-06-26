package ygl.com.yglapp;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import ygl.com.yglapp.R;

/**
 * Created by ahmed on 26/06/2017.
 */

public class YglApplication  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/tahoma.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
