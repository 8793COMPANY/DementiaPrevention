package com.corporation8793.dementia.game.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
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
import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DisplayFontSize;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    OxQuizDialog dialog;

    long pauseTime;
    long baseTime;
    long setTime;

    // 기본 3초로 설정
    private static final Long SET_TIME = 10L; // 10초로 설정

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
    boolean cycle_check = false;

    List<OxQuiz> questionList; // JSON에서 로드한 문제 리스트
    List<OxQuiz> usedQuestions = new ArrayList<>(); // 이미 사용된 질문 리스트
    OxQuiz currentQuestion; // 현재 표시 중인 문제


    /*
        TODO: 10번 반복되긴 하지만 정답 맞추는 기능은 구현되지 않음
              세희님께서 디자인 해주시면 diagnose 패키지에 있는 문진표 관련 코드 참고해서 추가하시면 됩니다
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_quiz);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_quiz);

        Application.FullScreenMode(QuizActivity.this);

        counting_rest = binding.topSection.findViewById(R.id.counting_rest);
        counting_rest.setTextSize(DisplayFontSize.font_size_x_32);

        close_btn = binding.topSection.findViewById(R.id.close_btn);
        close_btn.setTextSize(DisplayFontSize.font_size_x_32);

        binding.confirmBtn.setTextSize(DisplayFontSize.font_size_x_36);

        time_progress = binding.topSection.findViewById(R.id.time_progress);

        quiz_number = binding.quizNumber;
        quiz_text = binding.quizText;
        quiz_text.setTextSize(DisplayFontSize.font_size_x_44);

        answer1 = binding.answer1;
        answer2 = binding.answer2;

        answer_text_1 = binding.answerText1;
        answer_text_1.setTextSize(DisplayFontSize.font_size_x_32);
        answer_text_2 = binding.answerText2;
        answer_text_2.setTextSize(DisplayFontSize.font_size_x_32);

        answer_icon_1 = binding.answerIcon1;
        answer_icon_2 = binding.answerIcon2;

        counting_rest.setText(current_pos+"/"+quiz_size);
        quiz_number.setText("Q"+current_pos+".");

        close_btn.setOnClickListener(v->{
            finish();
        });

        // usedQuestions 리스트 초기화
        usedQuestions.clear(); // 앱 시작 시 사용된 질문 초기화

        // JSON 파일에서 문제를 로드하여 배열에 저장
        loadQuestionFromJson();
        showRandomQuestion();

        binding.confirmBtn.setOnClickListener(v->{
            //TODO : 정답 확인 기능 코드 필요
            /*
            cycle_check = true;

            // 정답이면 right_number++ 추가
            // 우선 확인하면 정답인걸로
            right_number++;

            choice_init();
            binding.confirmBtn.setEnabled(false);
//            current_pos++;
            timeReset();
            timeStart();
//            counting_rest.setText(current_pos+"/"+quiz_size);
//            quiz_number.setText("Q"+current_pos+".");
            Random rd = new Random();
            boolean check = rd.nextBoolean();
            setCorrectBtn(check,answer1, answer_text_1, answer_icon_1);
            setCorrectBtn(!check, answer2, answer_text_2, answer_icon_2);
            */
            if (currentQuestion != null) {
                // 사용자가 선택한 답을 확인
                String selectedAnswer = answer1.isSelected() ? answer_text_1.getText().toString() :
                                        answer2.isSelected() ? answer_text_2.getText().toString() : "";
                // 정답 비교
                Log.d("QuizActivity", "유저가 선택한 정답: " + selectedAnswer);
                Log.d("QuizActivity", "실제 정답: " + currentQuestion.getAnswer());

                if (selectedAnswer.equals(currentQuestion.getAnswer())) {
                    // 정답일 경우
                    right_number++; // 정답 수 증가
                    pauseTime(); // 시간 일시정지

                    dialog = new OxQuizDialog(this, view -> {
                        dialog.dismiss(); // 다이얼로그 닫기
                    }, true);
                } else {
                    // 오답일 경우
                    pauseTime();

                    dialog = new OxQuizDialog(this, view -> {
                        dialog.dismiss();
                    }, false);
                }
                // 다이얼로그 닫힐 때 다음 질문 표시
                dialog.setOnDismissListener(dialogInterface -> {
                    if (current_pos < quiz_size) {
                        current_pos++;
                        counting_rest.setText(current_pos+"/"+quiz_size);
                        quiz_number.setText("Q"+current_pos+".");
                        choice_init();
                        binding.confirmBtn.setEnabled(false);
                        timeReset(); // 시간 리셋
                        timeStart(); // 시간 다시 시작
                        showRandomQuestion(); // 다음 질문 표시
                    } else {
                        // 마지막 질문일 때
                        finish();
                        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                        intent.putExtra("type", 5);
                        intent.putExtra("size", quiz_size);
                        intent.putExtra("rating", right_number);
//                        Toast.makeText(QuizActivity.this, "총 점수는 " + right_number, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
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

        /*
        Random rd = new Random();
        boolean check = rd.nextBoolean();
        setCorrectBtn(check, answer1, answer_text_1, answer_icon_1);
        setCorrectBtn(!check, answer2, answer_text_2, answer_icon_2);
        */

        timeStart();
    }

    // JSON 파일에서 질문 로드하는 메서드
    void loadQuestionFromJson() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("oxquiz.json");

            Gson gson = new Gson();
            questionList = gson.fromJson(new InputStreamReader(inputStream), new TypeToken<List<OxQuiz>>(){}.getType());

            // 각 질문에 대해 drawable ID 설정
            for (OxQuiz question : questionList) {
                int drawableId = getResources().getIdentifier("oxquiz_" + question.getIndex(), "drawable", getPackageName());
                question.drawable = drawableId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 무작위로 질문 표시하는 메서드
    void showRandomQuestion() {
        if (questionList == null || questionList.isEmpty()) return;

        // 사용되지 않은 질문만 남도록 필터링
        List<OxQuiz> availableQuestions = new ArrayList<>(questionList);
        availableQuestions.removeAll(usedQuestions);

        if (availableQuestions.isEmpty()) {
            Log.d("QuizActivity", "모든 질문이 사용되었습니다.");
        }

        // 질문 리스트 섞어 무작위로 질문 선택
        Collections.shuffle(availableQuestions);
        currentQuestion = availableQuestions.get(0);
        usedQuestions.add(currentQuestion); // 현재 질문을 사용된 리스트에 추가

        quiz_text.setText(currentQuestion.getQuestion()); // 질문 텍스트 설정

        //TODO : 인덱스 43번째 문제 이미지 마늘로 나와있음
        // 선택된 이미지 리소스 quiz_img에 설정
        binding.quizImg.setImageResource(currentQuestion.getDrawable());

        // 정답과 오답 버튼 설정
        Random rd = new Random();
        boolean check = rd.nextBoolean();
        setCorrectBtn(check, answer1, answer_text_1, answer_icon_1);
        setCorrectBtn(!check, answer2, answer_text_2, answer_icon_2);
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
            /*
            if (cycle_check) {
                cycle_check = false;

                if (current_pos == quiz_size){
                    // 정답이면 right_number++ 추가
                    finish();
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("type", 5);
                    intent.putExtra("size",quiz_size);
                    intent.putExtra("rating",right_number);
                    startActivity(intent);
                }else{
                    // 정답이면 right_number++ 추가
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
                // 0초가 되면
                if (time.equals("00:00")) {
                    // 타이머 초기화
                    if (current_pos == quiz_size){
                        finish();
                        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                        intent.putExtra("type", 5);
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
            */

            // 타이머가 0초 되어 다음 문제로 이동할 때만 cycle_check 설정
            if (!cycle_check && time.equals("00:00")) { // 중복 방지 위해 cycle_check 확인
                cycle_check = true; // 다음 문제로 넘어가도록 설정

                if (current_pos < quiz_size) {
                    current_pos++;
                    counting_rest.setText(current_pos + "/" + quiz_size);
                    quiz_number.setText("Q" + current_pos +".");
                    choice_init();
                    binding.confirmBtn.setEnabled(false);
                    timeReset();
                    timeStart();
                    showRandomQuestion();
                    cycle_check = false; // 다시 초기화
                } else {
                    finish();
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("type", 5);
                    intent.putExtra("size", quiz_size);
                    intent.putExtra("rating", right_number);
//                    Toast.makeText(QuizActivity.this, "총 점수는 " + right_number, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            } else if (!time.equals("00:00")) { // 시간이 0초가 아니면 타이머 계속 진행
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

    public void pauseTime() {
        pauseTime = SystemClock.elapsedRealtime();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}