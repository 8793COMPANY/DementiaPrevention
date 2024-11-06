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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.corporation8793.dementia.Init;
import com.corporation8793.dementia.R;
import com.corporation8793.dementia.data.Question;
import com.corporation8793.dementia.databinding.ActivityQuestionnaireBinding;
import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DataSetting;
import com.corporation8793.dementia.util.DisplayFontSize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    ImageView ready_view;

    // 총 문항 수 : 20
    int quiz_size = 20;
//    int quiz_size = 8;

    int current_pos = 1;

    ArrayList<Diagnose> questionnaire_list = new ArrayList<>();

    // 문제 리스트 가져오기
    List<Question> questionList = new ArrayList<>();
    List<Question> orientation_time = new ArrayList<>();
    List<Question> orientation_place = new ArrayList<>();
    List<Question> orientation_person = new ArrayList<>();
    List<Question> memory = new ArrayList<>();
    List<Question> behavior_change = new ArrayList<>();
    List<Question> emotional_change = new ArrayList<>();
    List<Question> picture = new ArrayList<>();
    List<Question> four_arithmetic_operations = new ArrayList<>();
    List<Question> linguistic_reasoning = new ArrayList<>();
    List<Question> picture_inference = new ArrayList<>();

    List<Integer> orientation_time_num = new ArrayList<>();
    List<Integer> orientation_place_num = new ArrayList<>();
    List<Integer> orientation_person_num = new ArrayList<>();
    List<Integer> memory_num = new ArrayList<>();
    List<Integer> behavior_change_num = new ArrayList<>();
    List<Integer> emotional_change_num = new ArrayList<>();
    List<Integer> picture_num = new ArrayList<>();
    List<Integer> four_arithmetic_operations_num = new ArrayList<>();
    List<Integer> linguistic_reasoning_num = new ArrayList<>();
    List<Integer> picture_inference_num = new ArrayList<>();

    List<Integer> orientation_time_numbers = new ArrayList<>();
    List<Integer> orientation_place_numbers = new ArrayList<>();
    List<Integer> orientation_person_numbers = new ArrayList<>();
    List<Integer> memory_numbers = new ArrayList<>();
    List<Integer> behavior_change_numbers = new ArrayList<>();
    List<Integer> emotional_change_numbers = new ArrayList<>();
    List<Integer> picture_numbers = new ArrayList<>();
    List<Integer> four_arithmetic_operations_numbers = new ArrayList<>();
    List<Integer> linguistic_reasoning_numbers = new ArrayList<>();
    List<Integer> picture_inference_numbers = new ArrayList<>();

    String [] titles = {"음주는 얼마나 자주하시나요?","약 먹는 시간을\n여러 번 놓친 적이 있다.","자신의 전화번호를 모른다.","자신의 주소를 모른다.","글을 읽고 쉽게 문맥을\n파악하기 어려운 적이 있다.",
    "5분 전에 무슨 일이 있었는지\n기억하지 못한다.","갈수록 말수가 감소하는\n경향이 있다.","자주 가던 공간이 낯설다고\n느낀 적이 있다."};

    int [] drawables = {R.drawable.questionnaire_icon1,R.drawable.questionnaire_icon2,R.drawable.questionnaire_icon3,R.drawable.questionnaire_icon4,
            R.drawable.questionnaire_icon5,R.drawable.questionnaire_icon6,R.drawable.questionnaire_icon7,R.drawable.questionnaire_icon8};

    // 3(3지 선다) - 지남력(시간), 지남력(장소), 지남력(사람), 기억력, 행동 변화, 정서적 변화
    // 4(4지 선다/텍스트) - 그림 문제
    // 5(주관식) - 사칙연산 문제, 언어추론 문제
    // 6(4지 선다/그림) - 그림추론 문제
    int [] choice_count ={3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                            3, 3, 3, 3, 4, 5, 5, 5, 6};

    public String [] answer_list = {"하지않음", "아니요", "아니요", "아니요", "아니요", "아니요", "아니요", "아니요", "아니요", "아니요",
                                    "하지않음", "아니요", "아니요", "아니요", "아니요", "아니요", "아니요", "아니요", "아니요", "아니요"};

    int answer_count = 0;
    static String current_answer = "";

    int total_score = 0;

    QuestionnaireDialog questionnaireDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_questionnaire);
        fragmentManager = getSupportFragmentManager();
        ActivityQuestionnaireBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_questionnaire);

        Application.FullScreenMode(QuestionnaireActivity.this);

        next_end_btn = binding.nextEndBtn;
        next_end_btn.setTextSize(DisplayFontSize.font_size_x_36);

        time_progress = binding.topSection.findViewById(R.id.time_progress);

        counting_rest = binding.topSection.findViewById(R.id.counting_rest);
        close_btn = binding.topSection.findViewById(R.id.close_btn);
        time_progress = binding.topSection.findViewById(R.id.time_progress);

        counting_rest.setTextSize(DisplayFontSize.font_size_x_32);
        close_btn.setTextSize(DisplayFontSize.font_size_x_32);

        ready_view = binding.readyView.findViewById(R.id.ready_view);

        textToSpeech  = Init.textToSpeech;

        questionList = DataSetting.getInstance(getBaseContext()).getQuestionList();

        questionnaireDialog = new QuestionnaireDialog(QuestionnaireActivity.this, dialog_listener);

        Window window = questionnaireDialog.getWindow();
        if (window != null) {
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }

        questionnaireDialog.show();
        questionnaireDialog.setCancelable(false);

        int x = (int) (Application.displaySize_X * 0.928);
        int y = (int) (Application.displaySize_Y * 0.35);

        window.setLayout(x, y);

        // 문제 종류별로 분류
        for (int i = 0; i < questionList.size(); i++) {
            switch (questionList.get(i).getType()) {
                case "지남력(시간)":
                    orientation_time.add(questionList.get(i));
                    break;
                case "지남력(장소)":
                    orientation_place.add(questionList.get(i));
                    break;
                case "지남력(사람)":
                    orientation_person.add(questionList.get(i));
                    break;
                case "기억력":
                    memory.add(questionList.get(i));
                    break;
                case "행동변화":
                    behavior_change.add(questionList.get(i));
                    break;
                case "정서적변화":
                    emotional_change.add(questionList.get(i));
                    break;
                case "그림 문제":
                    picture.add(questionList.get(i));
                    break;
                case "사칙연산 문제":
                    four_arithmetic_operations.add(questionList.get(i));
                    break;
                case "언어추론 문제":
                    linguistic_reasoning.add(questionList.get(i));
                    break;
                case "그림추론 문제":
                    picture_inference.add(questionList.get(i));
                    break;
            }
        }

        Log.e("test", orientation_time.size()+"");
        Log.e("test", orientation_place.size()+"");
        Log.e("test", orientation_person.size()+"");
        Log.e("test", memory.size()+"");
        Log.e("test", behavior_change.size()+"");
        Log.e("test", emotional_change.size()+"");
        Log.e("test", picture.size()+"");
        Log.e("test", four_arithmetic_operations.size()+"");
        Log.e("test", linguistic_reasoning.size()+"");
        Log.e("test", picture_inference.size()+"");

        // 문제 랜덤으로 뽑기
        for (int i = 1; i <= 61; i++) {
            orientation_time_num.add(i);
        }
        Collections.shuffle(orientation_time_num);
        orientation_time_numbers = orientation_time_num.subList(0, 3);
        Log.e("test", "orientation_time_numbers : " + orientation_time_numbers);

        for (int i = 62; i <= 87; i++) {
            orientation_place_num.add(i);
        }
        Collections.shuffle(orientation_place_num);
        orientation_place_numbers = orientation_place_num.subList(0, 3);
        Log.e("test", "orientation_place_numbers : " + orientation_place_numbers);

        for (int i = 88; i <= 109; i++) {
            orientation_person_num.add(i);
        }
        Collections.shuffle(orientation_person_num);
        orientation_person_numbers = orientation_person_num.subList(0, 2);
        Log.e("test", "orientation_person_numbers : " + orientation_person_numbers);

        for (int i = 110; i <= 166; i++) {
            memory_num.add(i);
        }
        Collections.shuffle(memory_num);
        memory_numbers = memory_num.subList(0, 3);
        Log.e("test", "memory_numbers : " + memory_numbers);

        for (int i = 167; i <= 247; i++) {
            behavior_change_num.add(i);
        }
        Collections.shuffle(behavior_change_num);
        behavior_change_numbers = behavior_change_num.subList(0, 2);
        Log.e("test", "behavior_change_numbers : " + behavior_change_numbers);

        for (int i = 248; i <= 329; i++) {
            emotional_change_num.add(i);
        }
        Collections.shuffle(emotional_change_num);
        emotional_change_numbers = emotional_change_num.subList(0, 2);
        Log.e("test", "emotional_change_numbers : " + emotional_change_numbers);

        for (int i = 330; i <= 347; i++) {
            picture_num.add(i);
        }
        Collections.shuffle(picture_num);
        picture_numbers = picture_num.subList(0, 1);
        Log.e("test", "picture_numbers : " + picture_numbers);

        for (int i = 348; i <= 357; i++) {
            four_arithmetic_operations_num.add(i);
        }
        Collections.shuffle(four_arithmetic_operations_num);
        four_arithmetic_operations_numbers = four_arithmetic_operations_num.subList(0, 2);
        Log.e("test", "four_arithmetic_operations_numbers : " + four_arithmetic_operations_numbers);

        for (int i = 358; i <= 363; i++) {
            linguistic_reasoning_num.add(i);
        }
        Collections.shuffle(linguistic_reasoning_num);
        linguistic_reasoning_numbers = linguistic_reasoning_num.subList(0, 1);
        Log.e("test", "linguistic_reasoning_numbers : " + linguistic_reasoning_numbers);

        for (int i = 364; i <= 368; i++) {
            picture_inference_num.add(i);
        }
        Collections.shuffle(picture_inference_num);
        picture_inference_numbers = picture_inference_num.subList(0, 1);
        Log.e("test", "picture_inference_numbers : " + picture_inference_numbers);

        counting_rest.setText(current_pos+"/"+quiz_size);

        for (int i=0; i< quiz_size; i++){
//            Diagnose diagnose = new Diagnose(titles[i], drawables[i], choice_count[i]);
            Diagnose diagnose;

            if (i < 8) {
                diagnose = new Diagnose(questionList.get(i).getContents(), drawables[i], choice_count[i]);
            } else {
                diagnose = new Diagnose(questionList.get(i).getContents(), drawables[7], choice_count[i]);
            }

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

        // 원래 코드
//        if (savedInstanceState == null) {
//            question_text = "음주는 얼마나 자주하시나요?";
//            Log.e("error","first in");
//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .add(R.id.fragment_container_view, new ThreeChoiceFragment(current_pos+"",questionnaire_list.get(current_pos-1)), null)
//                    .commit();
////            speakText(question_text);
//
//        }

        // current_pos == 1 일때(첫번째 문제)
//        if (savedInstanceState == null) {
//            Log.e("error","first in");
//
//            String imageName = "orientation_time_" + orientation_time_numbers.get(0);
////            String imageName = "orientation_time_" + (Integer.parseInt(orientation_time.get(orientation_time_numbers.get(0)).getNum()) - 1);
////            Log.e("test", orientation_time.get(orientation_time_numbers.get(0)).getNum());
//            Log.e("test", orientation_time_numbers.get(0)+"");
//            Log.e("test", imageName);
//
//            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());
//
//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .add(R.id.fragment_container_view, new ThreeSelectFragment(orientation_time.get((orientation_time_numbers.get(0) - 1)), imageResource))
//                    .commit();
////            String imageName = "orientation_time_11";
////            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());
////
////            getSupportFragmentManager().beginTransaction()
////                    .setReorderingAllowed(true)
////                    .add(R.id.fragment_container_view, new ThreeSelectFragment(orientation_time.get(10), imageResource))
////                    .commit();
//        }

        binding.topSection.findViewById(R.id.close_btn).setOnClickListener(v->{
            finish();
        });

        next_end_btn.setOnClickListener(v->{
            Log.e("check value answer_list", answer_list[current_pos-1]);
            Log.e("check value current_answer", current_answer);

            next_end_btn.setEnabled(false);

            // 정답 체크(점수)
            if (answer_list[current_pos-1].equals(current_answer)){

                answer_count++;
            }

            if (current_pos -1 >= 19){
                Log.e("test", "total_score! : " + total_score);
                timeReset();
                Intent intent = new Intent(QuestionnaireActivity.this, DiagnosticResultActivity.class);
                intent.putExtra("rating",answer_count);
                intent.putExtra("total_score", total_score);
                startActivity(intent);
                finish();
            }else{
                current_pos++;
                timeReset();
                transaction_fragment();
                counting_rest.setText(current_pos+"/"+quiz_size);
                next_end_btn.setEnabled(false);
                if (current_pos == 20)
                    next_end_btn.setText("제출");
            }

//            next_end_btn.setEnabled(true);
        });
    }

    private final View.OnClickListener dialog_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.e("error","first in");

            String imageName = "orientation_time_" + orientation_time_numbers.get(0);
