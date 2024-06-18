package com.corporation8793.dementia.game;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.databinding.ActivityFindSameColorAndTextBinding;
import com.corporation8793.dementia.game.pulse_countdown.OnCountdownCompleted;
import com.corporation8793.dementia.game.pulse_countdown.PulseCountDown;
import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DisplayFontSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class FindSameColorAndTextActivity extends AppCompatActivity {

    TextView counting_rest;
    Button close_btn;
    ProgressBar time_progress;

    // 게임 기본 시간 6초로 설정
    private static final int GAME_TIME = 6000;

    PulseCountDown startGameCountDownTimer;
    CountDownTimer mainCountDownTimer;

    int[] buttonArray = { R.id.first, R.id.second, R.id.third, R.id.fourth,
            R.id.fifth, R.id.sixth, R.id.seventh, R.id.eighth, R.id.ninth };

    String[] colorName = { "빨강", "주황", "노랑", "초록", "파랑", "남색", "보라", "하양", "분홍", "연두", "하늘", "갈색" };
    int[] colorPalette = { R.color.red_ff0000, R.color.orange_ffa500, R.color.yellow_ffff00, R.color.green_008000,
            R.color.blue_0000ff, R.color.dark_blue_00008b, R.color.purple_800080, R.color.white_ffffff,
            R.color.pink_ff5999, R.color.light_green_99ee90, R.color.sky_blue_5bccff, R.color.brown_8a4b08 };

    ArrayList<String> colorName2 = new ArrayList<>();
    ArrayList<Integer> colorPalette2 = new ArrayList<>();

    Button correct_btn;
    String correct_color;
    int correct_color_palette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_find_same_color_and_text);
        ActivityFindSameColorAndTextBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_find_same_color_and_text);

        counting_rest = binding.topSection.findViewById(R.id.counting_rest);
        close_btn = binding.topSection.findViewById(R.id.close_btn);
        time_progress = binding.topSection.findViewById(R.id.time_progress);

        counting_rest.setTextSize(DisplayFontSize.font_size_x_32);
        close_btn.setTextSize(DisplayFontSize.font_size_x_32);

        close_btn.setOnClickListener(v->{
            finish();
        });

        binding.gameExplainText.setTextSize(DisplayFontSize.font_size_x_46);

        for (int i = 0; i < buttonArray.length; i++) {
            Button button = findViewById(buttonArray[i]);
            button.setTextSize(DisplayFontSize.font_size_x_46);
        }

        startGameCountDownTimer = binding.pulseCountDown;
        startGameCountDownTimer.setTextSize(DisplayFontSize.font_size_x_60);

        // 네비게이션바 숨기기
        Application.FullScreenMode(FindSameColorAndTextActivity.this);

        // 6초 타이머 설정
        startPulse(GAME_TIME);
    }

    // 랜덤 문제 출제
    private void startGame() {
        // 리스트 초기화
        colorName2.clear();
        colorPalette2.clear();

        // 색상 이름과 색상 매칭
        Map<String, Integer> color_match = new HashMap<>();

        for (int i = 0; i < colorName.length; i++) {
            Log.e("test2", "color_match : " + colorName[i] + "," + colorPalette[i]);
            color_match.put(colorName[i], colorPalette[i]);
        }
        Log.e("test2", "color_match : " + color_match);

        // 랜덤으로 9개의 동그라미 중 정답으로 쓰일 동그라미 정하기
        Random random = new Random();
        int randomNumValue = random.nextInt(buttonArray.length);
        correct_btn = findViewById(buttonArray[randomNumValue]);

        Log.e("test2", "correct_btn : " + randomNumValue);

        // 랜덤으로 12개의 색상 중 정답으로 쓰일 색상 정하기
        int randomColorValue = random.nextInt(colorName.length);
        correct_color = colorName[randomColorValue];
        correct_color_palette = colorPalette[randomColorValue];

        // 정답을 제외한 색상 지정을 위한 이름 및 색상 리스트 추가
        colorName2.addAll(Arrays.asList(colorName));
        Log.e("test2", "colorName2 : " + colorName2);

        for (int j : colorPalette) {
            colorPalette2.add(j);
        }
        Log.e("test2", "colorPalette2 : " + colorPalette2);

        Log.e("test2", "correct_color : " + colorName[randomColorValue]);
        Log.e("test2", "correct_color_palette : " + colorPalette[randomColorValue]);

        // 정답 위치에 색상 이름과 색상 적용
        correct_btn.setText(correct_color);
        correct_btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, correct_color_palette)));

        // 정답 버튼 클릭시
        correct_btn.setOnClickListener(v -> {
            // 타이머 취소
            if (mainCountDownTimer != null) {
                mainCountDownTimer.cancel();
            }

            // 다시 문제 출제
            startGame();

            // 타이머 시작
            startTimer(GAME_TIME);
        });

        // 정답 색상 이름 및 색상 제거
        colorName2.remove(correct_color);
        Log.e("test2", "colorName2 : " + colorName2);

        colorPalette2.remove(randomColorValue);
        Log.e("test2", "colorPalette2 : " + colorPalette2);

        // 나머지 위치에 무작위로 이름과 색상 지정
        // 같은 이름과 색상이 적용되지 않도록 체크
        for (int i = 0; i < buttonArray.length; i++) {
            if (i != randomNumValue) {
                // 정답 버튼이 아닌 경우만 적용
                Button button = findViewById(buttonArray[i]);

                // 정답을 제외한 11개중 랜덤 색상 이름 지정
                int randomColorValue2 = random.nextInt(colorName2.size());

                Log.e("test2", "color : " + randomColorValue2);
                Log.e("test2", "color : " + colorName2.get(randomColorValue2));

                // 중복되지 않는 이름으로 부여
                button.setText(colorName2.get(randomColorValue2));

                // 정답을 제외한 11개중 랜덤 색상 지정
                int randomColorValue3 = random.nextInt(colorPalette2.size());

                // 색상 이름과 색상이 같으면 다시 돌리기
                while (Objects.equals(color_match.get(colorName2.get(randomColorValue2)), colorPalette2.get(randomColorValue3))) {
                    Log.e("test2", "color correct!");
                    randomColorValue3 = random.nextInt(colorPalette2.size());
                }

                // 중복과 정답이 아닌 색상으로 부여
                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, colorPalette2.get(randomColorValue3))));

                // 사용한 색상 이름 제거
                colorName2.remove(randomColorValue2);
                Log.e("test2", "colorName2 : " + colorName2);

                // 사용한 색상 제거
                colorPalette2.remove(randomColorValue3);
                Log.e("test2", "colorPalette2 : " + colorPalette2);

