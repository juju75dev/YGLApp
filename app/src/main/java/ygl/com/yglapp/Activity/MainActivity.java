package ygl.com.yglapp.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.Adapter.QuizzAdapter;
import ygl.com.yglapp.Model.Quizz;
import ygl.com.yglapp.Model.OnQuizzClicked;
import ygl.com.yglapp.R;
import ygl.com.yglapp.Utlities.ParsingUtil;

public class MainActivity extends AppCompatActivity implements OnQuizzClicked {

    @BindView(R.id.recycler_view) RecyclerView myRecyclerView;
    private LinearLayoutManager recyclerViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        recyclerViewManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(recyclerViewManager);

        Quizz[] listQuizz =ParsingUtil.getConfig(this);

        QuizzAdapter adapterQuizz = new QuizzAdapter(listQuizz,this);

        myRecyclerView.setAdapter(adapterQuizz);

    }

    @Override
    public void onQuizzClicked(Quizz myQuizz){

        Intent intent =new Intent(this,QuizzActivity.class);
        intent.putExtra("quiz",myQuizz);
        startActivity(intent);

    }

}
