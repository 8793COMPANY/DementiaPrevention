package com.corporation8793.dementia.game.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.databinding.ActivityQuizBinding;
import com.corporation8793.dementia.diagnose.QuestionnaireActivity;
import com.corporation8793.dementia.game.ResultActivity;
import com.corporation8793.dementia.game.order_number.OrderNumberGame;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    long baseTime;
    long setTime;

    // 기본 3초로 설정
    private static final Long SET_TIME = 10L;

    TextView quiz_number, quiz_text;
    ConstraintLayout answer1,answer2;
    ImageView answer_icon_1, answer_icon_2;
    TextView answer_text_1,answer_text_2;

    //top section
    TextView counting_rest;
    Button close_btn;
    ProgressBar time_progress;

    int quiz_size = 10;

    int current_pos =1;
    ActivityQuizBinding binding;

    int right_number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_quiz);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_quiz);

        counting_rest = binding.topSection.findViewById(R.id.counting_rest);
        close_btn = binding.topSection.findViewById(R.id.close_btn);
        time_progress = binding.topSection.findViewById(R.id.time_progress);

        quiz_number = binding.quizNumber;
        quiz_text = binding.quizText;

        answer1 = binding.answer1;
        answer2 = binding.answer2;

        answer_text_1 = binding.answerText1;
        answer_text_2 = binding.answerText2;

        answer_icon_1 = binding.answerIcon1;
        answer_icon_2 = binding.answerIcon2;

        counting_rest.setText(current_pos+"/"+quiz_size);
        quiz_number.setText("Q"+current_pos+".");
        close_btn.setOnClickListener(v->{
        });

        binding.confirmBtn.setOnClickListener(v->{
            //TODO : 정답 확인 기능 코드 필요
            choice_init();
            binding.confirmBtn.setEnabled(false);
            current_pos++;
            timeReset();
            timeStart();
            counting_rest.setText(current_pos+"/"+quiz_size);
            quiz_number.setText("Q"+current_pos+".");
            Random rd = new Random();
            boolean check = rd.nextBoolean();
            setCorrectBtn(check,answer1, answer_text_1, answer_icon_1);
            setCorrectBtn(!check, answer2, answer_text_2, answer_icon_2);
        });

        answer1.setOnClickListener(v->{
            if(answer1.isSelected()){
                binding.confirmBtn.setEnabled(false);
                answer1.setSelected(false);
            }else{
                choice_init();
                answer1.setSelected(true);
                binding.confirmBtn.setEnabled(true);
            }
        });

        answer2.setOnClickListener(v->{
            if(answer2.isSelected()){
                binding.confirmBtn.setEnabled(false);
                answer1.setSelected(false);
            }else{
                choice_init();
                answer2.setSelected(true);
                binding.confirmBtn.setEnabled(true);
            }
        });


        Random rd = new Random();
        boolean check = rd.nextBoolean();
        setCorrectBtn(check, answer1, answer_text_1, answer_icon_1);
        setCorrectBtn(!check, answer2, answer_text_2, answer_icon_2);

        timeStart();
    }

    void choice_init(){
        answer1.setSelected(false);
        answer2.setSelected(false);
    }



    void setCorrectBtn(boolean check, ConstraintLayout layout, TextView text, ImageView icon){
        if (check){
            layout.setBackgroundResource(R.drawable.correct_btn_selector);
            text.setText("맞다");
            icon.setBackgroundResource(R.drawable.quiz_correct_icon);
        }else{
            layout.setBackgroundResource(R.drawable.incorrect_btn_selector);
            text.setText("아니다");
            icon.setBackgroundResource(R.drawable.quiz_incorrect_icon);
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
                    finish();
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("type","5");
                    intent.putExtra("size",quiz_size);
                    intent.putExtra("rating",right_number);
                    startActivity(intent);
                }else{
                    choice_init();
                    binding.confirmBtn.setEnabled(false);
                    current_pos++;
                    timeReset();
                    timeStart();
                    counting_rest.setText(current_pos+"/"+quiz_size);
                    quiz_number.setText("Q"+current_pos+".");
                    Random rd = new Random();
                    boolean check = rd.nextBoolean();
                    setCorrectBtn(check,answer1, answer_text_1, answer_icon_1);
                    setCorrectBtn(!check, answer2, answer_text_2, answer_icon_2);
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