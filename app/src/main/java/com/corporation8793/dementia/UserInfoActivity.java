package com.corporation8793.dementia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.corporation8793.dementia.data.DiagnoseList;
import com.corporation8793.dementia.data.User;
import com.corporation8793.dementia.diagnose.DiagnosticResultActivity;
import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DataSetting;
import com.corporation8793.dementia.util.DisplayFontSize;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {

    Button back_btn, confirm_btn;

    TextView birthday_input, region_input_box, info_intro_text, id_text, gender_text, birthday_text, region_text, counting_rest;
    AlertDialog dialog;
    RadioGroup gender_select_section;

    ImageView app_icon;
    ConstraintLayout top_section;

    String type;

    EditText id_input_box;

    User user;
    List<User> userList = new ArrayList<>();

    /*TODO : 1. 정보수집인지 정보수정인지에 따라 변화
                - 정보수집 (정보를 다 입력할 때까지 확인버튼 비활성화되게)
                - 정보수정 (입력했던 정보 처음에 뿌려져 있어야 함, 정보의 변화가 있을 때만 확인 버튼 활성화)
             2. 생년월일 다이얼로그는 대충 틀만 잡아놓고 커스텀한거라 디자인 필요해보임
             3. 지역 고르는 입력창은 만드신 지역선택 기능 연결해주세요
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Application.FullScreenMode(UserInfoActivity.this);

        // 정보수집(null)인지 수정(edit)인지 판단
        type = getIntent().getStringExtra("type");

        info_intro_text = findViewById(R.id.info_intro_text);
        info_intro_text.setTextSize(DisplayFontSize.font_size_x_40);
        counting_rest = findViewById(R.id.counting_rest);
        counting_rest.setTextSize(DisplayFontSize.font_size_x_40);

        id_text = findViewById(R.id.id_text);
        id_text.setTextSize(DisplayFontSize.font_size_x_32);
        gender_text = findViewById(R.id.gender_text);
        gender_text.setTextSize(DisplayFontSize.font_size_x_32);
        birthday_text = findViewById(R.id.birthday_text);
        birthday_text.setTextSize(DisplayFontSize.font_size_x_32);
        region_text = findViewById(R.id.region_text);
        region_text.setTextSize(DisplayFontSize.font_size_x_32);

        id_input_box = findViewById(R.id.id_input_box);
        id_input_box.setTextSize(DisplayFontSize.font_size_x_34);

        dialog = createDialog();

        app_icon = findViewById(R.id.app_icon);
        top_section = findViewById(R.id.top_section);

        birthday_input = findViewById(R.id.birthday_input);
        birthday_input.setInputType(0);
        birthday_input.setTextSize(DisplayFontSize.font_size_x_34);

        region_input_box = findViewById(R.id.region_input_box);
        region_input_box.setTextSize(DisplayFontSize.font_size_x_34);

        gender_select_section= findViewById(R.id.gender_select_section);

        RadioButton radioButton = gender_select_section.findViewById(R.id.woman);
        radioButton.setTextSize(DisplayFontSize.font_size_x_34);
        radioButton = gender_select_section.findViewById(R.id.man);
        radioButton.setTextSize(DisplayFontSize.font_size_x_34);

        confirm_btn = findViewById(R.id.confirm_btn);
        confirm_btn.setTextSize(DisplayFontSize.font_size_x_36);
        confirm_btn.setOnClickListener(v->{
            // 유저 정보 디비에 저장
            RadioButton selectedRadioButton = findViewById(gender_select_section.getCheckedRadioButtonId());
            user = new User(id_input_box.getText().toString().trim(), region_input_box.getText().toString(),
                    selectedRadioButton.getText().toString(), birthday_input.getText().toString());

            if (!TextUtils.isEmpty(type)){
                Log.e("check","edit");
                // 유저 정보 수정
                userList = DataSetting.getInstance(UserInfoActivity.this).getUserList();
                DataSetting.getInstance(UserInfoActivity.this).update_user_data(userList.get(0).uid, user);
//                // 메인으로 넘어가기
//                Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            } else {
                Log.e("check","get");
//                // 유저 정보 디비에 저장
//                RadioButton selectedRadioButton = findViewById(gender_select_section.getCheckedRadioButtonId());
//                user = new User(id_input_box.getText().toString().trim(), region_input_box.getText().toString(),
//                        selectedRadioButton.getText().toString(), birthday_input.getText().toString());
                DataSetting.getInstance(UserInfoActivity.this).insert_user_data(user);
                Application.setUserData(user);

                // 메인으로 넘어가기
                Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (!TextUtils.isEmpty(type)){
            Log.e("check","edit");
            app_icon.setVisibility(View.INVISIBLE);
            top_section.setVisibility(View.VISIBLE);
            confirm_btn.setEnabled(false);

            id_input_box.setText(Application.user.name);

            if (Application.user.ageRange.equals("여자")) {
                Log.e("test", "woman");
                ((RadioButton)findViewById(R.id.man)).setChecked(false);
                ((RadioButton)findViewById(R.id.woman)).setChecked(true);
            } else { // "남자"인 경우
                Log.e("test", "man");
                ((RadioButton)findViewById(R.id.man)).setChecked(true);
                ((RadioButton)findViewById(R.id.woman)).setChecked(false);
            }

            birthday_input.setText(Application.user.birthday);
            region_input_box.setText(Application.user.region);
        }else{
            Log.e("check","get");
            info_intro_text.setText("프로필 정보를 입력해주세요");
//            confirm_btn.setBackground(Color.parseColor("#dde0e2"));
            confirm_btn.setEnabled(false);
        }

        region_input_box.setInputType(0);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v->{
            finish();
        });

        birthday_input.setOnClickListener(v -> {
//            dialog.show();
//            Window window = dialog.getWindow();
//
//            int x = (int) (Application.displaySize_X * 0.55f);
//            int y = (int) (Application.displaySize_Y * 0.4f);
//            window.setLayout(x, y);

            DialogFragment dialogFragment = new DatePickerFragment(new DatePickerFragment.DatePickerDialogListener() {
                @Override
                public void clickBtn(String date) {
                    Log.e("teset!!", date+"!!");
                    birthday_input.setText(date);

                    // 여기에 정보 모두 수집 완료했는지 확인
                    checkFieldsForEmptyValues();
                }
            });

            dialogFragment.show(getSupportFragmentManager(), "datePicker");
        });

        gender_select_section.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.woman){
                findViewById(R.id.man).setActivated(false);
            }

            if (checkedId == R.id.man){
                findViewById(R.id.woman).setActivated(false);
            }

            checkFieldsForEmptyValues();
//            DialogFragment dialogFragment = new DatePickerFragment(new DatePickerFragment.DatePickerDialogListener() {
//                @Override
//                public void clickBtn(String date) {
//                    Log.e("teset!!", date+"!!");
//                    birthday_input.setText(date);
//                }
//            });
//
//            dialogFragment.show(getSupportFragmentManager(), "datePicker");
        });

        region_input_box.setOnClickListener(v->{
            MapDialog mapDialog = new MapDialog(UserInfoActivity.this, 3, "", new MapDialog.MapDialogListener() {
                @Override
                public void clickBtn(String si, String gu) {
                    Log.e("teset!!", si+"!!"+gu);
                    region_input_box.setText(si + " " + gu);
                    // 여기에 정보 모두 수집 완료했는지 확인
                    checkFieldsForEmptyValues();
                }
            });

            mapDialog.show();
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        id_input_box.addTextChangedListener(textWatcher);
    }

    // 모든 입력 필드와 날짜, 라디오버튼 상태를 확인하는 함수
    private void checkFieldsForEmptyValues() {
        Log.e("test", "checkFieldsForEmptyValues()");

        // 빈칸이 모두 채워져 있으면 확인버튼 활성화 및 데이터 디비에 넣기
        String editText = id_input_box.getText().toString();
        int selectedRadioButtonId = gender_select_section.getCheckedRadioButtonId();
        String selectedDate = birthday_input.getText().toString();
        String selectedRegion = region_input_box.getText().toString();

        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

        if (!TextUtils.isEmpty(type)){
            Log.e("check","edit");

            if (Application.user.name.equals(editText) && Application.user.ageRange.equals(selectedRadioButton.getText().toString())
                    && Application.user.birthday.equals(selectedDate) && Application.user.region.equals(selectedRegion)) { // 변경한 정보가 이전 정보와 같을 경우
                confirm_btn.setEnabled(false);
                confirm_btn.setBackgroundResource(R.drawable.user_info_button_off);
            } else { // 변경한 정보가 이전 정보와 다를 경우
                confirm_btn.setEnabled(true);
                confirm_btn.setBackgroundResource(R.drawable.user_info_button_on);
            }
        } else {
            Log.e("check","get");

            if (!editText.isEmpty() && selectedRadioButtonId != -1 && !selectedDate.isEmpty() && !selectedRegion.isEmpty()) {
                if (!editText.equals(" ") && !editText.trim().isEmpty()) { // 내용이 모두 채워졌을 경우
                    confirm_btn.setEnabled(true);
                    confirm_btn.setBackgroundResource(R.drawable.user_info_button_on);
                }
            } else { // 내용이 모두 채워지지 않은 경우

                confirm_btn.setEnabled(false);
                confirm_btn.setBackgroundResource(R.drawable.user_info_button_off);
            }
        }
    }

    AlertDialog createDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        LayoutInflater edialog = LayoutInflater.from(getApplicationContext());
        View mView  = edialog.inflate(R.layout.dialog_datepicker,null);

        NumberPicker year  = mView.findViewById(R.id.yearpicker_datepicker);
        NumberPicker month = mView.findViewById(R.id.monthpicker_datepicker);
        NumberPicker day = mView.findViewById(R.id.daypicker_datepicker);
        Button cancel  = mView.findViewById(R.id.cancel_btn);
        Button save  = mView.findViewById(R.id.save_btn);

        //  순환 안되게 막기
        year.setWrapSelectorWheel(false);
        month.setWrapSelectorWheel(false);
        day.setWrapSelectorWheel(false);

        //  editText 설정 해제
        year.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        year.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        year.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        Calendar cal = java.util.Calendar.getInstance();
        int current_year = cal.get ( cal.YEAR );

        int current_month = cal.get ( cal.MONTH ) + 1 ;

        int current_date = cal.get ( cal.DATE ) ;

        //  최소값 설정
        year.setMinValue(1900);
        month.setMinValue(1);
        day.setMinValue(1);

        //  최대값 설정
        year.setMaxValue(2024);
        month.setMaxValue(12);
        day.setMaxValue(31);

        year.setValue(current_year);
        month.setValue(current_month);
        day.setValue(current_date);

        //  취소 버튼 클릭 시
        cancel.setOnClickListener(v1 -> {
            dialog.dismiss();
            dialog.cancel();
        });

        //  완료 버튼 클릭 시
        save.setOnClickListener(v1 -> {
            birthday_input.setText((year.getValue())+"/"+(month.getValue())+"/"+(day.getValue()));

            // 여기에 정보 모두 수집 완료했는지 확인
            checkFieldsForEmptyValues();

            dialog.dismiss();
            dialog.cancel();
        });

        month.setOnValueChangedListener((picker, oldVal, newVal) -> {
            day.setMaxValue(28);
        });

        dialog.setView(mView);
        dialog.create();

        WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
        params.width=(get_width() / 720) * 500;;
        params.height= (get_height() / 1280) * 440;
        dialog.getWindow().setAttributes(params);

        return dialog;
    }

    int get_height(){
        Display display = getWindowManager().getDefaultDisplay();  // in Activity
        /* getActivity().getWindowManager().getDefaultDisplay() */ // in Fragment
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        int height = size.y;
        return  height;
    }

    int get_width(){
        Display display = getWindowManager().getDefaultDisplay();  // in Activity
        /* getActivity().getWindowManager().getDefaultDisplay() */ // in Fragment
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        int width = size.x;
        return  width;
    }
}