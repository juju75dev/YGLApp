package ygl.com.yglapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.R;

public class LoginActivityExample extends AppCompatActivity {

    @BindView(R.id.sign_in_button)
    Button signInButton;
    @BindView(R.id.password)
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_layout_example);
        ButterKnife.bind(this);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editPassword.getText().toString().equals(getString(R.string.default_pwd))){

                    Intent intent = new Intent(LoginActivityExample.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else{

                    Toast.makeText(LoginActivityExample.this,getString(R.string.wrong_pseudo_or_pwd),Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

}
