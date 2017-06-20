package ygl.com.yglapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.Activity.MainActivity;
import ygl.com.yglapp.Model.MyEventBus;
import ygl.com.yglapp.Utlities.AppUtils;

/**
 * Created by juju on 09/06/2017.
 */

public class FormCandidatFragment extends Fragment {

    @BindView(R.id.prenom_candidat)
    EditText editPrenom;
    @BindView(R.id.nom_candidat)
    EditText editNom;
    @BindView(R.id.email_candidat)
    EditText editEmail;
    @BindView(R.id.validate_infos_button)
    Button validateButton;
    private View fragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.candidat_formulaire, container, false);
        ButterKnife.bind(this,fragmentView);
        fragmentView.setVisibility(View.GONE);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editPrenom.getText().toString().equals("")&&
                        !editNom.getText().toString().equals("")&&
                        !editEmail.getText().toString().equals("")){

                    validateInfos(editPrenom.getText().toString(),editNom.getText().toString(),
                            editEmail.getText().toString());

                }else{

                    Toast.makeText(getActivity(),getString(R.string.fill_all_input),Toast.LENGTH_SHORT).show();
                }

            }
        });


        this.fragmentView=fragmentView;

        return fragmentView;
    }

    private void validateInfos(String prenom, String nom, String email){

        //On enregistre les infos du candidat et on lance notre activité.
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("prenom",prenom);
        intent.putExtra("nom",nom);
        intent.putExtra("email",email);
        startActivity(intent);


    }

    private void showFragment(){

        fragmentView.setVisibility(View.VISIBLE);
        AlphaAnimation anim = AppUtils.initAlphaAnim();
        fragmentView.startAnimation(anim);

    }


    @Subscribe
    public void getMessage(MyEventBus.LoginSuccessMessage loginMessage) {

      showFragment();

    }


    @Override
    public void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }


}