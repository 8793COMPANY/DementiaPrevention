
package com.corporation8793.dementia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DataSetting;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Application.getInstance();
        Application.getStandardSize(IntroActivity.this);
        Application.FullScreenMode(IntroActivity.this);

        // 여기에서 글씨 크기 조절하기 / SharedPreferences 사용
        if (MySharedPreferences.getString(IntroActivity.this, "FontSize") == null
                || MySharedPreferences.getString(IntroActivity.this, "FontSize").isEmpty()) { // 처음 시작하는 경우(폰트 크기 미설정)
            // 기본으로 폰트 크기 "크게"로 설정
            Log.e("testtttt", "null");
            MySharedPreferences.setString(IntroActivity.this, "FontSize", "크게");
        } else {
            Log.e("testtttt", "not null");
            Log.e("test~", MySharedPreferences.getString(IntroActivity.this, "FontSize"));
        }
        Application.getFontSize(MySharedPreferences.getString(IntroActivity.this, "FontSize"));

        DataSetting setting = DataSetting.getInstance(getApplicationContext());
        setting.dataCheck();

        try {
            Region.loadData(IntroActivity.this);
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            // 유저 정보 유무 확인
            if (setting.getUserList().isEmpty()) { // 유저 정보가 없는 경우 -> 정보 기입 페이지로 이동
                Intent intent = new Intent(IntroActivity.this, UserInfoActivity.class);
                startActivity(intent);
            } else { // 유저 정보가 있는 경우 -> 메인 페이지로 이동
                // 유저 정보 저장
                Application.setUserData(setting.getUserList().get(0));
                Log.e("test", "사용자 이름 : " + Application.user.name);

                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            }

            finish();
        }, 2000);
    }
}