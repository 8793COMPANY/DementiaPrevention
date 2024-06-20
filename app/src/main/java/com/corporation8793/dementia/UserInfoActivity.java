package com.corporation8793.dementia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class UserInfoActivity extends AppCompatActivity {


    Button back_btn;

    EditText birthday_input, region_input_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        birthday_input = findViewById(R.id.birthday_input);
        birthday_input.setInputType(0);

        region_input_box = findViewById(R.id.region_input_box);
        region_input_box.setInputType(0);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v->{
            finish();
        });

        birthday_input.setOnClickListener(v -> {
            DialogFragment dialogFragment = new DatePickerFragment(new DatePickerFragment.DatePickerDialogListener() {
                @Override
                public void clickBtn(String date) {
                    Log.e("teset!!", date+"!!");
                    birthday_input.setText(date);
                }
            });

            dialogFragment.show(getSupportFragmentManager(), "datePicker");
        });

        region_input_box.setOnClickListener(v->{
            MapDialog mapDialog = new MapDialog(UserInfoActivity.this, 3, "", new MapDialog.MapDialogListener() {
                @Override
                public void clickBtn(String si, String gu) {
                    Log.e("teset!!", si+"!!"+gu);
                    region_input_box.setText(si + " " + gu);
                }
            });

            mapDialog.show();
        });
    }
}