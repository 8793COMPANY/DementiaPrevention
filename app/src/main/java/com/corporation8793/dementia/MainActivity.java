package com.corporation8793.dementia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Button;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button seoul, daegu, gwangju;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Region.loadData(MainActivity.this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

        seoul = findViewById(R.id.seoul);
        daegu = findViewById(R.id.daegu);
        gwangju = findViewById(R.id.gwangju);

        Region region = new Region();

        seoul.setOnClickListener(v->{
            region.check_region("서울특별시");
        });

        daegu.setOnClickListener(v->{
            region.check_region("대구광역시");
        });


        gwangju.setOnClickListener(v->{
            region.check_region("경기도");
        });



    }
}