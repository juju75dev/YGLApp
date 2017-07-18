package ygl.com.yglapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by juju on 06/06/2017.
 */

public class QuizGroupHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView titleView;
    public ImageView imageView;
    public CheckBox checkBox;
    public RadioGroup radioGroup;

    public QuizGroupHolder(View itemView) {
        super(itemView);

        this.titleView = (TextView) itemView.findViewById(R.id.quizz_name_view);
        this.imageView = (ImageView) itemView.findViewById(R.id.image_cell);
        this.checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_quiz);
        this.radioGroup = (RadioGroup)itemView.findViewById(R.id.radioGroup);

    }
}