package com.corporation8793.dementia.game;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.corporation8793.dementia.game.pulse_countdown.OnCountdownCompleted;
import com.corporation8793.dementia.game.pulse_countdown.PulseCountDown;
import com.corporation8793.dementia.R;

import java.util.ArrayList;
import java.util.Random;

public class WhacAMoleActivity extends AppCompatActivity {

    TextView countDownTimerText, scoreText;
    //    ImageButton firstMole, secondMole, thirdMole, fourthMole, fifthMole, sixthMole, seventhMole, eighthMole, ninethMole;
    ImageView firstMole, secondMole, thirdMole, fourthMole, fifthMole, sixthMole, seventhMole, eighthMole, ninethMole;

    ArrayList<Integer> availableMoleIdArrayList = new ArrayList<>();
    int[] moleIdArray = { R.id.firstMoleBtn, R.id.secondMoleBtn, R.id.thirdMoleBtn,
            R.id.fourthMoleBtn, R.id.fifthMoleBtn, R.id.sixthMoleBtn,
            R.id.seventhMoleBtn, R.id.eigthMoleBtn, R.id.ninethMoleBtn };

    private static final int GAME_TIME = 30000;

    PulseCountDown startGameCountDownTimer;
    CountDownTimer mainCountDownTimer;

    boolean moleIsActive = false, countDownWorks = false;
    long curSeconds = 0, curMillies = 0;

    public static final String FINAL_SCORE_VALUE_EXTRA = "FINAL_SCORE_VALUE_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_whac_amole);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        // 변수 설정
        initUiElements();

        // 두더지 비활성화
        disableHoles();

        fillMolesArrayList(0);

        // 30초 타이머 설정
        startPulse(GAME_TIME);
    }

    private void initUiElements() {
        countDownTimerText = findViewById(R.id.countDownTimerText);
        scoreText = findViewById(R.id.scoreText);

        firstMole = findViewById(R.id.firstMoleBtn);
        secondMole = findViewById(R.id.secondMoleBtn);
        thirdMole = findViewById(R.id.thirdMoleBtn);
        fourthMole = findViewById(R.id.fourthMoleBtn);
        fifthMole = findViewById(R.id.fifthMoleBtn);
        sixthMole = findViewById(R.id.sixthMoleBtn);
        seventhMole = findViewById(R.id.seventhMoleBtn);
        eighthMole = findViewById(R.id.eigthMoleBtn);
        ninethMole = findViewById(R.id.ninethMoleBtn);

        startGameCountDownTimer = findViewById(R.id.pulseCountDown);

        for (int i = 0; i < moleIdArray.length; i++) {
//            ImageButton imageButton = findViewById(moleIdArray[i]);
//            imageButton.setImageResource(R.drawable.mole_hole);
            ImageView imageView = findViewById(moleIdArray[i]);
            imageView.setImageResource(R.drawable.mole_hole);
        }
    }

    // 게임 시작 시 모든 두더지 홀 사용 안 함
    private void disableHoles() {
        firstMole.setEnabled(false);
        secondMole.setEnabled(false);
        thirdMole.setEnabled(false);
        fourthMole.setEnabled(false);
        fifthMole.setEnabled(false);
        sixthMole.setEnabled(false);
        seventhMole.setEnabled(false);
        eighthMole.setEnabled(false);
        ninethMole.setEnabled(false);
    }

    // 사용 가능한 두더지 배열 목록 값 초기화
    private void fillMolesArrayList(int notAvailableMoleId) {
        for (int i = 0; i < moleIdArray.length; i++) {
            availableMoleIdArrayList.add(moleIdArray[i]);
        }

        // 목록에서 마지막 두더지 제거
        if (notAvailableMoleId != 0) {
            availableMoleIdArrayList.remove(new Integer(notAvailableMoleId));
        }
    }

    // 게임 시작 전 시작 펄스 타이머
    private void startPulse(int time) {
        startGameCountDownTimer.start(new OnCountdownCompleted() {
            @Override
            public void completed() {
                startTimer(time);
            }
        });
    }

    // 게임 타이머 시작
    private void startTimer(int time){

        // Interval - 1 second
        // Timer - 30 seconds
        mainCountDownTimer = new CountDownTimer(time, 1) {
            public void onTick(long millisUntilFinished) {
                countDownWorks = true;
                long seconds = (millisUntilFinished / 1000) % 60;

                // 현재 남은 시간(초) 저장
                curSeconds = seconds;
                long millies = millisUntilFinished % 1000;

                // 현재 밀리언 저장
                curMillies = millies;

                String stringSeconds = "", stringMillies = "";

                // seconds 포맷
                if (String.valueOf(seconds).length() < 2)
                    stringSeconds += "  " + seconds;
                else if (String.valueOf(seconds).length() == 2)
                    stringSeconds += seconds;

                // millies 포맷
                if (String.valueOf(millies).length() < 2)
                    stringMillies += "00" + millies;
                else if (String.valueOf(millies).length() < 3)
                    stringMillies += "0" + millies;
                else if (String.valueOf(millies).length() == 3)
                    stringMillies += millies;

                if (seconds < 10 && seconds > 3)
                    countDownTimerText.setTextColor(getResources().getColor(R.color.black));
                else if (seconds <=3)
                    countDownTimerText.setTextColor(getResources().getColor(R.color.white));

                countDownTimerText.setText(stringSeconds + "." + stringMillies);

                if(!moleIsActive)
                    showMole();
            }

            // 게임이 끝나면 0.000
            public void onFinish() {
                countDownTimerText.setText("0.000");
                startResultActivity();
            }
        }.start();
    }

    // 게임 시작
    private void showMole(){

//        // Special moles
//        boolean tntMole = false;
//        boolean goldenMole = false;
//
//        // Calculate the chance of special moles
//        Random randomMole = new Random();
//        int randomMoleValue = randomMole.nextInt(20);
//
//        // 10% chance to see mole with tnt
//        if (randomMoleValue == 0 || randomMoleValue == 1)
//            tntMole  = true;
//
//        // 5% chance to see golden mole
//        if (randomMoleValue == 2)
//            goldenMole = true;adjustViewBounds

        // 랜덤 두더지가 나오기
//        ImageButton activeMole
//                = findViewById(availableMoleIdArrayList.get(new Random().nextInt(availableMoleIdArrayList.size())));
        ImageView activeMole
                = findViewById(availableMoleIdArrayList.get(new Random().nextInt(availableMoleIdArrayList.size())));

        // Set mole image
        // 자원 해제할 수 있는 방법/ gif 가 끝나는 시간 알 수 있는 방법
        // 액티비티가 종료되지 않았을 경우에만 실행
        if (!WhacAMoleActivity.this.isDestroyed()) {
            Glide.with(WhacAMoleActivity.this).load(R.drawable.mole_up).placeholder(R.drawable.mole_hole)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                            // gif 재생횟수 : 1번
                            if (resource instanceof GifDrawable) {
                                ((GifDrawable) resource).setLoopCount(1);
                            }
                            return false;
                        }
                    }).into(activeMole);
        }
        //activeMole.setImageResource(R.drawable.ic_mole);

