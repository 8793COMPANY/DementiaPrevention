package com.corporation8793.dementia.game;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DisplayFontSize;

public class WhacAMoleResultDialog extends Dialog {

    TextView score_num, score_text, retry_btn, finish_btn;

    int score = 0;

    View.OnClickListener retry_listener, finish_listener;

    public WhacAMoleResultDialog(@NonNull Context context, int score, View.OnClickListener retry_listener, View.OnClickListener finish_listener) {
        super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        this.score = score;
        this.retry_listener = retry_listener;
        this.finish_listener = finish_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.dialog_whac_a_mole_result);

        score_num = findViewById(R.id.score_num);
        score_text = findViewById(R.id.score_text);
        retry_btn = findViewById(R.id.retry_btn);
        finish_btn = findViewById(R.id.finish_btn);

        score_num.setTextSize(DisplayFontSize.font_size_x_92);
        score_text.setTextSize(DisplayFontSize.font_size_x_55);
        retry_btn.setTextSize(DisplayFontSize.font_size_x_36);
        finish_btn.setTextSize(DisplayFontSize.font_size_x_36);

        retry_btn.setOnClickListener(retry_listener);
        finish_btn.setOnClickListener(finish_listener);

        score_num.setText(String.valueOf(score));
    }
}
