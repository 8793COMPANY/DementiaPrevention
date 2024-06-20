package com.corporation8793.dementia.diagnose;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.corporation8793.dementia.R;

public class DiagnosticResultActivity extends AppCompatActivity {

    int rating=0;
    Button close_btn;

    ImageView first, second, third;

    TextView result_score, user_name, result_text;

    Button go_lately_diagnose_list, go_main_activity;
    String msg ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_result);

        rating = getIntent().getIntExtra("rating",0);
        Log.e("rating",rating+"");

        close_btn = findViewById(R.id.close_btn);
        go_lately_diagnose_list = findViewById(R.id.go_lately_diagnose_list);
        go_main_activity = findViewById(R.id.go_main_activity);

        result_score = findViewById(R.id.result_score);

        user_name = findViewById(R.id.user_name);
        result_text = findViewById(R.id.result_text);


        //별
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);


        result_score.setText(rating+"");

        if (rating < 3){
            third.setBackgroundResource(R.drawable.full_star_icon);
            msg = "치매 의심입니다.";
        }else if (rating < 7){
            second.setBackgroundResource(R.drawable.full_star_icon);
            third.setBackgroundResource(R.drawable.full_star_icon);
            msg = "치매 보통입니다.";
        }else{
            first.setBackgroundResource(R.drawable.full_star_icon);
            second.setBackgroundResource(R.drawable.full_star_icon);
            third.setBackgroundResource(R.drawable.full_star_icon);
            msg = "치매 안심입니다.";
        }

        int text_chagne_index= msg.indexOf("입니다");

        SpannableStringBuilder ssb = new SpannableStringBuilder(msg);
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.yellow_ffbb49)), 0, text_chagne_index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        result_text.setText(ssb);

        close_btn.setOnClickListener(v->{
            finish();
        });

        go_lately_diagnose_list.setOnClickListener(v->{
            finish();

        });

        go_main_activity.setOnClickListener(v->{
            finish();
        });
    }
}