package ygl.com.yglapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.GlobalBus;
import ygl.com.yglapp.Model.MyEventBus;
import ygl.com.yglapp.R;

public class LoginActivityExample extends AppCompatActivity {

    @BindView(R.id.sign_in_button)
    Button signInButton;
    @BindView(R.id.password)
    EditText editPassword;
    @BindView(R.id.pseudo)
    EditText editMail;
    @BindView(R.id.login_form)
    LinearLayout loginForm;
    @BindView(R.id.login_progress)
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_layout_example);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)LoginActivityExample.this.
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editPassword.getWindowToken(), 0);

                if (!editPassword.getText().toString().equals("")  && !editMail.getText().toString().equals("")) {

                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(editMail.getText().toString()
                            , editPassword.getText().toString())
                            .addOnCompleteListener(LoginActivityExample.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        GlobalBus.getBus().post(new MyEventBus.LoginSuccessMessage());
                                        loginForm.setVisibility(View.GONE);
                                        Log.d("TAG", "signInWithEmail:success");
                                        //FirebaseUser user = mAuth.getCurrentUser();
                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivityExample.this, getString(R.string.auth_failed),
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                }
                            });
                }else{

                    //Toast.makeText(LoginActivityExample.this,getString(R.string.fill_all_input),
                      //      Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivityExample.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        });

    }


}
