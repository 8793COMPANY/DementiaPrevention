package com.corporation8793.dementia.game.order_number;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;

import com.corporation8793.dementia.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrderNumberGame extends AppCompatActivity {

    int count =0;

    String [][] number ={{"1","일","one"},{"2","이","two"},{"3","삼","three"},
            {"4","사","four"},{"5","오","five"},{"6","육","six"},
            {"7","칠","seven"},{"8","팔","eight"},{"9","구","nine"} };

    int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_number_game);

        List<String[]> list = Arrays.asList(number);
        List<Integer> shuffle = Arrays.stream(a).boxed().collect(Collectors.toList());

        Collections.shuffle(shuffle);

        shuffle.forEach(i -> {

            Log.e("hello shuffle num", i+"");
        });


        GridLayout number_list = findViewById(R.id.number_list);




        ConstraintLayout container2 = (ConstraintLayout) number_list.getChildAt(0);

        int childCount = container2.getChildCount();
        Log.e("childCount",childCount+"");
        for (int i= 0; i < childCount; i++){

            Button container = (Button) container2.getChildAt(i);

            int randomNum = (int) (Math.random() * 3);
            container.setText(number[shuffle.get(i)][randomNum]);
            container.setOnClickListener(v->{
                Log.e("button text", container.getText().toString());
                if (Arrays.asList(list.get(count)).contains(container.getText().toString())){
                    Log.e("result","정답!");
                    container.setText("x");
                    container.setBackgroundColor(getResources().getColor(R.color.gray_9f9f9f));
                    container.setEnabled(false);
                    count++;
                }
            });
        }


    }
}