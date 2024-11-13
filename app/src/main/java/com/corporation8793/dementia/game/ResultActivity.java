package com.corporation8793.dementia.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.corporation8793.dementia.MySharedPreferences;
import com.corporation8793.dementia.R;
import com.corporation8793.dementia.game.find_different_color.FindDifferentColorGame;
import com.corporation8793.dementia.game.find_same_thing.FindSameThingGame;
import com.corporation8793.dementia.game.order_number.OrderNumberGame;
import com.corporation8793.dementia.game.quiz.QuizActivity;
import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DisplayFontSize;

public class ResultActivity extends AppCompatActivity {

    TextView score_text, high_score_text, diagnose_result, result_text;
    Button retry_btn, out_btn, close_btn;

    // type : 게임 유형, rating : 정답 점수, highest_score : 최고 점수
    int type, rating, highest_score;
    String total;

    Intent intent;

    /*
    * TODO: 저번에 만들어두신 다시하기 버튼 기능은 아직 지우지 않았어요
    *       뇌 기능 게임 리스트 순서대로
    *       두더지게임 - 1
    *       순서 게임  - 2
    *       이렇게 번호 매겨서 intent 값 보낼테니 분류작업해주세요
    * */

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Application.FullScreenMode(ResultActivity.this);

        diagnose_result = findViewById(R.id.diagnose_result);
        diagnose_result.setTextSize(DisplayFontSize.font_size_x_40);
        result_text = findViewById(R.id.result_text);
        result_text.setTextSize(DisplayFontSize.font_size_x_40);

        score_text = findViewById(R.id.score_text);
        score_text.setTextSize(DisplayFontSize.font_size_x_92);

        high_score_text = findViewById(R.id.high_score_text);
        high_score_text.setTextSize(DisplayFontSize.font_size_x_66);

        retry_btn = findViewById(R.id.retry_btn);
        retry_btn.setTextSize(DisplayFontSize.font_size_x_36);
        out_btn = findViewById(R.id.out_btn);
        out_btn.setTextSize(DisplayFontSize.font_size_x_36);

        close_btn = findViewById(R.id.close_btn);
        close_btn.setTextSize(DisplayFontSize.font_size_x_32);

        // 게임 유형
        type = getIntent().getIntExtra("type", 0);
        // 정답 수
        rating = getIntent().getIntExtra("rating",0);
        // 최고 점수
        highest_score = getIntent().getIntExtra("size",0); //최고점은 없을 수 있습니다

        Log.e("test", "type : " + type);
        Log.e("test", "rating : " + rating);
        Log.e("test", "highest_score : " + highest_score);

