package com.corporation8793.dementia.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.corporation8793.dementia.IntroActivity;
import com.corporation8793.dementia.MySharedPreferences;
import com.corporation8793.dementia.R;
import com.corporation8793.dementia.databinding.ActivityQuizBinding;
import com.corporation8793.dementia.databinding.ActivitySelectGameBinding;
import com.corporation8793.dementia.game.find_different_color.FindDifferentColorGame;
import com.corporation8793.dementia.game.find_same_thing.FindSameThingGame;
import com.corporation8793.dementia.game.order_number.OrderNumberGame;
import com.corporation8793.dementia.game.quiz.QuizActivity;
import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DisplayFontSize;

public class SelectGameActivity extends AppCompatActivity {
    ActivitySelectGameBinding binding;
    SelectGameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_game);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_select_game);

        Application.FullScreenMode(SelectGameActivity.this);

        viewModel = new ViewModelProvider(this).get(SelectGameViewModel.class);
        binding.setViewModel(viewModel);

        if (MySharedPreferences.getString(SelectGameActivity.this, "scoreWhacAMole") == null
                || MySharedPreferences.getString(SelectGameActivity.this, "scoreWhacAMole").isEmpty()) { // 처음 시작하는 경우
            // 기본 0점으로 처리
            Log.e("testtttt", "null");
            viewModel.setScoreWhacAMole("최고점수 : 0");
        } else {
            Log.e("testtttt", "not null");
            Log.e("test~", MySharedPreferences.getString(SelectGameActivity.this, "scoreWhacAMole"));
            viewModel.setScoreWhacAMole("최고점수 : " + MySharedPreferences.getString(SelectGameActivity.this, "scoreWhacAMole"));
        }

        if (MySharedPreferences.getString(SelectGameActivity.this, "scoreOtherNumber") == null
                || MySharedPreferences.getString(SelectGameActivity.this, "scoreOtherNumber").isEmpty()) { // 처음 시작하는 경우
            // 기본 0점으로 처리
            Log.e("testtttt", "null");
            viewModel.setScoreOtherNumber("최고점수 : 0");
        } else {
            Log.e("testtttt", "not null");
            Log.e("test~", MySharedPreferences.getString(SelectGameActivity.this, "scoreOtherNumber"));
            viewModel.setScoreOtherNumber("최고점수 : " + MySharedPreferences.getString(SelectGameActivity.this, "scoreOtherNumber"));
        }

        if (MySharedPreferences.getString(SelectGameActivity.this, "scoreFindDifferentColor") == null
                || MySharedPreferences.getString(SelectGameActivity.this, "scoreFindDifferentColor").isEmpty()) { // 처음 시작하는 경우
            // 기본 0점으로 처리
            Log.e("testtttt", "null");
            viewModel.setScoreFindDifferentColor("최고점수 : 0");
        } else {
            Log.e("testtttt", "not null");
            Log.e("test~", MySharedPreferences.getString(SelectGameActivity.this, "scoreFindDifferentColor"));
            viewModel.setScoreFindDifferentColor("최고점수 : " + MySharedPreferences.getString(SelectGameActivity.this, "scoreFindDifferentColor"));
        }

        if (MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameColorAndText") == null
                || MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameColorAndText").isEmpty()) { // 처음 시작하는 경우
            // 기본 0점으로 처리
            Log.e("testtttt", "null");
            viewModel.setScoreFindSameColorAndText("최고점수 : 0");
        } else {
            Log.e("testtttt", "not null");
            Log.e("test~", MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameColorAndText"));
            viewModel.setScoreFindSameColorAndText("최고점수 : " + MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameColorAndText"));
        }

        if (MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameThing") == null
                || MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameThing").isEmpty()) { // 처음 시작하는 경우
            // 기본 0점으로 처리
            Log.e("testtttt", "null");
            viewModel.setScoreFindSameThing("최고점수 : 0");
        } else {
            Log.e("testtttt", "not null");
            Log.e("test~", MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameThing"));
            viewModel.setScoreFindSameThing("최고점수 : " + MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameThing"));
        }

//        viewModel.setScoreWhacAMole("최고점수 : 0");
//        viewModel.setScoreOtherNumber("최고점수 : 0");
//        viewModel.setScoreFindDifferentColor("최고점수 : 0");
//        viewModel.setScoreFindSameColorAndText("최고점수 : 0");
        viewModel.setScoreQuiz("최고점수 : 0");
//        viewModel.setScoreFindSameThing("최고점수 : 0");

        binding.topSection.countingRest.setTextSize(DisplayFontSize.font_size_x_38);
        binding.whackAMole.gameTitle.setTextSize(DisplayFontSize.font_size_x_32);
        binding.whackAMole.gameHighestScore.setTextSize(DisplayFontSize.font_size_x_26);
        binding.orderNumberGame.gameTitle.setTextSize(DisplayFontSize.font_size_x_32);
        binding.orderNumberGame.gameHighestScore.setTextSize(DisplayFontSize.font_size_x_26);
        binding.findDifferentColorGame.gameTitle.setTextSize(DisplayFontSize.font_size_x_32);
        binding.findDifferentColorGame.gameHighestScore.setTextSize(DisplayFontSize.font_size_x_26);
        binding.selectRightColorName.gameTitle.setTextSize(DisplayFontSize.font_size_x_32);
        binding.selectRightColorName.gameHighestScore.setTextSize(DisplayFontSize.font_size_x_26);
        binding.quizGame.gameTitle.setTextSize(DisplayFontSize.font_size_x_32);
        binding.quizGame.gameHighestScore.setTextSize(DisplayFontSize.font_size_x_26);
        binding.colorPuzzle.gameTitle.setTextSize(DisplayFontSize.font_size_x_32);
        binding.colorPuzzle.gameHighestScore.setTextSize(DisplayFontSize.font_size_x_26);

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

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("test", "Select Game onResume");

        if (MySharedPreferences.getString(SelectGameActivity.this, "scoreWhacAMole") == null
                || MySharedPreferences.getString(SelectGameActivity.this, "scoreWhacAMole").isEmpty()) { // 처음 시작하는 경우
            // 기본 0점으로 처리
            Log.e("testtttt", "null");
            binding.whackAMole.gameHighestScore.setText("최고점수 : 0");
        } else {
            Log.e("testtttt", "not null");
            Log.e("test~", MySharedPreferences.getString(SelectGameActivity.this, "scoreWhacAMole"));
            binding.whackAMole.gameHighestScore.setText("최고점수 : " + MySharedPreferences.getString(SelectGameActivity.this, "scoreWhacAMole"));
        }

        if (MySharedPreferences.getString(SelectGameActivity.this, "scoreOtherNumber") == null
                || MySharedPreferences.getString(SelectGameActivity.this, "scoreOtherNumber").isEmpty()) { // 처음 시작하는 경우
            // 기본 0점으로 처리
            Log.e("testtttt", "null");
            binding.orderNumberGame.gameHighestScore.setText("최고점수 : 0");
        } else {
            Log.e("testtttt", "not null");
            Log.e("test~", MySharedPreferences.getString(SelectGameActivity.this, "scoreOtherNumber"));
            binding.orderNumberGame.gameHighestScore.setText("최고점수 : " + MySharedPreferences.getString(SelectGameActivity.this, "scoreOtherNumber"));
        }

        if (MySharedPreferences.getString(SelectGameActivity.this, "scoreFindDifferentColor") == null
                || MySharedPreferences.getString(SelectGameActivity.this, "scoreFindDifferentColor").isEmpty()) { // 처음 시작하는 경우
            // 기본 0점으로 처리
            Log.e("testtttt", "null");
            binding.findDifferentColorGame.gameHighestScore.setText("최고점수 : 0");
        } else {
            Log.e("testtttt", "not null");
            Log.e("test~", MySharedPreferences.getString(SelectGameActivity.this, "scoreFindDifferentColor"));
            binding.findDifferentColorGame.gameHighestScore.setText("최고점수 : " + MySharedPreferences.getString(SelectGameActivity.this, "scoreFindDifferentColor"));
        }

        if (MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameColorAndText") == null
                || MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameColorAndText").isEmpty()) { // 처음 시작하는 경우
            // 기본 0점으로 처리
            Log.e("testtttt", "null");
            binding.selectRightColorName.gameHighestScore.setText("최고점수 : 0");
        } else {
            Log.e("testtttt", "not null");
            Log.e("test~", MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameColorAndText"));
            binding.selectRightColorName.gameHighestScore.setText("최고점수 : " + MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameColorAndText"));
        }

        if (MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameThing") == null
                || MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameThing").isEmpty()) { // 처음 시작하는 경우
            // 기본 0점으로 처리
            Log.e("testtttt", "null");
            binding.colorPuzzle.gameHighestScore.setText("최고점수 : 0");
        } else {
            Log.e("testtttt", "not null");
            Log.e("test~", MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameThing"));
            binding.colorPuzzle.gameHighestScore.setText("최고점수 : " + MySharedPreferences.getString(SelectGameActivity.this, "scoreFindSameThing"));
        }
    }
}