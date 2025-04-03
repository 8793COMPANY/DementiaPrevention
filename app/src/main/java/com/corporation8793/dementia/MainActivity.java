package com.corporation8793.dementia;

import static android.provider.Settings.Secure.getString;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.corporation8793.dementia.chat.ChatActivity;
import com.corporation8793.dementia.data.DiagnoseList;
import com.corporation8793.dementia.databinding.ActivityMainBinding;
import com.corporation8793.dementia.diagnose.QuestionnaireActivity;
import com.corporation8793.dementia.diagnose.lately.DiagnoseResultListActivity;
import com.corporation8793.dementia.game.FindSameColorAndTextActivity;
import com.corporation8793.dementia.game.SelectGameActivity;
import com.corporation8793.dementia.game.WhacAMoleActivity;
import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DataSetting;
import com.corporation8793.dementia.util.DisplayFontSize;
import com.opencsv.exceptions.CsvException;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    ImageButton menu_btn;
    ProgressBar progressBar;
    Button startBtn, whacAMoleBtn, mapBtn;
    TextView timeText, changeSizeTextView;
    Button seoul, daegu, gwangju;

    ImageView app_name_icon;

    int count = 0;

    long baseTime;
    long setTime;

    // 기본 3초로 설정
    private static final Long SET_TIME = 3L;

    DrawerLayout drawerLayout;
    RadioGroup font_select_section;
    View drawerView;

    List<DiagnoseList> result_all_list = new ArrayList();
    DiagnoseList diagnoseList_lately;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Init.settingTTS(getApplicationContext());

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        // 초기
//        if (!MySharedPreferences.getBoolean(getApplicationContext(),"first_check")){
//            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
//            startActivity(intent);
//        }

//        Application.getInstance();
//        Application.getStandardSize(MainActivity.this);
        Application.FullScreenMode(MainActivity.this);

        result_all_list = DataSetting.getInstance(MainActivity.this).getDiagnoseLists();
        if (!result_all_list.isEmpty()) {
            diagnoseList_lately = result_all_list.get((result_all_list.size() - 1));
            Log.e("test", "diagnoseList_lately : " + diagnoseList_lately);
        }

        drawerLayout = findViewById(R.id.dl_main);
        menu_btn = findViewById(R.id.menu_btn);

        binding.userName.setTextSize(DisplayFontSize.font_size_x_34);
        binding.userName.setText(Application.user.name + "님");

        binding.welcomeText.setTextSize(DisplayFontSize.font_size_x_34);

        binding.latestDate.setTextSize(DisplayFontSize.font_size_x_24);
        binding.latestResult.setTextSize(DisplayFontSize.font_size_x_28);
        if (!result_all_list.isEmpty()) {
            binding.latestDate.setText(diagnoseList_lately.date);
            binding.latestResult.setText(diagnoseList_lately.resultText);
        } else {
            binding.latestDate.setText("현재 기록이 없습니다.");
            binding.latestResult.setText("");
        }


        binding.diagnoseBtn.title.setTextSize(DisplayFontSize.font_size_x_32);
        binding.diagnoseBtn.intro.setTextSize(DisplayFontSize.font_size_x_24);
        binding.gameBtn.title.setTextSize(DisplayFontSize.font_size_x_32);
        binding.gameBtn.intro.setTextSize(DisplayFontSize.font_size_x_24);
        binding.findCenterBtn.title.setTextSize(DisplayFontSize.font_size_x_32);
        binding.findCenterBtn.intro.setTextSize(DisplayFontSize.font_size_x_24);
        binding.chatBtn.title.setTextSize(DisplayFontSize.font_size_x_32);
        binding.chatBtn.intro.setTextSize(DisplayFontSize.font_size_x_24);

