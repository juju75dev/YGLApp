package ygl.com.yglapp.Adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ygl.com.yglapp.Model.OnQuizzGroupClicked;
import ygl.com.yglapp.Model.QuizzGroup;
import ygl.com.yglapp.QuestionHolder;
import ygl.com.yglapp.R;

/**
 * Created by juju on 06/06/2017.
 */

public class QuizzAdapter extends RecyclerView.Adapter<QuestionHolder> {

    private ArrayList<QuizzGroup> listQuizzGroup;
    private OnQuizzGroupClicked clickCallback;

    public QuizzAdapter(ArrayList<QuizzGroup> list, OnQuizzGroupClicked callback) {
        this.listQuizzGroup = list;
        this.clickCallback=callback;
    }

    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_quizz,viewGroup,false);
        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionHolder myViewHolder, int position) {

        final QuizzGroup quizzGroup = listQuizzGroup.get(position);
        
        TextView titleView = (TextView) myViewHolder.itemView.findViewById(R.id.quizz_name_view);
        TextView descView = (TextView) myViewHolder.itemView.findViewById(R.id.quizz_description_view);
        ImageView imageView = (ImageView) myViewHolder.itemView.findViewById(R.id.image_cell);

        titleView.setText(quizzGroup.getName());

        // Dans le dur pour le moment (Seulement android et java
        if(quizzGroup.getName().toLowerCase().contains("android")){

            Glide.with(imageView.getContext()).load("").placeholder(ContextCompat.
                    getDrawable(imageView.getContext(),R.drawable.android_logo)).into(imageView);

        }else{

            Glide.with(imageView.getContext()).load("").placeholder(ContextCompat.
                    getDrawable(imageView.getContext(),R.drawable.java_logo)).into(imageView);
        }

        /****/

/*
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            descView.setText(Html.fromHtml(descText,Html.FROM_HTML_MODE_LEGACY));
        } else {
            descView.setText(Html.fromHtml(descText), TextView.BufferType.SPANNABLE);
        }*/


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallback.onQuizzGroupClicked(quizzGroup);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listQuizzGroup.size();
    }



}