//        if (tntMole)
//            activeMole.setImageResource(R.drawable.ic_tnt_mole);
//        if (goldenMole)
//            activeMole.setImageResource(R.drawable.ic_golden_mole);

        activeMole.setEnabled(true);
        moleIsActive = true;

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run(){
                // 두더지 배열 목록을 지운 다음 마지막 두더지 없이 다시 채우기
                availableMoleIdArrayList.clear();
                fillMolesArrayList(activeMole.getId());

                // 두더지 숨기기
                //Glide.with(WhacAMoleActivity.this).load(R.drawable.mole_down).into(activeMole);

                //activeMole.setImageResource(R.drawable.ic_mole_hole);

                activeMole.setImageResource(R.drawable.mole_hole);

                activeMole.setEnabled(false);
                moleIsActive = false;
            }
        };

        // Mole hides after 0.5 second
        // 여기가 속도 조절하는 곳(두더지가 나와있는 시간)
        //handler.postDelayed(runnable, 2000);

        // 랜덤으로 속도 조절하기
        // Calculate the chance of special moles
        Random randomTime = new Random();
        int randomTimeValue = randomTime.nextInt(20);

        if (randomTimeValue == 0 || randomTimeValue == 1) {
            Log.e("test", "10%");
            // 10% chance
            handler.postDelayed(runnable, 1500);
        } else if (randomTimeValue == 2) {
            Log.e("test", "5%");
            // 5% chance
            handler.postDelayed(runnable, 1000);
        } else {
            Log.e("test", "75%");
            // 75% chance
            handler.postDelayed(runnable, 2000);
        }

        // 두더지 버튼 리스너
        for (int i=0; i< moleIdArray.length; i++){

//            boolean innerTntMoleStatus = tntMole;
//            boolean innerGoldenMoleStatus = goldenMole;

            // ImageButton imageButton = findViewById(moleIdArray[i]);
            ImageView imageButton = findViewById(moleIdArray[i]);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!imageButton.isEnabled()) {
                        return;
                    } else {
                        //imageButton.setImageResource(R.drawable.ic_mole_hole);

                        imageButton.setImageResource(R.drawable.mole_hit);

                        scoreText.setText(String.valueOf(Integer.parseInt(scoreText.getText().toString()) + 1));
                        imageButton.setEnabled(false);

                        new Handler().postDelayed(() -> {
                            imageButton.setImageResource(R.drawable.mole_hole);
                        },200);
                    }

                    // Finish game if we click on mole with tnt
//                    if (innerTntMoleStatus == true){
//                        mainCountDownTimer.cancel();
//                        mainCountDownTimer.onFinish();
//                        return;
//                    }

                    // Add 3 points if clicked at golden mole
//                    if (innerGoldenMoleStatus == true){
//                        scoreText.setText(String.valueOf(Integer.parseInt(scoreText.getText().toString()) + 3));
//                        return;
//                    }
                }
            });

        }

    }

    // Start Result Activity when game is finished
    private void startResultActivity(){
        Intent intent = new Intent(WhacAMoleActivity.this, ResultActivity.class);
        intent.putExtra(WhacAMoleActivity.FINAL_SCORE_VALUE_EXTRA, scoreText.getText().toString());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mainCountDownTimer.cancel();
    }
}