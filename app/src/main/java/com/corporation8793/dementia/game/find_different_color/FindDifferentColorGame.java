package com.corporation8793.dementia.game.find_different_color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.diagnose.DiagnosticResultActivity;
import com.corporation8793.dementia.diagnose.QuestionnaireActivity;
import com.corporation8793.dementia.game.ResultActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FindDifferentColorGame extends AppCompatActivity {

    long baseTime;
    long setTime;

    // 기본 3초로 설정
    private static final Long SET_TIME = 10L;

    TextView counting_rest;
    Button close_btn;
    ProgressBar time_progress;

    int out_size = 5;

    int current_pos =1;

    int different_color = R.color.blue_00baff;
    int same_color = R.color.blue_5ad3ff;

    TextView game_explain_text;
    ConstraintLayout parent_container;

    int right_number=0;
    int randomIndex;

    int counting= 0;
    GridLayout color_list;

    ArrayList<Button> buttons = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_different_color);

        game_explain_text = findViewById(R.id.game_explain_text);
        color_list = findViewById(R.id.color_list);
        parent_container = (ConstraintLayout) color_list.getChildAt(0);

        View top_section = findViewById(R.id.top_section);

        counting_rest = top_section.findViewById(R.id.counting_rest);
        close_btn = top_section.findViewById(R.id.close_btn);
        time_progress = top_section.findViewById(R.id.time_progress);

        counting_rest.setText(current_pos+"/"+out_size);

        Random randomInt = new Random();
        randomIndex = randomInt.nextInt(9);

        int childCount = parent_container.getChildCount();
        Log.e("childCount",childCount+"");
        for (int i= 0; i < childCount; i++){

            Button container = (Button) parent_container.getChildAt(i);
            container.setTag(i);
            buttons.add(container);

            if (i == randomIndex){
                container.setBackgroundTintList(getResources().getColorStateList(different_color));
            }else {
                container.setBackgroundTintList(getResources().getColorStateList(same_color));
            }

            Handler handler = new Handler();

            container.setOnClickListener(v->{
                Log.e("container","onclick");
                setBtnEnabled(false);
                timeReset();



                if (Integer.parseInt(container.getTag().toString()) == randomIndex){
                    game_explain_text.setText("정답!");
                    counting++;

                }else {
                    game_explain_text.setText("ㅠㅠ");
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (current_pos == out_size){
                            timeReset();
                            finish();
                            Intent intent = new Intent(FindDifferentColorGame.this, ResultActivity.class);
                            intent.putExtra("size",out_size);
                            intent.putExtra("rating",counting);
                            startActivity(intent);
                            return;
                        }else {
                            current_pos++;
                            game_explain_text.setText("혼자 다른 색상을 찾아보세요");
                            setting_btn_value(parent_container);
                            timeStart();
                            counting_rest.setText(current_pos + "/" + out_size);
                        }
                    }
                },1000);

            });


        }

        close_btn.setOnClickListener(v->{
            finish();
        });

        timeStart();
    }

    void setBtnEnabled(boolean check){
        for (int i=0; i< buttons.size(); i++){
            buttons.get(i).setEnabled(check);
            buttons.get(i).setClickable(check);
        }
    }

    void setting_btn_value(ConstraintLayout parent_container) {

        Random randomInt = new Random();
         randomIndex= randomInt.nextInt(9);

        int childCount = parent_container.getChildCount();
        Log.e("childCount", childCount + "");
        for (int i = 0; i < childCount; i++) {

            Button container = (Button) parent_container.getChildAt(i);
            container.setTag(i);
            setBtnEnabled(true);

            if (i == randomIndex) {
                container.setBackgroundTintList(getResources().getColorStateList(different_color));
            } else {
                container.setBackgroundTintList(getResources().getColorStateList(same_color));
            }
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String time = setTimeOut();

            Log.e("test", time);
//            timeText.setText(time);

            // 0초가 되면
            if (time.equals("00:00")) {
                // 타이머 초기화
                if (current_pos == out_size){
//                    finish();
                    timeReset();
                    finish();
                    Intent intent = new Intent(FindDifferentColorGame.this, ResultActivity.class);
                    intent.putExtra("size",out_size);
                    intent.putExtra("rating",counting);
                    startActivity(intent);
                }else{
                    current_pos++;
                    timeReset();
                    counting_rest.setText(current_pos+"/"+out_size);
                    setting_btn_value(parent_container);
                    timeStart();
                }

            } else {
                // 0초가 아니면
                handler.sendEmptyMessage(0);
            }

        }
    };

    public void timeStart() {
        // 기본 3초로 설정
        setTime(SET_TIME);

        baseTime = SystemClock.elapsedRealtime();

        handler.sendEmptyMessage(0);
    }


    public void timeReset() {
        //핸들러 메세지 전달 종료
        handler.removeCallbacksAndMessages(null);

        //long 변환한 시간 초기화
        setTime(SET_TIME);

        //프로그레스바 프로그레스 초기화
        time_progress.setMax((int)setTime);
        time_progress.setProgress((int)setTime);
    }

    @SuppressLint("DefaultLocale")
    public String setTimeOut() {
        long now = SystemClock.elapsedRealtime();
        long outTime = baseTime - now + setTime;

        long min = outTime / 1000 / 60;
        long sec = outTime / 1000 % 60;

        // 0.1초 단위가 남아있을때 초가 넘어가서 0.5초에도 0초로 표시 되기 때문에
        // 0.1초 단위를 계산해서 초가 60초 이하일때 0.1초 단위가 남아 있으면 초가 변경되지 않도록 세팅
        if (outTime % 1000 / 10 != 0) {
            sec += 1;

            if (sec == 60) {
                sec = 0;
                min += 1;
            }
        }

        // 시간 확인용
        String easy_outTime = String.format("%02d:%02d", min, sec);

        // 늘어나는 경우
//        progressBar.setProgress((int) ((now - baseTime) + (setTime/1000)));
        // 줄어드는 경우
        time_progress.setProgress(time_progress.getMax() - ((int) ((now - baseTime) + (setTime/1000))));

        return easy_outTime;
    }

    public void setTime(Long time) {
        // 분단위 : * 1000 * 60 + 초단위 : * 1000
        // 현재는 초 단위만 계산
        setTime = time * 1000;
        time_progress.setMax((int) setTime);
    }
}