        if (type != 0) {
            switch (type) {
                case 1: // 두더지 게임
                    score_text.setText(rating+"");
                    high_score_text.setText("");
                    break;
                case 2: // 순서 게임
                    total = rating + "/" + highest_score;
                    Log.e("test", "total : " + total);

                    score_text.setText(rating+"");
                    high_score_text.setText("/" + highest_score);

                    // 점수 저장
                    if (MySharedPreferences.getString(ResultActivity.this, "scoreOtherNumber") == null
                            || MySharedPreferences.getString(ResultActivity.this, "scoreOtherNumber").isEmpty()) { // 처음 시작하는 경우
                        // 바로 점수 저장
                        Log.e("testtttt", "null");
                        MySharedPreferences.setString(ResultActivity.this, "scoreOtherNumber", String.valueOf(rating));
                    } else {
                        // 이전 점수가 있는 경우 비교해서 저장
                        Log.e("testtttt", "not null");
                        Log.e("test~", MySharedPreferences.getString(ResultActivity.this, "scoreOtherNumber"));

                        int highScore = Integer.parseInt(MySharedPreferences.getString(ResultActivity.this, "scoreOtherNumber"));

                        Log.e("testtttt", "highScore : " + highScore);
                        Log.e("testtttt", "score : " + rating);

                        if (highScore < rating) {
                            Log.e("testtttt", "high");
                            MySharedPreferences.setString(ResultActivity.this, "scoreOtherNumber", String.valueOf(rating));
                        } else {
                            Log.e("testtttt", "low");
                        }
                    }
                    break;
                case 3: // 다른 색상 찾기 게임
                    total = rating + "/" + highest_score;
                    Log.e("test", "total : " + total);

                    score_text.setText(rating+"");
                    high_score_text.setText("/" + highest_score);

                    // 점수 저장
                    if (MySharedPreferences.getString(ResultActivity.this, "scoreFindDifferentColor") == null
                            || MySharedPreferences.getString(ResultActivity.this, "scoreFindDifferentColor").isEmpty()) { // 처음 시작하는 경우
                        // 바로 점수 저장
                        Log.e("testtttt", "null");
                        MySharedPreferences.setString(ResultActivity.this, "scoreFindDifferentColor", String.valueOf(rating));
                    } else {
                        // 이전 점수가 있는 경우 비교해서 저장
                        Log.e("testtttt", "not null");
                        Log.e("test~", MySharedPreferences.getString(ResultActivity.this, "scoreFindDifferentColor"));

                        int highScore = Integer.parseInt(MySharedPreferences.getString(ResultActivity.this, "scoreFindDifferentColor"));

                        Log.e("testtttt", "highScore : " + highScore);
                        Log.e("testtttt", "score : " + rating);

                        if (highScore < rating) {
                            Log.e("testtttt", "high");
                            MySharedPreferences.setString(ResultActivity.this, "scoreFindDifferentColor", String.valueOf(rating));
                        } else {
                            Log.e("testtttt", "low");
                        }
                    }
                    break;
                case 4: // 색상 일치 게임
                    String total = rating + "/" + highest_score;
                    Log.e("test", "total : " + total);

                    score_text.setText(rating+"");
                    high_score_text.setText("/" + highest_score);

                    // 점수 저장
                    if (MySharedPreferences.getString(ResultActivity.this, "scoreFindSameColorAndText") == null
                            || MySharedPreferences.getString(ResultActivity.this, "scoreFindSameColorAndText").isEmpty()) { // 처음 시작하는 경우
                        // 바로 점수 저장
                        Log.e("testtttt", "null");
                        MySharedPreferences.setString(ResultActivity.this, "scoreFindSameColorAndText", String.valueOf(rating));
                    } else {
                        // 이전 점수가 있는 경우 비교해서 저장
                        Log.e("testtttt", "not null");
                        Log.e("test~", MySharedPreferences.getString(ResultActivity.this, "scoreFindSameColorAndText"));

                        int highScore = Integer.parseInt(MySharedPreferences.getString(ResultActivity.this, "scoreFindSameColorAndText"));

                        Log.e("testtttt", "highScore : " + highScore);
                        Log.e("testtttt", "score : " + rating);

                        if (highScore < rating) {
                            Log.e("testtttt", "high");
                            MySharedPreferences.setString(ResultActivity.this, "scoreFindSameColorAndText", String.valueOf(rating));
                        } else {
                            Log.e("testtttt", "low");
                        }
                    }
                    break;
                case 5: // ox 퀴즈 게임
                    total = rating + "/" + highest_score;
                    Log.e("test", "total : " + total);

                    score_text.setText(rating+"");
                    high_score_text.setText("/" + highest_score);

                    // 점수 저장
                    if (MySharedPreferences.getString(ResultActivity.this, "scoreQuiz") == null
                            || MySharedPreferences.getString(ResultActivity.this, "scoreQuiz").isEmpty()) { // 처음 시작하는 경우
                        // 바로 점수 저장
                        Log.e("testtttt", "null");
                        MySharedPreferences.setString(ResultActivity.this, "scoreQuiz", String.valueOf(rating));
                    } else {
                        // 이전 점수가 있는 경우 비교해서 저장
                        Log.e("testtttt", "not null");
                        Log.e("test~", MySharedPreferences.getString(ResultActivity.this, "scoreQuiz"));

                        int highScore = Integer.parseInt(MySharedPreferences.getString(ResultActivity.this, "scoreQuiz"));

                        Log.e("testtttt", "highScore : " + highScore);
                        Log.e("testtttt", "score : " + rating);

                        if (highScore < rating) {
                            Log.e("testtttt", "high");
                            MySharedPreferences.setString(ResultActivity.this, "scoreQuiz", String.valueOf(rating));
                        } else {
                            Log.e("testtttt", "low");
                        }
                    }
                    break;
                case 6: // 색상 퍼즐 게임
                    total = rating + "/" + highest_score;
                    Log.e("test", "total : " + total);

                    score_text.setText(rating+"");
                    high_score_text.setText("/" + highest_score);

                    // 점수 저장
                    if (MySharedPreferences.getString(ResultActivity.this, "scoreFindSameThing") == null
                            || MySharedPreferences.getString(ResultActivity.this, "scoreFindSameThing").isEmpty()) { // 처음 시작하는 경우
                        // 바로 점수 저장
                        Log.e("testtttt", "null");
                        MySharedPreferences.setString(ResultActivity.this, "scoreFindSameThing", String.valueOf(rating));
                    } else {
                        // 이전 점수가 있는 경우 비교해서 저장
                        Log.e("testtttt", "not null");
                        Log.e("test~", MySharedPreferences.getString(ResultActivity.this, "scoreFindSameThing"));

                        int highScore = Integer.parseInt(MySharedPreferences.getString(ResultActivity.this, "scoreFindSameThing"));

                        Log.e("testtttt", "highScore : " + highScore);
                        Log.e("testtttt", "score : " + rating);

                        if (highScore < rating) {
                            Log.e("testtttt", "high");
                            MySharedPreferences.setString(ResultActivity.this, "scoreFindSameThing", String.valueOf(rating));
                        } else {
                            Log.e("testtttt", "low");
                        }
                    }
                    break;
                default:
                    break;
            }
        } else {
            Log.e("test", "type 0");
        }

        // 다시하기 버튼
        retry_btn.setOnClickListener(view -> {
            switch (type) {
                case 1: // 두더지 게임
                    intent = new Intent(ResultActivity.this, WhacAMoleActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2: // 순서 게임
                    intent = new Intent(ResultActivity.this, OrderNumberGame.class);
                    startActivity(intent);
                    finish();
                    break;
                case 3: // 다른 색상 찾기 게임
                    intent = new Intent(ResultActivity.this, FindDifferentColorGame.class);
                    startActivity(intent);
                    finish();
                    break;
                case 4: // 색상 일치 게임
                    intent = new Intent(ResultActivity.this, FindSameColorAndTextActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 5: // ox 퀴즈 게임
                    intent = new Intent(ResultActivity.this, QuizActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 6: // 색상 퍼즐 게임
                    intent = new Intent(ResultActivity.this, FindSameThingGame.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        });

        // 나가기 버튼
        out_btn.setOnClickListener(view -> {
            finish();
        });

        // 닫기 버튼
        close_btn.setOnClickListener(v->{
            finish();
        });
    }
}