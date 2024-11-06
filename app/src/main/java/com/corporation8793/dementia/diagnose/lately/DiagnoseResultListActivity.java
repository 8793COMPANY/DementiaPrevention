package com.corporation8793.dementia.diagnose.lately;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.chat.ChatItemDecoration;
import com.corporation8793.dementia.util.Application;
import com.corporation8793.dementia.util.DataSetting;
import com.corporation8793.dementia.util.DisplayFontSize;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class DiagnoseResultListActivity extends AppCompatActivity {

    RecyclerView diagnose_list;
    DiagnoseListAdapter adapter;

    ConstraintLayout test_section;

    TextView counting_rest, lately_diagnose_progress_text, ten_text, lately_diagnose_list;

    Button close_btn;

    ArrayList<DiagnoseList> chat_list = new ArrayList<>();

    LineChart chart;

    // 원래 코드
//    int [] lately = {8,2,5,6,7,4,1,7,0,5};
    ArrayList<Integer> lately = new ArrayList<>();

    List<com.corporation8793.dementia.data.DiagnoseList> result_all_list = new ArrayList();
    List<com.corporation8793.dementia.data.DiagnoseList> result_list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dignose_result_list);

        Application.FullScreenMode(DiagnoseResultListActivity.this);

        result_all_list = DataSetting.getInstance(DiagnoseResultListActivity.this).getDiagnoseLists();

        Display display = getWindowManager().getDefaultDisplay();  // in Activity
        /* getActivity().getWindowManager().getDefaultDisplay() */ // in Fragment
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        int height = size.y;

        int lately_diagnose_progress_size = ((int)(height / 1280) * 500);
        int item_size = ((int)(height / 1280) * 150);

        counting_rest = findViewById(R.id.counting_rest);
        counting_rest.setTextSize(DisplayFontSize.font_size_x_40);
        lately_diagnose_progress_text = findViewById(R.id.lately_diagnose_progress_text);
        lately_diagnose_progress_text.setTextSize(DisplayFontSize.font_size_x_32);
        ten_text = findViewById(R.id.ten_text);
        ten_text.setTextSize(DisplayFontSize.font_size_x_24);
        lately_diagnose_list = findViewById(R.id.lately_diagnose_list);
        lately_diagnose_list.setTextSize(DisplayFontSize.font_size_x_32);

        test_section = findViewById(R.id.test_section);
        chart = findViewById(R.id.graph_view);
        close_btn = findViewById(R.id.close_btn);

        close_btn.setOnClickListener(v->{
            finish();
        });

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = lately_diagnose_progress_size;

        test_section.setLayoutParams(params);

        diagnose_list = (RecyclerView)findViewById(R.id.diagnose_list);
        diagnose_list.setHasFixedSize(true); // 변경하지 않음 -> 항목의 높이가 바뀌지 않아야 비용이 적게 드므로 성능이 좋음

        // 원래 코드
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); // 리사이클러뷰의 레이아웃을 정해줄 레이아웃 매니저
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 리스트 역순으로 출력
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        diagnose_list.setLayoutManager(layoutManager); // 리사이클러뷰에 리니어 레이아웃 매니저를 사용함

        if (result_all_list.size() > 10) {
            for (int i = (result_all_list.size() - 1); i >= (result_all_list.size() - 10); i--) {
                Log.e("test", "i : " + i);
                result_list.add(result_all_list.get(i));

//                Log.e("test", "result_list : " + result_list.get(i).date + result_list.get(i).resultText + result_list.get(i).resultScore);
//
//                chat_list.add(new DiagnoseList(result_list.get(i).date, result_list.get(i).resultText));
//                lately.add(result_list.get(i).resultScore);
            }

//            for (int j = 0; j < result_list.size(); j++) {
//                Log.e("test", "result_list : " + result_list.get(j).date + result_list.get(j).resultText + result_list.get(j).resultScore);
//
//                chat_list.add(new DiagnoseList(result_list.get(j).date, result_list.get(j).resultText));
//                lately.add(result_list.get(j).resultScore);
//            }

            for (int j = result_list.size() - 1; j >= 0; j--) {
                Log.e("test", "result_list : " + result_list.get(j).date + result_list.get(j).resultText + result_list.get(j).resultScore);

                chat_list.add(new DiagnoseList(result_list.get(j).date, result_list.get(j).resultText));
                lately.add(result_list.get(j).resultScore);
            }
        } else {
            result_list.addAll(result_all_list);

            for (int i = 0; i < result_list.size(); i++) {
                Log.e("test", "result_list : " + result_list.get(i).date + result_list.get(i).resultText + result_list.get(i).resultScore);

                chat_list.add(new DiagnoseList(result_list.get(i).date, result_list.get(i).resultText));
                lately.add(result_list.get(i).resultScore);
            }
        }

