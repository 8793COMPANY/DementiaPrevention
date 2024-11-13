package com.corporation8793.dementia.game.quiz;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.util.DisplayFontSize;

public class OxQuizDialog extends Dialog {
    TextView main_text, main_text2, dialog_confirm_btn;
    View.OnClickListener confirm_listener;
    boolean isCorrect; // 정답 여부 확인

    public OxQuizDialog(@NonNull Context context, View.OnClickListener confirm_listener, boolean isCorrect) {
        super(context);

        this.confirm_listener = confirm_listener;
        this.isCorrect = isCorrect; // 정답 여부 초기화
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 정답 여부에 따라 다른 레이아웃 설정
        if (isCorrect) {
            setContentView(R.layout.dialog_oxquiz);
        } else {
            setContentView(R.layout.dialog_oxquiz_incorrect);
        }

        // 다이얼로그 크기 설정
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.95);
        params.height = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.8);
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);

        main_text = findViewById(R.id.main_text);
        main_text2 = findViewById(R.id.main_text2);
        dialog_confirm_btn = findViewById(R.id.dialog_confirm_btn);

        main_text.setTextSize(DisplayFontSize.font_size_x_46);
        dialog_confirm_btn.setTextSize(DisplayFontSize.font_size_x_36);

        dialog_confirm_btn.setOnClickListener(confirm_listener);
    }
}
