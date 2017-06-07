package ygl.com.yglapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.Adapter.QuizzAdapter;
import ygl.com.yglapp.Model.Quizz;
import ygl.com.yglapp.Utlities.ParsingUtil;

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
        Quizz[] listQuizz =ParsingUtil.getConfig(this);

        QuizzAdapter adapterQuizz = new QuizzAdapter(listQuizz,this);

        myRecyclerView.setAdapter(adapterQuizz);


        /****RECUPERATION DE LA LISTE DES QUIZZ AND SET ADAPTER*****/


    }


    @Override
    public void onQuizzClicked(Quizz myQuizz){

        /****PARSE QUIZZ PROPERTIES AND START ACTIVITY WITH QUIZZ PROPERTIES****/


        Intent intent =new Intent(this,QuizzActivity.class);
        intent.putExtra("quiz",myQuizz);
        startActivity(intent);

    }

}
