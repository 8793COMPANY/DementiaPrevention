package com.corporation8793.dementia.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.databinding.ActivityQuizBinding;
import com.corporation8793.dementia.databinding.ActivitySelectGameBinding;
import com.corporation8793.dementia.game.find_different_color.FindDifferentColorGame;
import com.corporation8793.dementia.game.find_same_thing.FindSameThingGame;
import com.corporation8793.dementia.game.order_number.OrderNumberGame;
import com.corporation8793.dementia.game.quiz.QuizActivity;

public class SelectGameActivity extends AppCompatActivity {
    ActivitySelectGameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_game);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_select_game);

        binding.topSection.closeBtn.setOnClickListener(v->{
            finish();
        });

        binding.whackAMole.game.setOnClickListener(v->{
            Intent intent = new Intent(SelectGameActivity.this, WhacAMoleActivity.class);
            startActivity(intent);
        });


        binding.colorPuzzle.game.setOnClickListener(v -> {
            Intent intent = new Intent(SelectGameActivity.this, FindSameThingGame.class);
            startActivity(intent);
        });
        binding.selectRightColorName.game.setOnClickListener(v -> {
            Intent intent = new Intent(SelectGameActivity.this, FindSameColorAndTextActivity.class);
            startActivity(intent);
        });
        binding.quizGame.game.setOnClickListener(v -> {
            Intent intent = new Intent(SelectGameActivity.this, QuizActivity.class);
            startActivity(intent);
        });
        binding.orderNumberGame.game.setOnClickListener(v -> {
            Intent intent = new Intent(SelectGameActivity.this, OrderNumberGame.class);
            startActivity(intent);
        });
        binding.findDifferentColorGame.game.setOnClickListener(v -> {
            Intent intent = new Intent(SelectGameActivity.this, FindDifferentColorGame.class);
            startActivity(intent);
        });

    }
}