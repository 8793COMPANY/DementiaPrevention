package com.corporation8793.dementia.game.find_same_thing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.databinding.ActivityFindSameThingGameBinding;
import com.corporation8793.dementia.databinding.ActivityQuizBinding;

import java.util.ArrayList;

public class FindSameThingGame extends AppCompatActivity {

    String result;
    PatternLockView patternLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_find_same_thing_game);
        ActivityFindSameThingGameBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_find_same_thing_game);


        patternLockView = findViewById(R.id.patternLockView);

        binding.patternLockView.setOnPatternListener(listener);
        binding.patternLockView.setTextView(binding.noticeText);
    }


    PatternLockView.OnPatternListener listener = new PatternLockView.OnPatternListener() {

        @Override
        public void onStarted() {

        }

        @Override
        public void onProgress(@NonNull ArrayList<Integer> ids) {

        }

        @Override
        public boolean onComplete(@NonNull ArrayList<Integer> ids) {
            boolean isCorrect = TextUtils.equals("012", getPatternString(ids));
            String tip;
            if (isCorrect){
                tip = "correct:" + getPatternString(ids);
            }else {
                tip = "error:"+ getPatternString(ids);
            }
            return isCorrect;
        }
    };


    String getPatternString(ArrayList<Integer> ids){
        result ="";
        ids.forEach(integer -> {
            result = result+ integer+"";
        });

        return result;
    }


}