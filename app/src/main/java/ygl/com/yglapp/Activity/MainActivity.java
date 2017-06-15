package ygl.com.yglapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.Adapter.QuizzAdapter;
import ygl.com.yglapp.Model.OnQuizzClicked;
import ygl.com.yglapp.Model.Quizz;
import ygl.com.yglapp.R;
import ygl.com.yglapp.Utlities.ParsingUtil;

public class MainActivity extends AppCompatActivity implements OnQuizzClicked {

    @BindView(R.id.recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private LinearLayoutManager recyclerViewManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        recyclerViewManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(recyclerViewManager);

        final Quizz[] listQuizz = ParsingUtil.getConfig(this);

        final QuizzAdapter adapterQuizz = new QuizzAdapter(listQuizz, this);

        myRecyclerView.setAdapter(adapterQuizz);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizzActivity.class);
                adapterQuizz.notifyDataSetChanged();
                intent.putExtra("quiz", listQuizz[0]);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onQuizzClicked(Quizz myQuizz) {

        Intent intent = new Intent(this, QuizzActivity.class);
        intent.putExtra("quiz", myQuizz);
        startActivity(intent);

    }

}