//        binding.appNameIcon.setOnClickListener(view -> {
//            DataSetting setting = DataSetting.getInstance(getApplicationContext());
//            setting.dataCheck();
//        });

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
            drawerLayout.openDrawer(GravityCompat.END);
        });

        try {
            Region.loadData(MainActivity.this);
            //MedicalCenter.loadData(MainActivity.this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

        seoul = findViewById(R.id.seoul);
        daegu = findViewById(R.id.daegu);
        gwangju = findViewById(R.id.gwangju);

        Region region = new Region();

        seoul.setOnClickListener(v->{
            region.check_region("서울특별시");
        });

        daegu.setOnClickListener(v->{
            region.check_region("대구광역시");
        });

        gwangju.setOnClickListener(v->{
            region.check_region("경기도");
        });

        progressBar = findViewById(R.id.progressBar);
        startBtn = findViewById(R.id.startBtn);
        whacAMoleBtn = findViewById(R.id.whacAMoleBtn);
        timeText = findViewById(R.id.timeText);
        mapBtn = findViewById(R.id.mapBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeStart();
                //sendMessage();
            }
        });

        whacAMoleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WhacAMoleActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        mapBtn. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        //마이페이지
        drawerView = findViewById(R.id.in_view_drawer);
        changeSizeTextView = drawerView.findViewById(R.id.counting_rest);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_40);

        changeSizeTextView = drawerView.findViewById(R.id.user_name);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_40);
        changeSizeTextView.setText(Application.user.name + "님");

        changeSizeTextView = drawerView.findViewById(R.id.user_info_modify);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_26);
        changeSizeTextView = drawerView.findViewById(R.id.latest_diagnose_list_text);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_28);
        changeSizeTextView = drawerView.findViewById(R.id.logout_text);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_28);

        changeSizeTextView = drawerView.findViewById(R.id.font_text);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_28);
        changeSizeTextView = drawerView.findViewById(R.id.big);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_26);
        changeSizeTextView = drawerView.findViewById(R.id.medium);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_26);
        changeSizeTextView = drawerView.findViewById(R.id.small);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_26);

        font_select_section = drawerView.findViewById(R.id.font_select_section);

        if (MySharedPreferences.getString(MainActivity.this, "FontSize").equals("크게")) {
            ((RadioButton)findViewById(R.id.big)).setChecked(true);
            ((RadioButton)findViewById(R.id.medium)).setChecked(false);
            ((RadioButton)findViewById(R.id.small)).setChecked(false);
        } else if (MySharedPreferences.getString(MainActivity.this, "FontSize").equals("보통")) {
            ((RadioButton)findViewById(R.id.big)).setChecked(false);
            ((RadioButton)findViewById(R.id.medium)).setChecked(true);
            ((RadioButton)findViewById(R.id.small)).setChecked(false);
        } else if (MySharedPreferences.getString(MainActivity.this, "FontSize").equals("작게")) {
            ((RadioButton)findViewById(R.id.big)).setChecked(false);
            ((RadioButton)findViewById(R.id.medium)).setChecked(false);
            ((RadioButton)findViewById(R.id.small)).setChecked(true);
        }

        //font_select_section 여기에서 선택된 글씨 크기에 따라 쉐어드프리퍼런스 값 변경
        font_select_section.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (checkedId == R.id.big) {
                Log.e("test", "크게");
                MySharedPreferences.setString(MainActivity.this, "FontSize", "크게");

                findViewById(R.id.medium).setActivated(false);
                findViewById(R.id.small).setActivated(false);
            }

            if (checkedId == R.id.medium) {
                Log.e("test", "중간");
                MySharedPreferences.setString(MainActivity.this, "FontSize", "중간");

                findViewById(R.id.big).setActivated(false);
                findViewById(R.id.small).setActivated(false);
            }

            if (checkedId == R.id.small) {
                Log.e("test", "작게");
                MySharedPreferences.setString(MainActivity.this, "FontSize", "작게");

                findViewById(R.id.big).setActivated(false);
                findViewById(R.id.medium).setActivated(false);
            }

            applyFontSize();
//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
        });

        drawerLayout.findViewById(R.id.close_btn).setOnClickListener(v->{
            drawerLayout.closeDrawers();
        });

        drawerLayout.findViewById(R.id.user_info_modify_section).setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
            intent.putExtra("type","edit");
            startActivity(intent);
        });

        drawerLayout.findViewById(R.id.latest_diagnose_list_section).setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, DiagnoseResultListActivity.class);
            startActivity(intent);
        });


        drawerLayout.findViewById(R.id.logout).setOnClickListener(v->{
            drawerLayout.closeDrawers();
            Toast.makeText(getApplicationContext(),"로그아웃",Toast.LENGTH_SHORT).show();
        });

//        applyFontSize();
    }

    public void applyFontSize() {
        Log.e("test", "applyFontSize");

        // 여기에서 글씨 크기 변경 사항 적용
        Log.e("Font Size", "test1 : " + DisplayFontSize.font_size_x_20);
        Application.getFontSize(MySharedPreferences.getString(MainActivity.this, "FontSize"));

        Log.e("test", "fontSize : " + Application.fontSize);
        Application.setFontSize();

        // 적용된 값이 디버깅할 때 유효한지 확인 (Log 추가)
        Log.e("Font Size", MySharedPreferences.getString(MainActivity.this, "FontSize"));
        Log.e("Font Size", "test2 : " + DisplayFontSize.font_size_x_20);

        binding.userName.setTextSize(DisplayFontSize.font_size_x_34);
        binding.welcomeText.setTextSize(DisplayFontSize.font_size_x_34);
        binding.latestDate.setTextSize(DisplayFontSize.font_size_x_24);
        binding.latestResult.setTextSize(DisplayFontSize.font_size_x_28);
        binding.diagnoseBtn.title.setTextSize(DisplayFontSize.font_size_x_32);
        binding.diagnoseBtn.intro.setTextSize(DisplayFontSize.font_size_x_24);
        binding.gameBtn.title.setTextSize(DisplayFontSize.font_size_x_32);
        binding.gameBtn.intro.setTextSize(DisplayFontSize.font_size_x_24);
        binding.findCenterBtn.title.setTextSize(DisplayFontSize.font_size_x_32);
        binding.findCenterBtn.intro.setTextSize(DisplayFontSize.font_size_x_24);
        binding.chatBtn.title.setTextSize(DisplayFontSize.font_size_x_32);
        binding.chatBtn.intro.setTextSize(DisplayFontSize.font_size_x_24);

        changeSizeTextView = drawerView.findViewById(R.id.counting_rest);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_40);
        changeSizeTextView = drawerView.findViewById(R.id.user_name);
