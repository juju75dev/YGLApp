package ygl.com.yglapp.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import javax.inject.Inject;

import ygl.com.yglapp.Model.OnQuizzGroupClicked;
import ygl.com.yglapp.Model.QuizResult;
import ygl.com.yglapp.Model.QuizResultGroup;
import ygl.com.yglapp.Model.QuizzGroup;
import ygl.com.yglapp.QuizGroupHolder;
import ygl.com.yglapp.R;
import ygl.com.yglapp.Utlities.AppUtils;

//import com.firebase.ui.storage.images.FirebaseImageLoader;

/**
 * Created by juju on 06/06/2017.
 */

public class QuizzAdapter extends RecyclerView.Adapter<QuizGroupHolder> {

    private ArrayList<QuizzGroup> listQuizzGroup = new ArrayList<>();
    private ArrayList<QuizResult> listQuizzresult = new ArrayList<>();
    private OnQuizzGroupClicked clickCallback;
    private Context context;
    private AppCompatRadioButton radiobutton;


    public QuizzAdapter() {
    }

    @Inject
    public QuizzAdapter(ArrayList<QuizzGroup> list, OnQuizzGroupClicked callback) {
        this.listQuizzGroup = list;
        this.clickCallback = callback;
    }

    @Override
    public QuizGroupHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_quizz, viewGroup, false);
        context = viewGroup.getContext();
        return new QuizGroupHolder(view);
    }


    @Override
    public void onBindViewHolder(QuizGroupHolder myViewHolder, final int position) {

        final QuizzGroup quizzGroup = listQuizzGroup.get(position);

        final int fPosition = position;
        TextView titleView = myViewHolder.titleView;
        ImageView imageView = myViewHolder.imageView;
        CheckBox checkBox = myViewHolder.checkBox;
        final RadioGroup radioGroup = myViewHolder.radioGroup;

        for (int i = 0; i < listQuizzGroup.get(position).getListQuiz().size(); i++) {

            radiobutton = new AppCompatRadioButton(context);
            if (AppUtils.size(context) > 3)
                radiobutton.setTextSize(26);
            radiobutton.setText(listQuizzGroup.get(position).getListQuiz().get(i).getLevel());

            final int fIndex = i;

            radiobutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        listQuizzGroup.get(fPosition).setIdcheckedQuiz(fIndex);

                    }
                }
            });

            //listQuizzGroup.get(position).setIdcheckedQuiz(i);
            final int finalI = i;
            radiobutton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (listQuizzresult.size() != 0) {
                        listQuizzresult.get(finalI).setSubject(listQuizzGroup.get(position).getName());
                        listQuizzresult.get(finalI).setLevel(listQuizzGroup.get(position).getListQuiz().get(finalI).getLevel());
                    }
                    QuizResultGroup.getInstance().setQuizResults(listQuizzresult);
                }
            });

            radioGroup.addView(radiobutton);

        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioGroup.setVisibility(View.VISIBLE);
                    quizzGroup.setChecked(true);
                    //  listQuizzresult.add(position, QuizResult.getInstance());

                } else {
                    radioGroup.setVisibility(View.GONE);
                    listQuizzGroup.get(fPosition).setIdcheckedQuiz(-1);
                    radioGroup.clearCheck();
                    quizzGroup.setChecked(false);
                    //  listQuizzresult.remove(position);

                }
            }
        });

        titleView.setText(quizzGroup.getName());

        StorageReference imageRef = FirebaseStorage.getInstance().
                getReferenceFromUrl("gs://test-mail-f32c4.appspot.com").
                child("/" + quizzGroup.getName() + ".png");

        Glide.with(imageView.getContext())
                .using(new FirebaseImageLoader())
                .load(imageRef)
                .into(imageView);


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("listQuizGroup", quizzGroup.toString());
                clickCallback.onQuizzGroupClicked(quizzGroup);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listQuizzGroup.size();
    }

    public OnQuizzGroupClicked getClickCallback() {
        return clickCallback;
    }

    public void setClickCallback(OnQuizzGroupClicked clickCallback) {
        this.clickCallback = clickCallback;
    }

    public ArrayList<QuizzGroup> getListQuizzGroup() {
        return listQuizzGroup;
    }

    public void setListQuizzGroup(ArrayList<QuizzGroup> listQuizzGroup) {
        this.listQuizzGroup = listQuizzGroup;
    }
}