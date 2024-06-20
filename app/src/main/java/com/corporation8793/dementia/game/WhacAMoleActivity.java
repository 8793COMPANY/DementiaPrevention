package com.corporation8793.dementia.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.corporation8793.dementia.MainActivity;
import com.corporation8793.dementia.game.pulse_countdown.OnCountdownCompleted;
import com.corporation8793.dementia.game.pulse_countdown.PulseCountDown;
import com.corporation8793.dementia.R;
import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DisplayFontSize;
import com.jackandphantom.circularprogressbar.CircleProgressbar;

import java.util.ArrayList;
import java.util.Random;

public class WhacAMoleActivity extends AppCompatActivity {

    TextView countDownTimerText, scoreText, guide_text;
    //    ImageButton firstMole, secondMole, thirdMole, fourthMole, fifthMole, sixthMole, seventhMole, eighthMole, ninethMole;
    ImageView cancel_btn, star_first, star_second, star_third;
    ImageView firstMole, secondMole, thirdMole, fourthMole, fifthMole, sixthMole, seventhMole, eighthMole, ninethMole;

    ArrayList<Integer> availableMoleIdArrayList = new ArrayList<>();
    int[] moleIdArray = { R.id.firstMoleBtn, R.id.secondMoleBtn, R.id.thirdMoleBtn,
            R.id.fourthMoleBtn, R.id.fifthMoleBtn, R.id.sixthMoleBtn,
            R.id.seventhMoleBtn, R.id.eigthMoleBtn, R.id.ninethMoleBtn };

    private static final int GAME_TIME = 30000;

    // 기본 30초로 설정
//    private static final Long SET_TIME = 30L;
//    long baseTime;
//    long setTime;

    PulseCountDown startGameCountDownTimer;
    CountDownTimer mainCountDownTimer;
    CircleProgressbar timer_progressbar;
    Handler gameHandler;

    boolean moleIsActive = false, countDownWorks = false;
    long curSeconds = 0, curMillies = 0;

    boolean minusMole = false, touch_check = false;

    public static final String FINAL_SCORE_VALUE_EXTRA = "FINAL_SCORE_VALUE_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whac_amole);

        // 네비게이션바 숨기기
        Application.FullScreenMode(WhacAMoleActivity.this);

        // 변수 설정
        initUiElements();

        // 두더지 비활성화
        disableHoles();

        // 두더지 초기화
        fillMolesArrayList(0);

        // 30초 타이머 설정
        startPulse(GAME_TIME);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WhacAMoleActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initUiElements() {
        countDownTimerText = findViewById(R.id.countDownTimerText);
        scoreText = findViewById(R.id.scoreText);
        guide_text = findViewById(R.id.guide_text);

        cancel_btn = findViewById(R.id.cancel_btn);
        star_first = findViewById(R.id.star_first);
        star_second = findViewById(R.id.star_second);
        star_third = findViewById(R.id.star_third);

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
        timer_progressbar = findViewById(R.id.timer_progressbar);

        // 모든 두더지 홀 기본 이미지 적용
        defaultHoles();

        countDownTimerText.setTextSize(DisplayFontSize.font_size_x_36);
        scoreText.setTextSize(DisplayFontSize.font_size_x_34);
        scoreText.setPadding(0, 0, (int) DisplayFontSize.font_size_x_30, 0);
        guide_text.setTextSize(DisplayFontSize.font_size_x_34);
        startGameCountDownTimer.setTextSize(DisplayFontSize.font_size_x_60);

        star_first.setEnabled(true);
        star_second.setEnabled(true);
        star_third.setEnabled(true);
    }

    // 모든 두더지 홀 사용 안 함
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

    // 모든 두더지 홀 사용 함
    private void ableHoles() {
        firstMole.setEnabled(true);
        secondMole.setEnabled(true);
        thirdMole.setEnabled(true);
        fourthMole.setEnabled(true);
        fifthMole.setEnabled(true);
        sixthMole.setEnabled(true);
        seventhMole.setEnabled(true);
        eighthMole.setEnabled(true);
        ninethMole.setEnabled(true);
    }

