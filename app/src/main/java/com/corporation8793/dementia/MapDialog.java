package com.corporation8793.dementia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DisplayFontSize;

import java.util.ArrayList;
import java.util.List;

public class MapDialog extends Dialog {

    Context context;
    int num;
    TextView choice_si, close_btn;
    GridLayout grid_layout, grid_layout2;
    ConstraintLayout container;
    ScrollView scrollView;

    boolean first_check = false, select_check = false, select_check2 = false;

    Region region;
    String select_si, select_gu, before_si;
    int region_num = 0;
    List<String> gu_list  = new ArrayList<>();

    MapDialogListener mapDialogListener;

    int total_row;

    public MapDialog(@NonNull Context context, int num, String before_si, MapDialogListener mapDialogListener) {
        // 다이얼로그 전체 화면
        super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        this.context = context;
        this.num = num;
        this.before_si = before_si;
        this.mapDialogListener = mapDialogListener;
    }

    public interface MapDialogListener {
        void clickBtn(String si, String gu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_map);

        choice_si = findViewById(R.id.choice_si);
        close_btn = findViewById(R.id.close_btn);
        grid_layout = findViewById(R.id.grid_layout);
        grid_layout2 = findViewById(R.id.grid_layout2);
        scrollView = findViewById(R.id.scrollView);

        choice_si.setTextSize(DisplayFontSize.font_size_x_40);
        close_btn.setTextSize(DisplayFontSize.font_size_x_32);

        region = new Region();

