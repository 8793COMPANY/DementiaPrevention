package com.corporation8793.dementia.game.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.databinding.ActivityQuizBinding;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    TextView quiz_number, quiz_text;
    ConstraintLayout answer1,answer2;
    ImageView answer_icon_1, answer_icon_2;
    TextView answer_text_1,answer_text_2;

    //top section
    TextView counting_rest;
    Button close_btn;
    ProgressBar time_progress;

    int quiz_size = 12;

    int current_pos =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_quiz);

        ActivityQuizBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_quiz);

        counting_rest = binding.topSection.findViewById(R.id.counting_rest);
        close_btn = binding.topSection.findViewById(R.id.close_btn);
        time_progress = binding.topSection.findViewById(R.id.time_progress);

        quiz_number = binding.quizNumber;
        quiz_text = binding.quizText;

        answer1 = binding.answer1;
        answer2 = binding.answer2;

        answer_text_1 = binding.answerText1;
        answer_text_2 = binding.answerText2;

        answer_icon_1 = binding.answerIcon1;
        answer_icon_2 = binding.answerIcon2;

        counting_rest.setText(current_pos+"/"+quiz_size);
        quiz_number.setText("Q"+current_pos+".");
        close_btn.setOnClickListener(v->{
            current_pos++;
            counting_rest.setText(current_pos+"/"+quiz_size);
            quiz_number.setText("Q"+current_pos+".");
            Random rd = new Random();
            boolean check = rd.nextBoolean();
            setCorrectBtn(check, answer_text_1, answer_icon_1);
            setCorrectBtn(!check, answer_text_2, answer_icon_2);
        });

        binding.confirmBtn.setOnClickListener(v->{

        });



        Random rd = new Random();
        boolean check = rd.nextBoolean();
        setCorrectBtn(check, answer_text_1, answer_icon_1);
        setCorrectBtn(!check, answer_text_2, answer_icon_2);
    }


    void setCorrectBtn(boolean check, TextView text, ImageView icon){
        if (check){
            text.setText("맞다");
            icon.setBackgroundResource(R.drawable.quiz_correct_icon);
        }else{
            text.setText("아니다");
            icon.setBackgroundResource(R.drawable.quiz_incorrect_icon);
        }

    }
}