//            String imageName = "orientation_time_" + (Integer.parseInt(orientation_time.get(orientation_time_numbers.get(0)).getNum()) - 1);
//            Log.e("test", orientation_time.get(orientation_time_numbers.get(0)).getNum());
            Log.e("test", orientation_time_numbers.get(0)+"");
            Log.e("test", imageName);

            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, new ThreeSelectFragment(orientation_time.get((orientation_time_numbers.get(0) - 1)), imageResource))
                    .commit();

            questionnaireDialog.dismiss();
            ready_view.setVisibility(View.GONE);
        }
    };

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

        total_score = 0;
        textToSpeech.stop();
    }

    void transaction_fragment(){
        Log.e("test", "current_pos : " + current_pos);
        Log.e("test", "total_score : " + total_score);
        next_end_btn.setEnabled(false);

        if (current_pos == 2) {
            String imageName = "orientation_time_" + orientation_time_numbers.get(1);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(orientation_time.get((orientation_time_numbers.get(1) - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 3) {
            String imageName = "orientation_time_" + orientation_time_numbers.get(2);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(orientation_time.get((orientation_time_numbers.get(2) - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 4) {
            String imageName = "orientation_place_" + orientation_place_numbers.get(0);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(orientation_place.get((orientation_place_numbers.get(0) - 61 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 5) {
            String imageName = "orientation_place_" + orientation_place_numbers.get(1);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(orientation_place.get((orientation_place_numbers.get(1) - 61 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 6) {
            String imageName = "orientation_place_" + orientation_place_numbers.get(2);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(orientation_place.get((orientation_place_numbers.get(2) - 61 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 7) {
            String imageName = "orientation_person_" + orientation_person_numbers.get(0);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(orientation_person.get((orientation_person_numbers.get(0) - 87 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 8) {
            String imageName = "orientation_person_" + orientation_person_numbers.get(1);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(orientation_person.get((orientation_person_numbers.get(1) - 87 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 9) {
            String imageName = "memory_" + memory_numbers.get(0);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(memory.get((memory_numbers.get(0) - 109 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 10) {
            String imageName = "memory_" + memory_numbers.get(1);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(memory.get((memory_numbers.get(1) - 109 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 11) {
            String imageName = "memory_" + memory_numbers.get(2);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(memory.get((memory_numbers.get(2) - 109 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 12) {
            String imageName = "behavior_change_" + behavior_change_numbers.get(0);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(behavior_change.get((behavior_change_numbers.get(0) - 166 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 13) {
            String imageName = "behavior_change_" + behavior_change_numbers.get(1);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(behavior_change.get((behavior_change_numbers.get(1) - 166 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 14) {
            String imageName = "emotional_change_" + emotional_change_numbers.get(0);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(emotional_change.get((emotional_change_numbers.get(0) - 247 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 15) {
            String imageName = "emotional_change_" + emotional_change_numbers.get(1);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new ThreeSelectFragment(emotional_change.get((emotional_change_numbers.get(1) - 247 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 16) {
            String imageName = "picture_" + picture_numbers.get(0);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new FourSelectTextFragment(picture.get((picture_numbers.get(0) - 329 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 17) {
            String imageName = "four_arithmetic_operations_" + four_arithmetic_operations_numbers.get(0);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new InputAnswerFragment(four_arithmetic_operations.get((four_arithmetic_operations_numbers.get(0) - 347 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 18) {
            String imageName = "four_arithmetic_operations_" + four_arithmetic_operations_numbers.get(1);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new InputAnswerFragment(four_arithmetic_operations.get((four_arithmetic_operations_numbers.get(1) - 347 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 19) {
            String imageName = "linguistic_reasoning_" + linguistic_reasoning_numbers.get(0);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new InputAnswerFragment(linguistic_reasoning.get((linguistic_reasoning_numbers.get(0) - 357 - 1)), imageResource))
                    .commitAllowingStateLoss();
        } else if (current_pos == 20) {
            String imageName = "picture_inference_" + picture_inference_numbers.get(0);
            int imageResource = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, new FourSelectImageFragment(picture_inference.get((picture_inference_numbers.get(0) - 363 - 1)), imageResource, getApplicationContext()))
                    .commitAllowingStateLoss();
        }

        // 원래 코드
//        if (questionnaire_list.get(current_pos -1).choice_count == 2){
//            fragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container_view, new TwoChoiceFragment(current_pos+"",questionnaire_list.get(current_pos-1)), null)
//                    .commitAllowingStateLoss();
//        }else{
//            fragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container_view, new ThreeChoiceFragment(current_pos+"",questionnaire_list.get(current_pos-1)), null)
//                    .commitAllowingStateLoss();
//        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String time = setTimeOut();

//            Log.e("test", time);
//            timeText.setText(time);

            // 0초가 되면
            if (time.equals("00:00")) {
                // 타이머 초기화
                if (current_pos == quiz_size){
                    Log.e("check","time");
                    Log.e("test", "total_score!! : " + total_score);
                    timeReset();
                    Intent intent = new Intent(QuestionnaireActivity.this, DiagnosticResultActivity.class);
                    intent.putExtra("rating",answer_count);
                    intent.putExtra("total_score", total_score);
                    startActivity(intent);
                    finish();
                }else{
                    current_pos++;
                    timeReset();
                    transaction_fragment();
                    counting_rest.setText(current_pos+"/"+quiz_size);
                    next_end_btn.setEnabled(false);
                    if (current_pos == 20)
                        next_end_btn.setText("제출");
                }
            } else {
                // 0초가 아니면
                if (handler != null) {
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
}