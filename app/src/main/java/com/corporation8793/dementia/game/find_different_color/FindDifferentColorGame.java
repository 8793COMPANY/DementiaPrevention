package com.corporation8793.dementia.game.find_different_color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.corporation8793.dementia.R;

import java.util.Arrays;
import java.util.Random;

public class FindDifferentColorGame extends AppCompatActivity {

    int different_color = R.color.blue_00baff;
    int same_color = R.color.blue_5ad3ff;

    TextView game_explain_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_different_color);

        game_explain_text = findViewById(R.id.game_explain_text);
        GridLayout color_list = findViewById(R.id.color_list);
        ConstraintLayout parent_container2 = (ConstraintLayout) color_list.getChildAt(0);

        Random randomInt = new Random();
        int randomIndex = randomInt.nextInt(9);

        int childCount = parent_container2.getChildCount();
        Log.e("childCount",childCount+"");
        for (int i= 0; i < childCount; i++){

            Button container = (Button) parent_container2.getChildAt(i);
            container.setTag(i);

            if (i == randomIndex){
                container.setBackgroundTintList(getResources().getColorStateList(different_color));
            }else {
                container.setBackgroundTintList(getResources().getColorStateList(same_color));
            }


            container.setOnClickListener(v->{
                if (Integer.parseInt(container.getTag().toString()) == randomIndex){
                    game_explain_text.setText("정답!");
                }else {
                    game_explain_text.setText("ㅠㅠ");
                }
            });
        }
    }
}