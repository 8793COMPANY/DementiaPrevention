package com.corporation8793.dementia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class UserInfoActivity extends AppCompatActivity {


    Button back_btn;

    EditText region_input_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        region_input_box = findViewById(R.id.region_input_box);
        region_input_box.setInputType(0);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v->{
            finish();
        });

        region_input_box.setOnClickListener(v->{
            DialogFragment dialogFragment = new DatePickerFragment();
            dialogFragment.show(getSupportFragmentManager(), "datePicker");

        });
    }
}