//        changeSizeTextView.setText("바뀜");
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_40);
        changeSizeTextView = drawerView.findViewById(R.id.user_info_modify);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_26);
        changeSizeTextView = drawerView.findViewById(R.id.latest_diagnose_list_text);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_28);
        changeSizeTextView = drawerView.findViewById(R.id.logout_text);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_28);
        changeSizeTextView = drawerView.findViewById(R.id.font_text);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_28);
        changeSizeTextView = drawerView.findViewById(R.id.big);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_26);
        changeSizeTextView = drawerView.findViewById(R.id.medium);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_26);
        changeSizeTextView = drawerView.findViewById(R.id.small);
        changeSizeTextView.setTextSize(DisplayFontSize.font_size_x_26);

//        binding.getRoot().invalidate();
//        binding.getRoot().requestLayout();
        binding.invalidateAll();

        // 뷰 갱신
//        overridePendingTransition(0, 0);
//        Intent intent = getIntent();
//        finish();
//        startActivity(intent);
//        overridePendingTransition(0, 0);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String time = setTimeOut();

            Log.e("test", time);
            timeText.setText(time);

            // 0초가 되면
            if (time.equals("00:00")) {
                // 타이머 초기화
                timeReset();
            } else {
                // 0초가 아니면
                handler.sendEmptyMessage(0);
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

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("Test", "onResume");

        // 사용자 이름 갱신
        if (Application.user != null) {
            Log.e("Test", "유저 이름 갱신: " + Application.user.name);

            binding.userName.setText(Application.user.name + "님");

            TextView userNameTextView = drawerView.findViewById(R.id.user_name);
            userNameTextView.setText(Application.user.name + "님");
        } else {
            Log.e("Test", "유저 데이터가 없습니다.");
        }

        result_all_list = DataSetting.getInstance(MainActivity.this).getDiagnoseLists();
        if (!result_all_list.isEmpty()) {
            Log.e("Test", "not null");
            Log.e("Test", "not null" + result_all_list);

            diagnoseList_lately = result_all_list.get((result_all_list.size() - 1));
            Log.e("test", "diagnoseList_lately : " + diagnoseList_lately);

            binding.latestDate.setText(diagnoseList_lately.date);
            binding.latestResult.setText(diagnoseList_lately.resultText);
        } else {
            Log.e("Test", "null");
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.END)){
            drawerLayout.closeDrawers();

            Log.e("Test", "onBackPressed");

            result_all_list = DataSetting.getInstance(MainActivity.this).getDiagnoseLists();
            if (!result_all_list.isEmpty()) {
                binding.latestDate.setText(diagnoseList_lately.date);
                binding.latestResult.setText(diagnoseList_lately.resultText);
            }
        }else{
            finish();
        }
    }

    // 시간 시작
    public void timeStart() {
        // 기본 3초로 설정
        setTime(SET_TIME);

        baseTime = SystemClock.elapsedRealtime();

        handler.sendEmptyMessage(0);
    }

    // 시간 초기화
    public void timeReset() {
        //핸들러 메세지 전달 종료
        handler.removeCallbacksAndMessages(null);

        //long 변환한 시간 초기화
        setTime = 0;

        //프로그레스바 프로그레스 초기화
        progressBar.setProgress(0);
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
        progressBar.setProgress((int) ((now - baseTime) + (setTime/1000)));
        // 줄어드는 경우
        //progressBar.setProgress(progressBar.getMax() - ((int) ((now - baseTime) + (setTime/1000))));

        return easy_outTime;
    }

    public void setTime(Long time) {
        // 분단위 : * 1000 * 60 + 초단위 : * 1000
        // 현재는 초 단위만 계산
        setTime = time * 1000;
        progressBar.setMax((int) setTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Init.destroyTTS();
    }
}