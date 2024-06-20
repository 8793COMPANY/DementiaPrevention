package com.corporation8793.dementia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.corporation8793.dementia.chat.ChatActivity;
import com.corporation8793.dementia.databinding.ActivityMainBinding;
import com.corporation8793.dementia.diagnose.QuestionnaireActivity;
import com.corporation8793.dementia.diagnose.lately.DiagnoseResultListActivity;
import com.corporation8793.dementia.game.SelectGameActivity;
import com.corporation8793.dementia.game.WhacAMoleActivity;
import com.corporation8793.dementia.util.Application;
import com.opencsv.exceptions.CsvException;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageButton menu_btn;

    ProgressBar progressBar;
    Button startBtn, whacAMoleBtn, mapBtn;
    TextView timeText;
    Button seoul, daegu, gwangju;

    int count = 0;

    long baseTime;
    long setTime;

    // 기본 3초로 설정
    private static final Long SET_TIME = 3L;

    DrawerLayout drawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Init.settingTTS(getApplicationContext());

        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        if (!MySharedPreferences.getBoolean(getApplicationContext(),"first_check")){
            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
            startActivity(intent);
        }



        drawerLayout = findViewById(R.id.dl_main);
        menu_btn = findViewById(R.id.menu_btn);



        binding.diagnoseBtn.btn.setOnClickListener(v->{
            Log.e("hello","diagnosebtn");
            Intent intent = new Intent(MainActivity.this, QuestionnaireActivity.class);
            startActivity(intent);
        });

        binding.gameBtn.btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, SelectGameActivity.class);
            startActivity(intent);
        });


        binding.findCenterBtn.btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        });

        binding.chatBtn.btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        });


        menu_btn.setOnClickListener(v->{
            drawerLayout.openDrawer(GravityCompat.END);
        });

        Application.getInstance();
        Application.getStandardSize(MainActivity.this);

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


        //마이페이지
        drawerLayout.findViewById(R.id.close_btn).setOnClickListener(v->{
            drawerLayout.closeDrawers();
        });

        drawerLayout.findViewById(R.id.user_info_modify_section).setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
            intent.putExtra("type","edit");
            startActivity(intent);
        });

        drawerLayout.findViewById(R.id.latest_diagnose_list_section).setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, DiagnoseResultListActivity.class);
            startActivity(intent);
        });


        drawerLayout.findViewById(R.id.logout).setOnClickListener(v->{
            drawerLayout.closeDrawers();
            Toast.makeText(getApplicationContext(),"로그아웃",Toast.LENGTH_SHORT).show();
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


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.END)){
            drawerLayout.closeDrawers();
        }else{
            finish();
        }
    }

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Init.destroyTTS();
    }
}