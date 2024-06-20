package com.corporation8793.dementia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
<<<<<<< HEAD
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
=======
import android.util.Log;
>>>>>>> bdb660d0db22b8211a7229d05d99169f2f1c1f71
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

public class UserInfoActivity extends AppCompatActivity {


    Button back_btn;

<<<<<<< HEAD
    TextView birthday_input, region_input_box;

    AlertDialog dialog;

    RadioGroup gender_select_section;

    ImageView app_icon;
    ConstraintLayout top_section;

    /*TODO : 1. 정보수집인지 정보수정인지에 따라 변화
                - 정보수집 (정보를 다 입력할 때까지 확인버튼 비활성화되게)
                - 정보수정 (입력했던 정보 처음에 뿌려져 있어야 함, 정보의 변화가 있을 때만 확인 버튼 활성화)
             2. 생년월일 다이얼로그는 대충 틀만 잡아놓고 커스텀한거라 디자인 필요해보임
             3. 지역 고르는 입력창은 소영님께서 만드신 지역선택 기능 연결해주세요
     */
=======
    EditText birthday_input, region_input_box;
>>>>>>> bdb660d0db22b8211a7229d05d99169f2f1c1f71

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

<<<<<<< HEAD

        // 정보수집(null)인지 수정(edit)인지 판단
        String type = getIntent().getStringExtra("type");



        dialog = createDialog();

        app_icon = findViewById(R.id.app_icon);
        top_section = findViewById(R.id.top_section);
=======
        birthday_input = findViewById(R.id.birthday_input);
        birthday_input.setInputType(0);

>>>>>>> bdb660d0db22b8211a7229d05d99169f2f1c1f71
        region_input_box = findViewById(R.id.region_input_box);
        gender_select_section= findViewById(R.id.gender_select_section);
        birthday_input = findViewById(R.id.birthday_input);


        if (!TextUtils.isEmpty(type)){
            Log.e("check","edit");
            app_icon.setVisibility(View.INVISIBLE);
            top_section.setVisibility(View.VISIBLE);
        }else{
            Log.e("check","get");
        }


        region_input_box.setInputType(0);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v->{
            finish();
        });

        birthday_input.setOnClickListener(v -> {
<<<<<<< HEAD
            dialog.show();
        });

        gender_select_section.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.woman){
                findViewById(R.id.man).setActivated(false);
            }

            if (checkedId == R.id.man){
                findViewById(R.id.woman).setActivated(false);
            }
=======
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
>>>>>>> bdb660d0db22b8211a7229d05d99169f2f1c1f71
        });


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
    };


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