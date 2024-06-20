package com.corporation8793.dementia.diagnose;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.corporation8793.dementia.R;

public class DiagnosticResultActivity extends AppCompatActivity {

    int rating=0;
    Button close_btn;

    ImageView first, second, third;

    Button go_lately_diagnose_list, go_main_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_result);

        rating = getIntent().getIntExtra("rating",0);

        close_btn = findViewById(R.id.close_btn);
        go_lately_diagnose_list = findViewById(R.id.go_lately_diagnose_list);
        go_main_activity = findViewById(R.id.go_main_activity);


        //별
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);

        if (rating < 3){
            third.setBackgroundResource(R.drawable.full_star_icon);
        }else if (rating < 7){
            second.setBackgroundResource(R.drawable.full_star_icon);
        }else{
            first.setBackgroundResource(R.drawable.full_star_icon);
            second.setBackgroundResource(R.drawable.full_star_icon);
            third.setBackgroundResource(R.drawable.full_star_icon);
        }

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