package com.corporation8793.dementia;

import static android.provider.Settings.Secure.getString;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.corporation8793.dementia.databinding.ActivityMainBinding;
import com.corporation8793.dementia.databinding.HomeFuncBtnLayoutBinding;
import com.corporation8793.dementia.game.OrderNumberGame;
import com.corporation8793.dementia.game.WhacAMoleActivity;
import com.opencsv.exceptions.CsvException;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageButton menu_btn;

    int count = 0;

    long baseTime;
    long setTime;

    // 기본 3초로 설정
    private static final Long SET_TIME = 3L;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);



        drawerLayout = findViewById(R.id.dl_main);
        menu_btn = findViewById(R.id.menu_btn);



        binding.diagnoseBtn.btn.setOnClickListener(v->{
            Log.e("hello","diagnosebtn");
            Intent intent = new Intent(MainActivity.this, QuestionnaireActivity.class);
            startActivity(intent);
        });

        binding.gameBtn.btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, OrderNumberGame.class);
            startActivity(intent);
        });


        binding.findCenterBtn.btn.setOnClickListener(v->{

        });

        binding.chatBtn.btn.setOnClickListener(v->{

        });


        menu_btn.setOnClickListener(v->{
            drawerLayout.openDrawer(Gravity.END);
        });

        try {
            Region.loadData(MainActivity.this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }



    }

}