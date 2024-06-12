package com.corporation8793.dementia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

import com.corporation8793.dementia.databinding.ActivityQuestionnaireBinding;

public class QuestionnaireActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    Button next_end_btn;

    long baseTime;
    long setTime;

    // 기본 3초로 설정
    private static final Long SET_TIME = 6L;

    ProgressBar time_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_questionnaire);
        fragmentManager = getSupportFragmentManager();

        ActivityQuestionnaireBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_questionnaire);

        next_end_btn = binding.nextEndBtn;
        time_progress = binding.topSection.findViewById(R.id.time_progress);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, new ThreeChoiceFragment("1","음주는 얼마나 자주하시나요?"), null)
                    .commit();
        }

        binding.topSection.findViewById(R.id.close_btn).setOnClickListener(v->{
            finish();
        });

        timeStart();


    }

    void transaction_fragment(){
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, new ThreeChoiceFragment("2","안녕하세요?"), null)
                .setReorderingAllowed(true)
                .commit();
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
                timeReset();
                transaction_fragment();
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
        setTime = 0;

        //프로그레스바 프로그레스 초기화
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