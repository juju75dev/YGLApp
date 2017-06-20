package ygl.com.yglapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SET DAGGER2
        Intent intent = new Intent(this, LoginActivityExample.class);
        startActivity(intent);
        finish();
    }
}