    // 모든 두더지 홀 기본 이미지 적용
    private void defaultHoles() {
        for (int i = 0; i < moleIdArray.length; i++) {
//            ImageButton imageButton = findViewById(moleIdArray[i]);
//            imageButton.setImageResource(R.drawable.mole_hole);
            ImageView imageView = findViewById(moleIdArray[i]);
            imageView.setImageResource(R.drawable.whac_amole_mole_hole);
        }
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
                // 타이머 시작
                //startTimer(time);
                //timeStart();

                // 두더지 시작
                gameHandler = new Handler();
                gameHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!moleIsActive) {
                            touch_check = false;
                            showMole();
                        }

                        // 반복
                        gameHandler.postDelayed(this, 0);
                    }
                },0);
            }
        });
    }

    // 게임 타이머 시작
    @SuppressLint("HandlerLeak")
    private void startTimer(int time){
        // Interval - 1 second
        // Timer - 30 seconds
        timer_progressbar.setMaxProgress((float) time);
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
                    stringSeconds += "" + seconds;
                else if (String.valueOf(seconds).length() == 2)
                    stringSeconds += seconds;

                // millies 포맷
                if (String.valueOf(millies).length() < 2)
                    stringMillies += "00" + millies;
                else if (String.valueOf(millies).length() < 3)
                    stringMillies += "0" + millies;
                else if (String.valueOf(millies).length() == 3)
                    stringMillies += millies;

//                if (seconds < 10 && seconds > 3)
//                    countDownTimerText.setTextColor(getResources().getColor(R.color.black));
//                else if (seconds <=3)
//                    countDownTimerText.setTextColor(getResources().getColor(R.color.white));

                //countDownTimerText.setText(stringSeconds + "." + stringMillies);

//                if(!moleIsActive)
//                    showMole();

                Log.e("test", millisUntilFinished+"");

                countDownTimerText.setText(stringSeconds + "초");
                timer_progressbar.setProgress(millisUntilFinished);
            }

            // 게임이 끝나면 0.000
            public void onFinish() {
                countDownTimerText.setText("0초");
                timer_progressbar.setProgress(0);
                // 결과 페이지로 이동
                //startResultActivity();
            }
        }.start();
    }

