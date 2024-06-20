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

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.chat.ChatItemDecoration;
import com.corporation8793.dementia.diagnose.MyValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
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

public class DiagnoseResultListActivity extends AppCompatActivity {

    RecyclerView diagnose_list;
    DiagnoseListAdapter adapter;

    ConstraintLayout test_section;

    Button close_btn;

    ArrayList<DiagnoseList> chat_list = new ArrayList<>();

    LineChart chart;

    int [] lately = {8,2,5,6,7,4,1,7,0,5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dignose_result_list);
        Display display = getWindowManager().getDefaultDisplay();  // in Activity
        /* getActivity().getWindowManager().getDefaultDisplay() */ // in Fragment
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        int height = size.y;


        int lately_diagnose_progress_size = ((int)(height / 1280) * 500);
        int item_size = ((int)(height / 1280) * 150);

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); // 리사이클러뷰의 레이아웃을 정해줄 레이아웃 매니저
        diagnose_list.setLayoutManager(layoutManager); // 리사이클러뷰에 리니어 레이아웃 매니저를 사용함

        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));
        chat_list.add(new DiagnoseList("2025년 4월 10일","치매 안심입니다."));


        adapter = new DiagnoseListAdapter(chat_list, item_size); // chatArrayList를 어댑터로 연결, 회원의 이메일도 넘김
        diagnose_list.setAdapter(adapter); // 리사이클뷰에 어댑터를 설정
        diagnose_list.addItemDecoration(new ChatItemDecoration(10));



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
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setTextColor(getResources().getColor(R.color.gray_535353));

        chart.getXAxis().setYOffset(20f);
        chart.getXAxis().setLabelCount(10,true);


        chart.getXAxis().setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                return ((int)value+1)+"";
            }
        });


        Legend l = chart.getLegend();
        l.setEnabled(false);

        //y축
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaximum(10);
        leftAxis.setEnabled(true);
        leftAxis.setZeroLineColor(R.color.white);

        leftAxis.setLabelCount(3,true);

        leftAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                String str = "";
                Log.e("value",value+"");
                if (value == 10) {
                    str = "상";
                }else if (value>4 && value <5){
                    str = "중";
                }else if (value < 0){
                    str = "하";
                }

                Log.e("value",value+"");
                return str;
            }
        });

        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.invalidate();

        for (int i=0; i< lately.length; i++){
            addEntry(lately[i]);
        }


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



        data.addEntry(new Entry((float)set.getEntryCount(), (float)num), 0);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        chart.notifyDataSetChanged();

        chart.setVisibleXRangeMaximum(10);
        // this automatically refreshes the chart (calls invalidate())
        chart.moveViewTo(data.getEntryCount(), 50f, YAxis.AxisDependency.LEFT);



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