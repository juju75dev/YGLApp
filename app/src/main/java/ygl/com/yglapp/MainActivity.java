package ygl.com.yglapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        ParsingUtil.getConfig(this);
       // Toast.makeText(this, ParsingUtil.getConfig(this).getName(),Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onQuizzClicked(Quizz myQuizz){

        //DO SOMETHING WITH QUIZZ CLICK
        Toast.makeText(this,myQuizz.getName(),Toast.LENGTH_SHORT).show();

    }

}