        close_btn.setOnClickListener(view -> {
            if (num == 1) { // 시 선택
                if (first_check) {
                    if (select_check) {
                        select_check = false;

                        // 세종시 선택시 바로 닫기(구 없음)
                        if (select_si.equals("세종특별자치시")) {
                            mapDialogListener.clickBtn(Application.region_long_to_short(select_si), "-");
                            mapDialogListener.clickBtn(Application.region_long_to_short(select_si), "-");
                            dismiss();
                        } else {
                            grid_layout.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }

                        close_btn.setText("닫기");
                    } else {
                        if (select_check2) {
                            select_check2 = false;

                            Log.e("testtest", Application.region_long_to_short(select_si));

                            mapDialogListener.clickBtn(Application.region_long_to_short(select_si), select_gu);
                            dismiss();
                        } else {
                            Toast.makeText(context, "해당하는 구를 선택해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    dismiss();
                }
            } else { // 구 선택
                if (select_check2) {
                    select_check2 = false;

                    mapDialogListener.clickBtn(before_si, select_gu);
                    dismiss();
                } else {
                    dismiss();
                }
            }
        });

        // 시 선택 설정
        container = (ConstraintLayout) grid_layout.getChildAt(0);

        Log.e("test2",container+"");
        Log.e("test2",container.getChildCount()+"");

        for (int i = 0; i < container.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) container.getChildAt(i);
            radioButton.setTextSize(DisplayFontSize.font_size_x_32);

            int finalI = i;

            radioButton.setOnClickListener(view -> {
                // 초기화
                gu_list.clear();
                grid_layout2.removeAllViews();

                Log.e("radioButton text", radioButton.getText().toString());

                radioButton.setChecked(true);
                close_btn.setText("확인");

                first_check = true;
                select_check = true;

                for (int j = 0; j < container.getChildCount(); j++) {
                    if (j != finalI) {
                        RadioButton radioButton2 = (RadioButton) container.getChildAt(j);
                        radioButton2.setChecked(false);
                    }
                }

                select_si = Application.region_short_to_long(radioButton.getText().toString());
                Log.e("test2", select_si);

                region_num = region.check_region_num(select_si);
                gu_list = region.check_region_String(select_si);

                Log.e("test2", gu_list+"");
                Log.e("test2", "gu_list size : " + gu_list.size());

                int row_num = region_num / 3;
                Log.e("test2", "row_num : " + row_num);

                int row_num2 = region_num % 3;
                Log.e("test2", "row_num2 : " + row_num2);

                int count_num = 0;
                if (row_num2 == 0) {
                    total_row = row_num;
                } else {
                    total_row = (row_num + 1);
                }

                // 행은 개수에 따라 조절
                grid_layout2.setRowCount(total_row);

                // 열은 고정
                grid_layout2.setColumnCount(3);

                for (int k = 0; k < total_row; k++) {
                    for (int j = 0; j < 3; j++) {
                        Log.e("test2", "count_num : " + count_num);

                        if (count_num >= (region_num)) {
                            Log.e("test2", "end");
                        } else {
                            Log.e("test2", gu_list.get(count_num));

                            createBtn(k, j, gu_list.get(count_num));

                            count_num++;
                        }
                    }
                }
            });
        }

        if (num == 1) { // 시 선택
            grid_layout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else { // 구 선택
            grid_layout.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);

            Log.e("test2", before_si);
            Log.e("test2", Application.region_short_to_long(before_si));

            // 구버튼 생성
            createGu(before_si);
        }

        // 네비게이션바 숨기기
        FullScreenMode();
    }

    // 뷰 추가
    @SuppressLint("RtlHardcoded")
    private void createBtn(int row, int col, String gu) {
        Log.e("test2","createBtn");
        RadioButton radioButton = new RadioButton(context);

        // 아이디 부여
        radioButton.setId(View.generateViewId());

        // android:button="@null" 설정 및 배경 설정
        radioButton.setButtonDrawable(new StateListDrawable());
        radioButton.setBackgroundResource(R.drawable.map_radio_btn_select);

        // 글자 설정
        radioButton.setTextSize(DisplayFontSize.font_size_x_32);
        radioButton.setTextColor(Color.parseColor("#383838"));

        Typeface typeface = ResourcesCompat.getFont(context, R.font.pretendard_medium);
        radioButton.setTypeface(typeface);
        radioButton.setText(gu);

        radioButton.setGravity(Gravity.CENTER);

        // 그리드 레이아웃 행과 열 위치 지정
        GridLayout.Spec RowSpec = GridLayout.spec(row);
        GridLayout.Spec ColSpec = GridLayout.spec(col);

        // 길이 설정
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(RowSpec, ColSpec);
//        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//        radioButton.setLayoutParams(layoutParams);
        layoutParams.width = (int) DisplayFontSize.size_x_200;
        layoutParams.height = (int) DisplayFontSize.size_y_80;

        // 첫 줄이 아닌 경우 margin 값 주기
        if (row != 0) {
            layoutParams.topMargin = (int) DisplayFontSize.size_y_58;
        }

        // 첫 열이 아닌 경우 margin 값 주기
        if (col != 0) {
            layoutParams.leftMargin = (int) DisplayFontSize.size_x_20;
        }

        // 마지막 열인 경우 margin 값 주기
//        Log.e("testtest", row+"");
//        Log.e("testtest", total_row+"");
        if (row == (total_row - 1)) {
            layoutParams.bottomMargin = (int) DisplayFontSize.size_y_42;
        }

        // 그리드 레이아웃에 뷰 추가
        grid_layout2.addView(radioButton, layoutParams);
//        if (grid_layout2.getChildCount() == 0) {
//            grid_layout2.addView(radioButton, layoutParams);
//        } else {
//            for (int i = 0; i < grid_layout2.getChildCount(); i++) {
//                RadioButton radioButton2 = (RadioButton) grid_layout2.getChildAt(i);
//
//                // 중복되는 구가 있는 경우 추가하지 않기 - 예) 시-시-구로 가는 경우
//                if (!radioButton2.getText().toString().equals(radioButton.getText().toString())) {
//                    if (radioButton.getParent() != null) {
//                        ((ViewGroup) radioButton.getParent()).removeView(radioButton);
//                    }
//
//                    grid_layout2.addView(radioButton, layoutParams);
//                }
//            }
//        }

        radioButton.setOnClickListener(view -> {
            radioButton.setChecked(true);

            select_gu = radioButton.getText().toString();

            select_check2 = true;

            close_btn.setText("확인");

            for (int j = 0; j < grid_layout2.getChildCount(); j++) {
                RadioButton radioButton2 = (RadioButton) grid_layout2.getChildAt(j);

                if (radioButton != radioButton2) {
                    radioButton2.setChecked(false);
                }
            }
        });
    }

    private void createGu(String si) {
        Log.e("test22", si);

        // 초기화
        gu_list.clear();
        grid_layout2.removeAllViews();

        for (int i = 0; i < container.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) container.getChildAt(i);
            radioButton.setTextSize(DisplayFontSize.font_size_x_32);

            if (radioButton.getText().toString().equals(si)) {
                region_num = region.check_region_num(Application.region_short_to_long(si));
                gu_list = region.check_region_String(Application.region_short_to_long(si));

                Log.e("test2", gu_list+"");
                Log.e("test2", "gu_list size : " + gu_list.size());

                int row_num = region_num / 3;
                Log.e("test2", "row_num : " + row_num);

                int row_num2 = region_num % 3;
                Log.e("test2", "row_num2 : " + row_num2);

                int count_num = 0;
                if (row_num2 == 0) {
                    total_row = row_num;
                } else {
                    total_row = (row_num + 1);
                }

                // 행은 개수에 따라 조절
                grid_layout2.setRowCount(total_row);

                // 열은 고정
                grid_layout2.setColumnCount(3);

                for (int k = 0; k < total_row; k++) {
                    for (int j = 0; j < 3; j++) {
                        Log.e("test2", "count_num : " + count_num);

                        if (count_num >= (region_num)) {
                            Log.e("test2", "end");
                        } else {
                            Log.e("test2", gu_list.get(count_num));

                            createBtn(k, j, gu_list.get(count_num));

                            count_num++;
                        }
                    }
                }
            }
        }
    }

    // 네비게이션바 숨기기
    private void FullScreenMode(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }
}