//                // 정답 이름과 같으면 재지정
//                while (correct_color.equals(colorName[randomColorValue2])) {
//                    Log.e("test2", "color change1");
//
//                    randomColorValue2 = random.nextInt(colorName.length);
//                }
//
//                Log.e("test2", "color2 : " + randomColorValue2);
//
//                // 이미 쓰인 이름은 제외시키기
//                for (int j = 0; j < i; j++) {
//                    Button button2 = findViewById(buttonArray[j]);
//
//                    Log.e("test2", button2.getText().toString());
//
//                    if (button2.getText().toString().equals(colorName[randomColorValue2])) {
//                        Log.e("test2", "color change2");
//
//                        randomColorValue2 = random.nextInt(colorName.length);
//
//                        // 중복이 없는지 처음부터 다시 확인
//                        j--;
//                    }
//                }
//
//                Log.e("test2", "color3 : " + randomColorValue2);
            } else {
                Log.e("test2", "correct button");
            }

            Log.e("test2", "-------------------------");
        }
    }

    // 게임 시작 전 시작 펄스 타이머
    private void startPulse(int time) {
        startGameCountDownTimer.start(new OnCountdownCompleted() {
            @Override
            public void completed() {
                // 타이머 시작
                startTimer(time);

                // 랜덤 문제 출제
                startGame();
            }
        });
    }

    // 게임 타이머 시작
    private void startTimer(int time){
        // Interval - 1 second
        // Timer - 6 seconds
        time_progress.setMax(time);

        mainCountDownTimer = new CountDownTimer(time, 1) {
            public void onTick(long millisUntilFinished) {
                Log.e("test", millisUntilFinished+"");

                time_progress.setProgress((int) millisUntilFinished);
            }

            // 게임이 끝나면
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "TIME OVER", Toast.LENGTH_SHORT).show();
                time_progress.setProgress(0);
                // 결과 페이지로 이동
                //startResultActivity();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mainCountDownTimer != null) {
            mainCountDownTimer.cancel();
        }
    }
}