//    @SuppressLint("HandlerLeak")
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            String time = setTimeOut();
//            Log.e("test", time);
//
////            String[] split_time = time.split(":");
////            String real_time;
////
////            if (split_time[1].indexOf("0") == 0) {
////                if (!time.equals("00:00")) {
////                    String[] remove_zero = split_time[1].split("0");
////                    real_time = remove_zero[1];
////                } else {
////                    real_time = "0";
////                }
////            } else {
////                real_time = split_time[1];
////            }
////
////            countDownTimerText.setText(real_time + "초");
//
//            // 0초가 되면
//            if (time.equals("00:00")) {
//                // 타이머 초기화
//                timeReset();
//            } else {
//                // 0초가 아니면
//                handler.sendEmptyMessage(0);
//            }
//        }
//    };
//
//    // 시간 시작
//    public void timeStart() {
//        // 기본 30초로 설정
//        setTime(SET_TIME);
//
//        baseTime = SystemClock.elapsedRealtime();
//
//        handler.sendEmptyMessage(0);
//    }
//
//    public void setTime(Long time) {
//        // 분단위 : * 1000 * 60 + 초단위 : * 1000
//        // 현재는 초 단위만 계산
//        setTime = time * 1000;
//        timer_progressbar.setMaxProgress((float) setTime);
//    }
//
//    // 시간 초기화
//    public void timeReset() {
//        //핸들러 메세지 전달 종료
//        handler.removeCallbacksAndMessages(null);
//
//        //long 변환한 시간 초기화
//        setTime = 0;
//
//        //프로그레스바 프로그레스 초기화
//        timer_progressbar.setProgress((float) 0);
//    }
//
//    @SuppressLint("DefaultLocale")
//    public String setTimeOut() {
//        long now = SystemClock.elapsedRealtime();
//        long outTime = baseTime - now + setTime;
//
//        long min = outTime / 1000 / 60;
//        long sec = outTime / 1000 % 60;
//
//        // 0.1초 단위가 남아있을때 초가 넘어가서 0.5초에도 0초로 표시 되기 때문에
//        // 0.1초 단위를 계산해서 초가 60초 이하일때 0.1초 단위가 남아 있으면 초가 변경되지 않도록 세팅
//        if (outTime % 1000 / 10 != 0) {
//            sec += 1;
//
//            if (sec == 60) {
//                sec = 0;
//                min += 1;
//            }
//        }
//
//        // 시간 확인
//        String easy_outTime = String.format("%02d:%02d", min, sec);
//
//        Log.e("test2", easy_outTime);
//        Log.e("test2", timer_progressbar.getMaxProgress() - ((float) ((now - baseTime) + (setTime/1000)))+"");
//
//        String[] split_time = easy_outTime.split(":");
//        String real_time;
//
//        if (split_time[1].indexOf("0") == 0) {
//            if (!easy_outTime.equals("00:00")) {
//                String[] remove_zero = split_time[1].split("0");
//                real_time = remove_zero[1];
//            } else {
//                real_time = "0";
//            }
//        } else {
//            real_time = split_time[1];
//        }
//
//        countDownTimerText.setText(real_time + "초");
//
//
//        // 늘어나는 경우
//        //timer_progressbar.setProgress((int) ((now - baseTime) + (setTime/1000)));
//        // 줄어드는 경우
//        if ((timer_progressbar.getMaxProgress() - ((float) ((now - baseTime) + (setTime/1000)))) < 0) {
//            Log.e("test2", "on");
//            timer_progressbar.setProgress(0);
//            countDownTimerText.setText("0초");
//        } else {
//            Log.e("test2", "off");
//            timer_progressbar.setProgress(timer_progressbar.getMaxProgress() - ((float) ((now - baseTime) + (setTime/1000))));
//        }
//
//        return easy_outTime;
//    }

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

        // 모든 두더지 활성화
        ableHoles();

        // 두더지가 나올 홀 랜덤으로 정하기
        ImageView activeMole
                = findViewById(availableMoleIdArrayList.get(new Random().nextInt(availableMoleIdArrayList.size())));

        // 스폐셜 두더지(기본)
        minusMole = false;

        Random randomMole = new Random();
        int randomMoleValue = randomMole.nextInt(10);

        // 30% chance to see mole with sunglassesMole
        if (randomMoleValue == 0 || randomMoleValue == 1 || randomMoleValue == 2)
            minusMole = true;

        // Set mole image
        // 자원 해제할 수 있는 방법/ gif 가 끝나는 시간 알 수 있는 방법
        // 액티비티가 종료되지 않았을 경우에만 실행
        if (!WhacAMoleActivity.this.isDestroyed()) {
            if (minusMole) {
                Glide.with(WhacAMoleActivity.this).load(R.drawable.basic_mole_up_speed_up).placeholder(R.drawable.whac_amole_mole_hole).override(Target.SIZE_ORIGINAL)
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
            } else {
                Glide.with(WhacAMoleActivity.this).load(R.drawable.sunglasses_mole_up_speed_up).placeholder(R.drawable.whac_amole_mole_hole).override(Target.SIZE_ORIGINAL)
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
                Log.e("test2", "runnable on");
                Log.e("test2", "touch_check : " + touch_check);

                // 두더지 배열 목록을 지운 다음 마지막 두더지 없이 다시 채우기
                availableMoleIdArrayList.clear();
                fillMolesArrayList(activeMole.getId());

                // 두더지 숨기기
                //Glide.with(WhacAMoleActivity.this).load(R.drawable.mole_down).into(activeMole);

                //activeMole.setImageResource(R.drawable.ic_mole_hole);

                activeMole.setImageResource(R.drawable.whac_amole_mole_hole);

                //activeMole.setEnabled(false);
                moleIsActive = false;

                if (!touch_check) {
                    // 기본 두더지인 경우는 제외
                    if (!minusMole) {
                        if (star_third.isEnabled()) { // 첫번째 별 활성화시
                            // 첫번째 별 차감
                            star_third.setEnabled(false);
                            star_third.setImageResource(R.drawable.whac_amole_star_empty);
                        } else if (star_second.isEnabled()) { // 두번째 별 활성화시
                            // 두번째 별 차감
                            star_second.setEnabled(false);
                            star_second.setImageResource(R.drawable.whac_amole_star_empty);
                        } else if (star_first.isEnabled()) { // 세번째 별 활성화시
                            // 세번째 별 차감
                            star_first.setEnabled(false);
                            star_first.setImageResource(R.drawable.whac_amole_star_empty);

                            // 두더지 홀 비활성화 및 초기화
                            defaultHoles();
                            disableHoles();

                            // 모든 별이 비활성화
                            gameHandler.removeCallbacksAndMessages(null);

                            Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
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
            handler.postDelayed(runnable, 1750);
        } else if (randomTimeValue == 2) {
            Log.e("test", "5%");
            // 5% chance
            handler.postDelayed(runnable, 1500);
        } else {
            Log.e("test", "85%");
            // 85% chance
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
                        if (activeMole == imageButton) {
                            //imageButton.setImageResource(R.drawable.ic_mole_hole);

                            if (minusMole) {
                                imageButton.setImageResource(R.drawable.hit_basic_mole);

//                                if ((Integer.parseInt(scoreText.getText().toString()) - 1) > 0) {
//                                    scoreText.setText(String.valueOf(Integer.parseInt(scoreText.getText().toString()) - 1));
//                                } else {
//                                    scoreText.setText("0");
//                                }
                                if (star_third.isEnabled()) { // 첫번째 별 활성화시
                                    // 첫번째 별 차감
                                    star_third.setEnabled(false);
                                    star_third.setImageResource(R.drawable.whac_amole_star_empty);
                                } else if (star_second.isEnabled()) { // 두번째 별 활성화시
                                    // 두번째 별 차감
                                    star_second.setEnabled(false);
                                    star_second.setImageResource(R.drawable.whac_amole_star_empty);
                                } else if (star_first.isEnabled()) { // 세번째 별 활성화시
                                    // 세번째 별 차감
                                    star_first.setEnabled(false);
                                    star_first.setImageResource(R.drawable.whac_amole_star_empty);

                                    // 두더지 홀 비활성화 및 초기화
                                    defaultHoles();
                                    disableHoles();

                                    // 모든 별이 비활성화
                                    gameHandler.removeCallbacksAndMessages(null);

                                    Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                imageButton.setImageResource(R.drawable.hit_sunglasses_mole);

                                scoreText.setText(String.valueOf(Integer.parseInt(scoreText.getText().toString()) + 1));

                                touch_check = true;
                            }

                            imageButton.setEnabled(false);

                            new Handler().postDelayed(() -> {
                                imageButton.setImageResource(R.drawable.whac_amole_mole_hole);
                            },200);
                        } else {
                            // 두더지가 아닌 홀을 누르면 별 감점
//                            if ((Integer.parseInt(scoreText.getText().toString()) - 1) > 0) {
//                                scoreText.setText(String.valueOf(Integer.parseInt(scoreText.getText().toString()) - 1));
//                            } else {
//                                scoreText.setText("0");
//                            }
                            if (star_third.isEnabled()) { // 첫번째 별 활성화시
                                // 첫번째 별 차감
                                star_third.setEnabled(false);
                                star_third.setImageResource(R.drawable.whac_amole_star_empty);
                            } else if (star_second.isEnabled()) { // 두번째 별 활성화시
                                // 두번째 별 차감
                                star_second.setEnabled(false);
                                star_second.setImageResource(R.drawable.whac_amole_star_empty);
                            } else if (star_first.isEnabled()) { // 세번째 별 활성화시
                                // 세번째 별 차감
                                star_first.setEnabled(false);
                                star_first.setImageResource(R.drawable.whac_amole_star_empty);

                                // 두더지 홀 비활성화 및 초기화

                                defaultHoles();
                                disableHoles();

                                // 모든 별이 비활성화
                                // 타이머 취소하고 게임 끝내기
                                if (mainCountDownTimer != null) {
                                    mainCountDownTimer.cancel();
                                }

                                gameHandler.removeCallbacksAndMessages(null);

                                Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_SHORT).show();
                            }
                        }
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

        if (mainCountDownTimer != null) {
            mainCountDownTimer.cancel();
        }

        if (gameHandler != null) {
            gameHandler.removeCallbacksAndMessages(null);
        }
    }
}