package com.corporation8793.dementia.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.game.find_different_color.FindDifferentColorGame;
import com.corporation8793.dementia.game.find_same_thing.FindSameThingGame;
import com.corporation8793.dementia.game.order_number.OrderNumberGame;
import com.corporation8793.dementia.game.quiz.QuizActivity;

public class ResultActivity extends AppCompatActivity {

    TextView score_text, high_score_text;
    Button retry_btn, out_btn, close_btn;

    // type : 게임 유형, rating : 정답 점수, highest_score : 최고 점수
    int type, rating, highest_score;
    String total;

    Intent intent;

    /*
    * TODO: 소영님께서 저번에 만들어두신 다시하기 버튼 기능은 아직 지우지 않았어요
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

        score_text = findViewById(R.id.score_text);
        high_score_text = findViewById(R.id.high_score_text);
        retry_btn = findViewById(R.id.retry_btn);
        out_btn = findViewById(R.id.out_btn);
        close_btn = findViewById(R.id.close_btn);

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
                    break;
                case 3: // 다른 색상 찾기 게임
                    total = rating + "/" + highest_score;
                    Log.e("test", "total : " + total);

                    score_text.setText(rating+"");
                    high_score_text.setText("/" + highest_score);
                    break;
                case 4: // 색상 일치 게임
                    String total = rating + "/" + highest_score;
                    Log.e("test", "total : " + total);

                    score_text.setText(rating+"");
                    high_score_text.setText("/" + highest_score);
                    break;
                case 5: // ox 퀴즈 게임
                    total = rating + "/" + highest_score;
                    Log.e("test", "total : " + total);

                    score_text.setText(rating+"");
                    high_score_text.setText("/" + highest_score);
                    break;
                case 6: // 색상 퍼즐 게임
                    total = rating + "/" + highest_score;
                    Log.e("test", "total : " + total);

                    score_text.setText(rating+"");
                    high_score_text.setText("/" + highest_score);
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