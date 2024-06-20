package com.corporation8793.dementia.game.order_number;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.databinding.ActivityOrderNumberGameBinding;
import com.corporation8793.dementia.databinding.GameListLayoutBinding;
import com.corporation8793.dementia.diagnose.DiagnosticResultActivity;
import com.corporation8793.dementia.diagnose.QuestionnaireActivity;
import com.corporation8793.dementia.game.ResultActivity;
import com.corporation8793.dementia.game.find_different_color.FindDifferentColorGame;
import com.google.android.gms.common.util.DataUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrderNumberGame extends AppCompatActivity {

    int count =0;

    String [][] number ={{"1","일","one"},{"2","이","two"},{"3","삼","three"},
            {"4","사","four"},{"5","오","five"},{"6","육","six"},
            {"7","칠","seven"},{"8","팔","eight"},{"9","구","nine"} };

    int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    long baseTime;
    long setTime;

    // 기본 3초로 설정
    private static final Long SET_TIME = 10L;


    ActivityOrderNumberGameBinding binding;
    List<String[]> list;
    List<Integer> shuffle;


    TextView counting_rest;
    Button close_btn;
    ProgressBar time_progress;

    int out_size = 10;

    int current_pos =1;
    ConstraintLayout main;

    int right_number = 0;
    boolean cycle_check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order_number_game);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_number_game);

        counting_rest = binding.topSection.findViewById(R.id.counting_rest);
        close_btn = binding.topSection.findViewById(R.id.close_btn);
        time_progress = binding.topSection.findViewById(R.id.time_progress);

        counting_rest.setText(current_pos+"/"+out_size);



        list = Arrays.asList(number);
        reset();

        shuffle.forEach(i -> {

            Log.e("hello shuffle num", i+"");
        });


        GridLayout number_list = findViewById(R.id.number_list);




        main = (ConstraintLayout) number_list.getChildAt(0);


        close_btn.findViewById(R.id.close_btn).setOnClickListener(v->{
            finish();
        });

        int childCount = main.getChildCount();
        Log.e("childCount",childCount+"");
        for (int i= 0; i < childCount; i++){

            Button container = (Button) main.getChildAt(i);

            int randomNum = (int) (Math.random() * 3);
            container.setText(number[shuffle.get(i)][randomNum]);
            container.setOnClickListener(v->{
                Log.e("button text", container.getText().toString());
                if (count == 8){
                    cycle_check = true;

//                    right_number++;
//                    current_pos++;
                    reset();
                    setting_btn_value(main);
//                    counting_rest.setText(current_pos+"/"+out_size);
                    timeReset();
                    timeStart();
                }else{
                    if (Arrays.asList(list.get(count)).contains(container.getText().toString())){
                        Log.e("result","정답!");
                        container.setText("x");
                        container.setBackgroundColor(getResources().getColor(R.color.gray_9f9f9f));
                        container.setEnabled(false);
                        count++;
                    }
                }

            });
        }

        timeStart();


    }


    void reset(){
        shuffle = Arrays.stream(a).boxed().collect(Collectors.toList());

        Collections.shuffle(shuffle);
        count = 0;
    }


    void setting_btn_value(ConstraintLayout main){
        int childCount = main.getChildCount();
        Log.e("childCount",childCount+"");
        for (int i= 0; i < childCount; i++){

            Button container = (Button) main.getChildAt(i);

            int randomNum = (int) (Math.random() * 3);
            container.setText(number[shuffle.get(i)][randomNum]);
            container.setBackgroundColor(getResources().getColor(R.color.green_c8ff71));
            container.setEnabled(true);
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String time = setTimeOut();

            Log.e("test", time);
//            timeText.setText(time);

            if (cycle_check) {
                cycle_check = false;

                if (current_pos == out_size){
                    right_number++;
                    finish();
                    Intent intent = new Intent(OrderNumberGame.this, ResultActivity.class);
                    intent.putExtra("type", 2);
                    intent.putExtra("size",out_size);
                    intent.putExtra("rating",right_number);
                    startActivity(intent);
                }else{
                    right_number++;
                    current_pos++;
                    counting_rest.setText(current_pos+"/"+out_size);
                    timeReset();
                    reset();
                    setting_btn_value(main);
                    timeStart();
                }
            } else {
                // 0초가 되면
                if (time.equals("00:00")) {
                    // 타이머 초기화
                    if (current_pos == out_size){
                        finish();
                        Intent intent = new Intent(OrderNumberGame.this, ResultActivity.class);
                        intent.putExtra("type", 2);
                        intent.putExtra("size",out_size);
                        intent.putExtra("rating",right_number);
                        startActivity(intent);
                    }else{
                        current_pos++;
                        timeReset();
                        counting_rest.setText(current_pos+"/"+out_size);
                        reset();
                        setting_btn_value(main);
                        timeStart();
                    }

                } else {
                    // 0초가 아니면
                    handler.sendEmptyMessage(0);
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}