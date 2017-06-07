package ygl.com.yglapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ygl.com.yglapp.Model.Quizz;
import ygl.com.yglapp.OnQuizzClicked;
import ygl.com.yglapp.QuestionHolder;
import ygl.com.yglapp.R;

/**
 * Created by juju on 06/06/2017.
 */

public class QuizzAdapter extends RecyclerView.Adapter<QuestionHolder> {

    private Quizz[] listQuizz;
    private OnQuizzClicked clickCallback;

    public QuizzAdapter(Quizz[] list, OnQuizzClicked callback) {
        this.listQuizz = list;
        this.clickCallback=callback;
    }

    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_quizz,viewGroup,false);
        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionHolder myViewHolder, int position) {

        final Quizz quizz = listQuizz[position];

        TextView titleView = (TextView) myViewHolder.itemView.findViewById(R.id.quizz_name_view);
        TextView descView = (TextView) myViewHolder.itemView.findViewById(R.id.quizz_description_view);

        titleView.setText(quizz.getName());
        descView.setText(quizz.getDescription());


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallback.onQuizzClicked(quizz);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listQuizz.length;
    }



}


