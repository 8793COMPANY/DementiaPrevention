package com.corporation8793.dementia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.widget.Button;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button seoul, daegu, gwangju;
=======
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.corporation8793.dementia.game.WhacAMoleActivity;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button startBtn, whacAMoleBtn, mapBtn;
    TextView timeText;

    int count = 0;

    long baseTime;
    long setTime;

    // 기본 3초로 설정
    private static final Long SET_TIME = 3L;

>>>>>>> 82edb761c14c8da33bf7ca83a0d627ab3920d2b8
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        try {
            Region.loadData(MainActivity.this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

        seoul = findViewById(R.id.seoul);
        daegu = findViewById(R.id.daegu);
        gwangju = findViewById(R.id.gwangju);

        Region region = new Region();

        seoul.setOnClickListener(v->{
            region.check_region("서울특별시");
        });

        daegu.setOnClickListener(v->{
            region.check_region("대구광역시");
        });


        gwangju.setOnClickListener(v->{
            region.check_region("경기도");
        });



=======
        progressBar = findViewById(R.id.progressBar);
        startBtn = findViewById(R.id.startBtn);
        whacAMoleBtn = findViewById(R.id.whacAMoleBtn);
        timeText = findViewById(R.id.timeText);
        mapBtn = findViewById(R.id.mapBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeStart();
                //sendMessage();
            }
        });

        whacAMoleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WhacAMoleActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        mapBtn. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String time = setTimeOut();

            Log.e("test", time);
            timeText.setText(time);

            // 0초가 되면
            if (time.equals("00:00")) {
                // 타이머 초기화
                timeReset();
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

//    public void sendMessage() {
//        Message message = new Message();
//        handler.sendMessageDelayed(message, 10);
//    }

    // 시간 시작
    public void timeStart() {
        // 기본 3초로 설정
        setTime(SET_TIME);

        baseTime = SystemClock.elapsedRealtime();

        handler.sendEmptyMessage(0);
    }

    // 시간 초기화
    public void timeReset() {
        //핸들러 메세지 전달 종료
        handler.removeCallbacksAndMessages(null);

        //long 변환한 시간 초기화
        setTime = 0;

        //프로그레스바 프로그레스 초기화
        progressBar.setProgress(0);
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
        progressBar.setProgress((int) ((now - baseTime) + (setTime/1000)));
        // 줄어드는 경우
        //progressBar.setProgress(progressBar.getMax() - ((int) ((now - baseTime) + (setTime/1000))));

        return easy_outTime;
    }

    public void setTime(Long time) {
        // 분단위 : * 1000 * 60 + 초단위 : * 1000
        // 현재는 초 단위만 계산
        setTime = time * 1000;
        progressBar.setMax((int) setTime);
>>>>>>> 82edb761c14c8da33bf7ca83a0d627ab3920d2b8
    }
}