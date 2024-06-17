package com.corporation8793.dementia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageButton;

import com.corporation8793.dementia.chat.ChatActivity;
import com.corporation8793.dementia.databinding.ActivityMainBinding;
import com.corporation8793.dementia.diagnose.QuestionnaireActivity;
import com.corporation8793.dementia.game.SelectGameActivity;
import com.opencsv.exceptions.CsvException;

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

        Init.settingTTS(getApplicationContext());

        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);



        drawerLayout = findViewById(R.id.dl_main);
        menu_btn = findViewById(R.id.menu_btn);



        binding.diagnoseBtn.btn.setOnClickListener(v->{
            Log.e("hello","diagnosebtn");
            Intent intent = new Intent(MainActivity.this, QuestionnaireActivity.class);
            startActivity(intent);
        });

        binding.gameBtn.btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, SelectGameActivity.class);
            startActivity(intent);
        });


        binding.findCenterBtn.btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        });

        binding.chatBtn.btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Init.destroyTTS();
    }
}