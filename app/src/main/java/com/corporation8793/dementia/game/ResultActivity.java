package com.corporation8793.dementia.game;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.corporation8793.dementia.MainActivity;
import com.corporation8793.dementia.R;

public class ResultActivity extends AppCompatActivity {

    TextView score_text;
    Button retry_btn, main_btn;

    int score = 0;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        score_text = findViewById(R.id.score_text);
        retry_btn = findViewById(R.id.retry_btn);
        main_btn = findViewById(R.id.main_btn);

        if (!TextUtils.isEmpty(getIntent().getStringExtra(WhacAMoleActivity.FINAL_SCORE_VALUE_EXTRA))) {
            score = Integer.parseInt(getIntent().getStringExtra(WhacAMoleActivity.FINAL_SCORE_VALUE_EXTRA));
            score_text.setText(String.valueOf(score));

            num = 1;
        } else {
            score = getIntent().getIntExtra("score", -1);
            score_text.setText(String.valueOf(score));

            num = 2;
        }

        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num == 1) {
                    Intent intent = new Intent(ResultActivity.this, WhacAMoleActivity.class);
                    startActivity(intent);
                    finish();
                } else if (num == 2) {
                    Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("test", "error");
                }
            }
        });

        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}