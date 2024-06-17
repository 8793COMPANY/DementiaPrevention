package com.corporation8793.dementia;

import static android.speech.tts.TextToSpeech.ERROR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.corporation8793.dementia.databinding.ActivityQuestionnaireBinding;

import java.util.Locale;

public class QuestionnaireActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    Button next_end_btn;

    long baseTime;
    long setTime;

    // 기본 3초로 설정
    private static final Long SET_TIME = 2L;



    private TextToSpeech textToSpeech;

    String question_text="";
    private final String TTS_ID = "TTS";

    TextView counting_rest;
    Button close_btn;
    ProgressBar time_progress;

    int quiz_size = 10;

    int current_pos =1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_questionnaire);
        fragmentManager = getSupportFragmentManager();
        ActivityQuestionnaireBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_questionnaire);

        next_end_btn = binding.nextEndBtn;
        time_progress = binding.topSection.findViewById(R.id.time_progress);

        counting_rest = binding.topSection.findViewById(R.id.counting_rest);
        close_btn = binding.topSection.findViewById(R.id.close_btn);
        time_progress = binding.topSection.findViewById(R.id.time_progress);

        textToSpeech  = Init.textToSpeech;

        counting_rest.setText(current_pos+"/"+quiz_size);

        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {

            }

            @Override
            public void onDone(String s) {
               Log.e("end text", "hello");
               timeStart();

            }

            @Override
            public void onError(String s) {

            }

            @Override
            public void onRangeStart(String utteranceId, int start, int end, int frame) {

            }
        });





        if (savedInstanceState == null) {
            question_text = "음주는 얼마나 자주하시나요?";
            Log.e("error","first in");
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, new ThreeChoiceFragment(current_pos+"",question_text), null)
                    .commit();
//            speakText(question_text);

        }

        binding.topSection.findViewById(R.id.close_btn).setOnClickListener(v->{
            finish();
        });


        next_end_btn.setOnClickListener(v->{
            current_pos++;
            timeReset();
            transaction_fragment();
            counting_rest.setText(current_pos+"/"+quiz_size);
            next_end_btn.setEnabled(false);
        });


    }

    void speakText(String text){
        textToSpeech.setPitch(1.0f);
        textToSpeech.setSpeechRate(1.0f);
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, TTS_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    void transaction_fragment(){
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, new ThreeChoiceFragment(current_pos+"","안녕하세요?"), null)
                .commitAllowingStateLoss();
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
                if (current_pos == quiz_size){
                    finish();
                    Intent intent = new Intent(QuestionnaireActivity.this, DiagnosticResultActivity.class);
                    startActivity(intent);
                }else{
                    current_pos++;
                    timeReset();
                    transaction_fragment();
                    counting_rest.setText(current_pos+"/"+quiz_size);
                    next_end_btn.setEnabled(false);
                    if (current_pos == 10)
                        next_end_btn.setText("제출");
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