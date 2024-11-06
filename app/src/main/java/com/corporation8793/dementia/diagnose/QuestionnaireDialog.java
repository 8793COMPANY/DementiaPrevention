package com.corporation8793.dementia.diagnose;

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
import com.corporation8793.dementia.util.DisplayFontSize;

public class QuestionnaireDialog extends Dialog {

    TextView main_text, sub_text, confirm_btn, guide_text;

    View.OnClickListener confirm_listener;

    public QuestionnaireDialog(@NonNull Context context, View.OnClickListener confirm_listener) {
        super(context);

        this.confirm_listener = confirm_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.dialog_questionnaire);

        main_text = findViewById(R.id.main_text);
        sub_text = findViewById(R.id.sub_text);
        confirm_btn = findViewById(R.id.confirm_btn);
        guide_text = findViewById(R.id.guide_text);

        main_text.setTextSize(DisplayFontSize.font_size_x_46);
        sub_text.setTextSize(DisplayFontSize.font_size_x_34);
        confirm_btn.setTextSize(DisplayFontSize.font_size_x_36);
        guide_text.setTextSize(DisplayFontSize.font_size_x_34);

        confirm_btn.setOnClickListener(confirm_listener);
    }
}
