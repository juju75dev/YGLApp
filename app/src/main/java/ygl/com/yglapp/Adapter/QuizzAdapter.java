package ygl.com.yglapp.Adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ygl.com.yglapp.Model.OnQuizzClicked;
import ygl.com.yglapp.Model.Quizz;
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
        ImageView imageView = (ImageView) myViewHolder.itemView.findViewById(R.id.image_cell);

        titleView.setText(quizz.getName());

        String descText = "<font color='#ed9e02'>"+quizz.getDescription()+"</font>"+"<br><br>"+
                "- Nombre de questions : "+
                quizz.getQuestions().size()+"<br><br>- Durée : "+quizz.getDuration()+"min";

        // Dans le dur pour le moment (Seulement android et java
        if(quizz.getName().toLowerCase().contains("android")){

            Glide.with(imageView.getContext()).load("").placeholder(ContextCompat.
                    getDrawable(imageView.getContext(),R.drawable.android_logo)).into(imageView);

        }else{

            Glide.with(imageView.getContext()).load("").placeholder(ContextCompat.
                    getDrawable(imageView.getContext(),R.drawable.java_logo)).into(imageView);
        }

        /****/


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            descView.setText(Html.fromHtml(descText,Html.FROM_HTML_MODE_LEGACY));
        } else {
            descView.setText(Html.fromHtml(descText), TextView.BufferType.SPANNABLE);
        }


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