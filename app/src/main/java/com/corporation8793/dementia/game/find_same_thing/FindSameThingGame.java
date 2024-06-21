package com.corporation8793.dementia.game.find_same_thing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.databinding.ActivityFindSameThingGameBinding;
import com.corporation8793.dementia.databinding.ActivityOrderNumberGameBinding;
import com.corporation8793.dementia.databinding.ActivityQuizBinding;
import com.corporation8793.dementia.game.ResultActivity;
import com.corporation8793.dementia.game.SelectGameActivity;
import com.corporation8793.dementia.game.WhacAMoleActivity;
import com.corporation8793.dementia.game.order_number.OrderNumberGame;

import java.util.ArrayList;
import java.util.List;

public class FindSameThingGame extends AppCompatActivity {

    String result;
    PatternLockView patternLockView;

    long baseTime;
    long setTime;

    // 기본 3초로 설정
    private static final Long SET_TIME = 20L;


    ActivityOrderNumberGameBinding binding;
    List<String[]> list;
    List<Integer> shuffle;


    TextView counting_rest;
    Button close_btn;
    ProgressBar time_progress;

    int out_size = 3;

    public int current_pos =1;

    int right_number=0;

    int score= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_find_same_thing_game);
        ActivityFindSameThingGameBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_find_same_thing_game);


        patternLockView = findViewById(R.id.patternLockView);

        binding.patternLockView.setOnPatternListener(listener);
        binding.patternLockView.setTextView(binding.noticeText);
        binding.patternLockView.setActivity(this);


        counting_rest = binding.topSection.findViewById(R.id.counting_rest);
        close_btn = binding.topSection.findViewById(R.id.close_btn);
        time_progress = binding.topSection.findViewById(R.id.time_progress);

        counting_rest.setText(current_pos+"/"+out_size);

        close_btn.setOnClickListener(v->{
            Log.e("click","close");
            finish();
        });

        timeStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;
    }

    void setScore(int score){
        this.score += score;
    }
    void go_result_activity(int rating){
        Intent intent = new Intent(FindSameThingGame.this, ResultActivity.class);
        intent.putExtra("type","6");
        intent.putExtra("size",out_size);
        intent.putExtra("rating",score);
        startActivity(intent);
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
                    finish();
                    timeReset();
                    Intent intent = new Intent(FindSameThingGame.this, ResultActivity.class);
                    intent.putExtra("type","6");
                    intent.putExtra("size",out_size);
                    intent.putExtra("rating",score);
                    startActivity(intent);
                }else{
                    current_pos++;
                    timeReset();
                    counting_rest.setText(current_pos+"/"+out_size);
                    patternLockView.init();
                    patternLockView.invalidate();
                    timeStart();
                }

            } else {
                // 0초가 아니면
                handler.sendEmptyMessage(0);
            }
//                if (count < 100) {
//                    count++;
//                    progressBar.setProgress(count);
//
//                    MainActivity.this.sendMessage();
//                } else {
//                    count = 0;
//
//                    handler.removeCallbacksAndMessages(null);
//                }
        }
    };

    public void timeSetting(){
        current_pos++;
        timeReset();
        counting_rest.setText(current_pos+"/"+out_size);
    }

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