package ygl.com.yglapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import org.json.JSONArray;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.Adapter.QuizzAdapter;
import ygl.com.yglapp.Model.OnQuizzClicked;
import ygl.com.yglapp.Model.Quizz;

public class MainActivity extends AppCompatActivity implements OnQuizzClicked {

    @BindView(R.id.recycler_view) RecyclerView myRecyclerView;
    private LinearLayoutManager recyclerViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerViewManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(recyclerViewManager);

        /****RECUPERATION DE LA LISTE DES QUIZZ AND SET ADAPTER****/



        /*POUR TESTER*/
        Quizz quizz = new Quizz("quizz1","desc",20,new JSONArray());
        Quizz quizz2 = new Quizz("quizz2","desc",20,new JSONArray());
        ArrayList<Quizz> listQuizz = new ArrayList<>();
        listQuizz.add(quizz);
        for(int i=0;i<20;i++){

            listQuizz.add(quizz2);
        }
        QuizzAdapter adapter = new QuizzAdapter(listQuizz,this);
        myRecyclerView.setAdapter(adapter);
        /********/

    }


    @Override
    public void onQuizzClicked(Quizz myQuizz){

        /****PARSE QUIZZ PROPERTIES AND START ACTIVITY WITH QUIZZ PROPERTIES****/

        Intent intent =new Intent(this,QuizzActivity.class);
        startActivity(intent);

    }

}
