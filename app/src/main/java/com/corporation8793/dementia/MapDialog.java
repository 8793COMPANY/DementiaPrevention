package com.corporation8793.dementia;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.corporation8793.dementia.util.DisplayFontSize;

public class MapDialog extends Dialog {

    Context context;
    int num;
    TextView choice_si, close_btn;
    GridLayout grid_layout;
    ConstraintLayout container;

    boolean select_check = false;

    public MapDialog(@NonNull Context context, int num) {
        // 다이얼로그 전체 화면
        super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        this.context = context;
        this.num = num;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_map);

        choice_si = findViewById(R.id.choice_si);
        close_btn = findViewById(R.id.close_btn);
        grid_layout = findViewById(R.id.grid_layout);

        choice_si.setTextSize(DisplayFontSize.font_size_x_40);
        close_btn.setTextSize(DisplayFontSize.font_size_x_32);

        close_btn.setOnClickListener(view -> {
            if (num == 1) {
                if (select_check) {

                } else {
                    dismiss();
                }
            } else {
                dismiss();
            }
        });

        container = (ConstraintLayout) grid_layout.getChildAt(0);

        Log.e("test2",container+"");
        Log.e("test2",container.getChildCount()+"");

        for (int i = 0; i < container.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) container.getChildAt(i);
            radioButton.setTextSize(DisplayFontSize.font_size_x_32);

            int finalI = i;

            radioButton.setOnClickListener(view -> {
                Log.e("radioButton text", radioButton.getText().toString());

                radioButton.setChecked(true);
                close_btn.setText("확인");

                select_check = true;

                for (int j = 0; j < container.getChildCount(); j++) {
                    if (j != finalI) {
                        RadioButton radioButton2 = (RadioButton) container.getChildAt(j);
                        radioButton2.setChecked(false);
                    }
                }
            });
        }

        // 네비게이션바 숨기기
        FullScreenMode();
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
