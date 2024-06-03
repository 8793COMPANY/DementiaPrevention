package com.corporation8793.dementia;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Region {
    public static final int seoul = 11;
    public static final int busan = 26;
    public static final int daegu = 27;
    public static final int incheon = 28;
    public static final int gwangju = 29;
    public static final int daejeon = 30;
    public static final int ulsan = 31;
    public static final int sejong_si = 36;
    public static final int gyeonggi_do = 41;
    public static final int chungcheongbuk_do = 43;
    public static final int chungcheongnam_do = 44;
    public static final int jellanam_do = 46;
    public static final int gyeongsangbuk_do = 47;
    public static final int gyeongsangnam_do = 48;
    public static final int jeju_do = 50;
    public static final int gangwon_do = 51;
    public static final int jeonbuk_do = 52;

    public static Map<String, Set<RegionDetail>> region = new HashMap<>();
    public static void loadData(Activity activity) throws IOException, CsvException {
        AssetManager assetManager = activity.getAssets();
        InputStream inputStream = assetManager.open("region.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, "UTF-8"));

        List<String[]> allContent = (List<String[]>) csvReader.readAll();
        int count =-1;

        List<String> si = new ArrayList<>();
        List<String> gu = new ArrayList<>();
        for(String content[] : allContent){
            StringBuilder sb = new StringBuilder("");
            if (content[5].equals("0")){
                count++;
                si.clear();
                gu.clear();
                Set<RegionDetail> details = new HashSet<>();
                region.put(content[4], details);
            }else if (content[5].equals("1")){
                String [] sido = content[9].split(" ");
                RegionDetail item = new RegionDetail();
                String concat = content[4];
                if (sido.length == 2){
                    item.si = sido[0];
                    item.gu = sido[1];
                }else {
                    item.si = content[9];
                    item.gu = "";
                    concat = content[9];
                }
                CenterPoint centerPoint = new CenterPoint(Float.valueOf(content[3]),Float.valueOf(content[2]));
                item.center_point = centerPoint;
                region.get(content[7]).add(item);
            }
            Log.e("csv", content[0]+","+ content[1]+","+content[2]+","+content[3]+","+content[4]+","+content[5]+",");
        }
    }

    public void check_region(String hi){
        Log.e("hi region name", hi);
        region.keySet().forEach(s -> {
            Log.e("지역", s);

        });
        Log.e("region size", region.get(hi).size()+"");
        region.get(hi).forEach(s -> {
            Log.e("hi si name", s.si);
            Log.e("hi gu name", s.gu);
            Log.e("hi center point",  s.center_point.longitude+","+s.center_point.latitude);

        });



    }



}
