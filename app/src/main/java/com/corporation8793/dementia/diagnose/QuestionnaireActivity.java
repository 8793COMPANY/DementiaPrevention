package com.corporation8793.dementia.diagnose;

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

import com.corporation8793.dementia.Init;
import com.corporation8793.dementia.R;
import com.corporation8793.dementia.databinding.ActivityQuestionnaireBinding;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    Button next_end_btn;

    long baseTime;
    long setTime;

    // 기본 3초로 설정
    private static final Long SET_TIME = 5L;



    private TextToSpeech textToSpeech;

    String question_text="";
    private final String TTS_ID = "TTS";

    TextView counting_rest;
    Button close_btn;
    ProgressBar time_progress;

    int quiz_size = 8;

    int current_pos =1;


    ArrayList<Diagnose> questionnaire_list = new ArrayList<>();

    String [] titles = {"음주는 얼마나 자주하시나요?","약 먹는 시간을\n여러 번 놓친 적이 있다.","자신의 전화번호를 모른다.","자신의 주소를 모른다.","글을 읽고 쉽게 문맥을\n파악하기 어려운 적이 있다.",
    "5분 전에 무슨 일이 있었는지\n기억하지 못한다.","갈수록 말수가 감소하는\n경향이 있다.","자주 가던 공간이 낯설다고\n느낀 적이 있다."};


    int [] drawables = {R.drawable.questionnaire_icon1,R.drawable.questionnaire_icon2,R.drawable.questionnaire_icon3,R.drawable.questionnaire_icon4,
            R.drawable.questionnaire_icon5,R.drawable.questionnaire_icon6,R.drawable.questionnaire_icon7,R.drawable.questionnaire_icon8};

    int [] choice_count ={3,2,2,2,2,2,2,2};

    public String [] answer_list = {"하지않음","아니요","아니요","아니요","아니요","아니요","아니요","아니요"};

    int answer_count = 0;
    static String current_answer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_questionnaire);
        fragmentManager = getSupportFragmentManager();
        ActivityQuestionnaireBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_questionnaire);

        next_end_btn = binding.nextEndBtn;
        time_progress = binding.topSection.findViewById(R.id.time_progress);

        counting_rest = binding.topSection.findViewById(R.id.counting_rest);
        close_btn = binding.topSection.findViewById(R.id.close_btn);
        time_progress = binding.topSection.findViewById(R.id.time_progress);

        textToSpeech  = Init.textToSpeech;

        counting_rest.setText(current_pos+"/"+quiz_size);

        for (int i=0; i< quiz_size; i++){
            Diagnose diagnose = new Diagnose(titles[i], drawables[i], choice_count[i]);
            questionnaire_list.add(diagnose);
        }

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
                    .add(R.id.fragment_container_view, new ThreeChoiceFragment(current_pos+"",questionnaire_list.get(current_pos-1)), null)
                    .commit();
//            speakText(question_text);

        }

        binding.topSection.findViewById(R.id.close_btn).setOnClickListener(v->{
            finish();
        });


        next_end_btn.setOnClickListener(v->{
            Log.e("check value answer_list", answer_list[current_pos-1]);
            Log.e("check value current_answer", current_answer);
            next_end_btn.setEnabled(false);
            if (answer_list[current_pos-1].equals(current_answer)){

                answer_count++;
            }


            if (current_pos -1 >= 7){
                timeReset();
                finish();
                Intent intent = new Intent(QuestionnaireActivity.this, DiagnosticResultActivity.class);
                intent.putExtra("rating",answer_count);
                startActivity(intent);
            }else{
                current_pos++;
                timeReset();
                transaction_fragment();
                counting_rest.setText(current_pos+"/"+quiz_size);
                next_end_btn.setEnabled(false);
            }

            next_end_btn.setEnabled(true);
        });


    }

    void setCurrentAnswer(String str){
        Log.e("choice in",str);
        current_answer = str;
    }

    void speakText(String text){
        textToSpeech.setPitch(1.0f);
        textToSpeech.setSpeechRate(1.0f);
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, TTS_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;

    }

    void transaction_fragment(){
        if (questionnaire_list.get(current_pos -1).choice_count == 2){
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new TwoChoiceFragment(current_pos+"",questionnaire_list.get(current_pos-1)), null)
                    .commitAllowingStateLoss();
        }else{
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeChoiceFragment(current_pos+"",questionnaire_list.get(current_pos-1)), null)
                    .commitAllowingStateLoss();
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
                if (current_pos == quiz_size){
                    Log.e("check","time");
                    timeReset();
                    finish();
                    Intent intent = new Intent(QuestionnaireActivity.this, DiagnosticResultActivity.class);
                    startActivity(intent);
                }else{
                    current_pos++;
                    timeReset();
                    transaction_fragment();
                    counting_rest.setText(current_pos+"/"+quiz_size);
                    next_end_btn.setEnabled(false);
                    if (current_pos == 8)
                        next_end_btn.setText("제출");
                    next_end_btn.setEnabled(false);
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