//        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다.."));
//        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
//        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
//        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
//        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
//        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
//        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
//        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
//        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
//        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));

        adapter = new DiagnoseListAdapter(chat_list, item_size); // chatArrayList를 어댑터로 연결, 회원의 이메일도 넘김
        diagnose_list.setAdapter(adapter); // 리사이클뷰에 어댑터를 설정
        diagnose_list.addItemDecoration(new ChatItemDecoration(15));

        chart.setDrawGridBackground(true);
        chart.setBackgroundColor(Color.WHITE);
        chart.setGridBackgroundColor(Color.WHITE);

// description text
        chart.getDescription().setEnabled(false);
        Description des = chart.getDescription();
        des.setEnabled(false);

        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);

        chart.setAutoScaleMinMaxEnabled(true);
        chart.setPinchZoom(false);

        //x축
//        chart.getXAxis().setDrawAxisLine(false);
//        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        chart.getXAxis().setEnabled(true);
//        chart.getXAxis().setDrawGridLines(false);
//        chart.getXAxis().setTextColor(getResources().getColor(R.color.gray_535353));
//        chart.getXAxis().setTextSize(DisplayFontSize.font_size_x_16);
//        chart.getXAxis().setYOffset(20f);
//        chart.getXAxis().setLabelCount(10,true);

        // XAxis 설정 (1 ~ 10 숫자 표시)
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);  // X축 간격 1로 설정
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X축 아래쪽에 표시
        xAxis.setAxisMinimum(0.5f);  // X축 최소값 설정 (1부터 시작)
        xAxis.setAxisMaximum(10f); // X축 최대값 설정 (10까지 표시)
        xAxis.setLabelCount(10);   // X축에 10개의 레이블 설정
//        xAxis.setAvoidFirstLastClipping(true);
        chart.getXAxis().setTextColor(getResources().getColor(R.color.gray_535353));
        chart.getXAxis().setTextSize(DisplayFontSize.font_size_x_16);

//        chart.getXAxis().setValueFormatter(new ValueFormatter() {
//
//            @Override
//            public String getFormattedValue(float value) {
//                return ((int)value+1)+"";
//            }
//        });

        Legend l = chart.getLegend();
        l.setEnabled(false);

        //y축
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(35f);
        leftAxis.setEnabled(true);
        leftAxis.setZeroLineColor(R.color.white);
        leftAxis.setTextSize(DisplayFontSize.font_size_x_16);

        leftAxis.setLabelCount(3,true);

        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String str = "";
                Log.e("value",value+"");
                if (value == 35f) {
                    str = "상";
                } else if (value == 17.5f){
                    str = "중";
                } else if (value == 0f){
                    str = "하";
                } else {
                    str = "";
                }
//                if (value == 10) {
//                    str = "상";
//                }else if (value>4 && value <5){
//                    str = "중";
//                }else if (value < 0){
//                    str = "하";
//                }
                Log.e("value",value+"");
                return str;
            }
        });

        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.invalidate();

        // 원래 코드
//        for (int i=0; i< lately.length; i++){
//            addEntry(lately[i]);
//        }
        for (int i=0; i< lately.size(); i++){
            addEntry(lately.get(i));
        }

        close_btn.setOnClickListener(v->{
            finish();
        });

    }


    private void addEntry(double num) {

        LineData data = chart.getData();

        if (data == null) {
            data = new LineData();
            chart.setData(data);
        }

        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }

        // 원래 코드
//        data.addEntry(new Entry((float)set.getEntryCount(), (float)num), 0);

        // 임의로 추가하는 부분
        int xIndex = (int) set.getEntryCount() + 1; // 데이터의 개수 + 1;
        data.addEntry(new Entry(xIndex, (float)num), 0);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        chart.notifyDataSetChanged();

        chart.setVisibleXRangeMaximum(10);
        chart.setVisibleXRangeMinimum(10);
        // this automatically refreshes the chart (calls invalidate())
//        chart.moveViewTo(data.getEntryCount(), 50f, YAxis.AxisDependency.LEFT);
        chart.moveViewToX(xIndex);
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "");
//        set.setLineWidth(2f);

//        set.setLineWidth(3);
//        set.setCircleRadius(20);
//        set.setCircleHoleRadius(8);
//        set.setDrawValues(false);
//        set.setDrawCircleHole(true);
//        set.setDrawCircles(true);
//        set.setDrawHorizontalHighlightIndicator(false);
//        set.setDrawHighlightIndicators(false);
//        set.setDrawValues(false);
//        set.setValueTextColor(getResources().getColor(R.color.white));
//        set.setColor(Color.rgb(31, 120, 180));
////        set.setHighLightColor(Color.rgb(0, 190, 0));
//        set.setCircleColor(Color.rgb(31, 120, 180));
//        set.setCircleHoleColor(getResources().getColor(R.color.white));
//        set.setCircleSize(3.5f);

        set.setLineWidth(3);
        set.setCircleRadius(5);
        set.setCircleHoleRadius(3);
        set.setDrawValues(false);
        set.setDrawCircleHole(true);
        set.setDrawCircles(true);
        set.setDrawHorizontalHighlightIndicator(false);
        set.setDrawHighlightIndicators(false);
        set.setColor(getResources().getColor(R.color.mint_00b3bc));
        set.setCircleColor(getResources().getColor(R.color.mint_00b3bc));

        return set;
    }
}