package ygl.com.yglapp.Activity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import android.text.format.Formatter;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.Adapter.QuizzAdapter;
import ygl.com.yglapp.Model.OnQuizzGroupClicked;
import ygl.com.yglapp.Model.QuizzGroup;
import ygl.com.yglapp.R;
import ygl.com.yglapp.Utlities.FireBaseQuizParsing;

public class MainActivity extends AppCompatActivity implements OnQuizzGroupClicked {

    @BindView(R.id.recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private LinearLayoutManager recyclerViewManager;
    private String TAG = "firebasssse";
    private ArrayList<QuizzGroup> listQuizGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refQuiz = database.getReference("Quiz");

        final FireBaseQuizParsing firebaseParser= new FireBaseQuizParsing();

        // Read from the database
        refQuiz.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listQuizGroup= firebaseParser.quizParsing(dataSnapshot);


                QuizzAdapter adapterQuizz = new QuizzAdapter(listQuizGroup,MainActivity.this);
                myRecyclerView.setAdapter(adapterQuizz);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        recyclerViewManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(recyclerViewManager);

        final Quizz[] listQuizz = ParsingUtil.getConfig(this);

        final QuizzAdapter adapterQuizz = new QuizzAdapter(listQuizz, this);

        myRecyclerView.setAdapter(adapterQuizz);




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, QuizzActivity.class);
                //adapterQuizz.notifyDataSetChanged();
                //intent.putExtra("quiz", listQuizz[0]);
                //startActivity(intent);
            }
        });

    }

    @Override
    public void onQuizzGroupClicked(QuizzGroup myQuizzGroup){

        Toast.makeText(MainActivity.this,"Nbre Quiz : "+myQuizzGroup.getListQuiz().size(),Toast.LENGTH_SHORT).show();
        //Intent intent =new Intent(this,QuizzActivity.class);
        //intent.putExtra("quiz",myQuizzGroup);
        //startActivity(intent);

    }

}
