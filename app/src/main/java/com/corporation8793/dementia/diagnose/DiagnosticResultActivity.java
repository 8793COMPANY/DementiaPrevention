package com.corporation8793.dementia.diagnose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.corporation8793.dementia.MainActivity;
import com.corporation8793.dementia.R;
import com.corporation8793.dementia.data.DiagnoseList;
import com.corporation8793.dementia.diagnose.lately.DiagnoseResultListActivity;
import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DataSetting;
import com.corporation8793.dementia.util.DisplayFontSize;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiagnosticResultActivity extends AppCompatActivity {

    int rating=0;
    int total_score = 0;
    Button close_btn;

    ProgressBar progressBar;

    ImageView first, second, third;

    TextView result_score, user_name, result_text, diagnose_result, score;

    Button go_lately_diagnose_list, go_main_activity;
    String msg ="";

    DiagnoseList diagnoseList;
    List<DiagnoseList> diagnoseLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_result);

        Application.FullScreenMode(DiagnosticResultActivity.this);

        rating = getIntent().getIntExtra("rating",0);
        Log.e("rating",rating+"");

        total_score = getIntent().getIntExtra("total_score",0);
        Log.e("total_score",total_score+"");

        diagnose_result = findViewById(R.id.diagnose_result);
        diagnose_result.setTextSize(DisplayFontSize.font_size_x_38);
        score = findViewById(R.id.score);
        score.setTextSize(DisplayFontSize.font_size_x_50);

        close_btn = findViewById(R.id.close_btn);
        close_btn.setTextSize(DisplayFontSize.font_size_x_30);

        go_lately_diagnose_list = findViewById(R.id.go_lately_diagnose_list);
        go_lately_diagnose_list.setTextSize(DisplayFontSize.font_size_x_36);

        go_main_activity = findViewById(R.id.go_main_activity);
        go_main_activity.setTextSize(DisplayFontSize.font_size_x_36);

        result_score = findViewById(R.id.result_score);
        result_score.setTextSize(DisplayFontSize.font_size_x_70);

        user_name = findViewById(R.id.user_name);
        user_name.setTextSize(DisplayFontSize.font_size_x_44);

        result_text = findViewById(R.id.result_text);
        result_text.setTextSize(DisplayFontSize.font_size_x_44);

        progressBar = findViewById(R.id.diagnose_result_progress);

        //별
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);


//        result_score.setText(rating+"");
        result_score.setText(total_score+"");

//        if (rating < 3){
//            third.setBackgroundResource(R.drawable.full_star_icon);
//            msg = "치매 의심입니다.";
//        }else if (rating < 7){
//            second.setBackgroundResource(R.drawable.full_star_icon);
//            third.setBackgroundResource(R.drawable.full_star_icon);
//            msg = "치매 보통입니다.";
//        }else{
//            first.setBackgroundResource(R.drawable.full_star_icon);
//            second.setBackgroundResource(R.drawable.full_star_icon);
//            third.setBackgroundResource(R.drawable.full_star_icon);
//            msg = "치매 안심입니다.";
//        }

        if (total_score < 26){
            third.setBackgroundResource(R.drawable.full_star_icon);
            progressBar.setProgress(33);
            msg = "치매 의심입니다.";
        }else if (total_score < 35){
            second.setBackgroundResource(R.drawable.full_star_icon);
            third.setBackgroundResource(R.drawable.full_star_icon);
            progressBar.setProgress(66);
            msg = "치매 보통입니다.";
        }else{
            first.setBackgroundResource(R.drawable.full_star_icon);
            second.setBackgroundResource(R.drawable.full_star_icon);
            third.setBackgroundResource(R.drawable.full_star_icon);
            progressBar.setProgress(100);
            msg = "치매 안심입니다.";
        }

        // 여기에 디비 저장
        // 현재 날짜 가져오기
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String total_day = year + "년 " + month + "월 " + day + "일";

        diagnoseList = new DiagnoseList(total_day, msg, total_score);
        DataSetting.getInstance(DiagnosticResultActivity.this).insert_diagnose_list_data(diagnoseList);


        int text_chagne_index= msg.indexOf("입니다");

        SpannableStringBuilder ssb = new SpannableStringBuilder(msg);
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.yellow_ffbb49)), 0, text_chagne_index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        result_text.setText(ssb);

        close_btn.setOnClickListener(v->{
            finish();
        });

        go_lately_diagnose_list.setOnClickListener(v->{

            finish();
            Intent intent = new Intent(DiagnosticResultActivity.this, DiagnoseResultListActivity.class);
            startActivity(intent);
        });

        go_main_activity.setOnClickListener(v->{
            finish();
        });
    }
}