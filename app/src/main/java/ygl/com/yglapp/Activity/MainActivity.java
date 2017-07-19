package ygl.com.yglapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import ygl.com.yglapp.Adapter.QuizzAdapter;
import ygl.com.yglapp.IdleTesting;
import ygl.com.yglapp.Injection.DaggerQuizParsingComponent;
import ygl.com.yglapp.Injection.QuizParser;
import ygl.com.yglapp.Injection.QuizParsingComponent;
import ygl.com.yglapp.Model.Candidat;
import ygl.com.yglapp.Model.OnQuizzGroupClicked;
import ygl.com.yglapp.Model.QuizResult;
import ygl.com.yglapp.Model.QuizzGroup;
import ygl.com.yglapp.R;

public class MainActivity extends AppCompatActivity implements OnQuizzGroupClicked {

    @BindView(R.id.recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.image_view_loader)
    ImageView logoView;

    private LinearLayoutManager recyclerViewManager;
    private String TAG = "firebasssse";
    private ArrayList<QuizzGroup> listQuizGroup;
    private ArrayList<QuizzGroup> checkedListQuizGroup;
    private Candidat candidat;
    private AnimationDrawable frameAnimation;


    @Inject
    QuizParser firebaseParser;
    @Inject
    QuizzAdapter adapterQuizz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Load the ImageView that will host the animation and
        // set its background to our AnimationDrawable XML resource.
        logoView.setBackgroundResource(R.drawable.animation_logo);

        // Get the background, which has been compiled to an AnimationDrawable object.
        frameAnimation = (AnimationDrawable) logoView.getBackground();

        // Start the animation (looped playback by default).
        frameAnimation.start();

        Bundle bundle = getIntent().getExtras();
        candidat = (Candidat) bundle.getSerializable("candidat");

        QuizParsingComponent component = DaggerQuizParsingComponent.builder().build();
        component.inject(this);

        getSupportActionBar().setTitle(getString(R.string.quiz_selection));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refQuiz = database.getReference("Quiz");

        myRecyclerView.setAdapter(adapterQuizz);

        // Read from the database
        refQuiz.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //quizzProgress.setVisibility(View.GONE);
                frameAnimation.stop();
                logoView.setVisibility(View.GONE);
                listQuizGroup = firebaseParser.quizParsing(dataSnapshot);
                adapterQuizz.setListQuizzGroup(listQuizGroup);
                adapterQuizz.setClickCallback(MainActivity.this);
                adapterQuizz.notifyDataSetChanged();
                fab.setVisibility(View.VISIBLE);


                IdleTesting.espressoIdling=false;

                /*
                QuizzAdapter adapterQuizz = new QuizzAdapter(listQuizGroup,MainActivity.this);
                myRecyclerView.setAdapter(adapterQuizz);*/

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d(TAG, "Failed to read value.", error.toException());
                //quizzProgress.setVisibility(View.GONE);
                logoView.getAnimation().cancel();
                logoView.setVisibility(View.GONE);
            }
        });

        recyclerViewManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(recyclerViewManager);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, QuizzActivity.class);
                intent.putExtra("candidat", candidat);

                checkedListQuizGroup = new ArrayList<>();

                for (QuizzGroup quizgroup : listQuizGroup) {

                    if (quizgroup.getIdcheckedQuiz() != -1) {
                        checkedListQuizGroup.add(quizgroup);
                    }
                }


                if (checkedListQuizGroup.size() > 0 && checkedListQuizGroup.size() < 4) {
                    intent.putExtra("quizgroup", checkedListQuizGroup);
                    startActivity(intent);
                } else if (checkedListQuizGroup.size() > 3) {
                    Toast.makeText(MainActivity.this, "Le nombre maximum de Quiz est 3", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(MainActivity.this, "Veuillez sélectionner un niveau", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    private void sendResultsToFirebase(ArrayList<QuizResult> listResults) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        for (int i = 0; i < listResults.size(); i++) {

            String postId = mDatabase.push().getKey();
            mDatabase.child("Historic").child(postId).setValue(listResults.get(i));

        }

    }

    @Override
    public void onQuizzGroupClicked(QuizzGroup myQuizzGroup) {

        //Toast.makeText(MainActivity.this, "Nbre Quiz : " + myQuizzGroup.getListQuiz().size(), Toast.LENGTH_SHORT).show();
        //Intent intent =new Intent(this,QuizzActivity.class);
        //intent.putExtra("quiz",myQuizzGroup);
        //startActivity(intent);

    }

}
