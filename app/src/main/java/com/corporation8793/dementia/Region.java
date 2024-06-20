package com.corporation8793.dementia;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

    String location = "";

    static List<String> check_si = new ArrayList<>();

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
                check_si.clear();
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

                if (check_si.isEmpty()) {
                    Log.e("testtsettt", "1 : " + item.si);
                    check_si.add(item.si);
                    region.get(content[7]).add(item);

                    Log.e("testtsettt", "1 : " + check_si.size());
                    Log.e("testtsettt", "---------------");
                } else {
                    for (int i = 0; i < check_si.size(); i++) {
                        if (!check_si.get(i).equals(item.si)) {
                            Log.e("testtsettt", "2 : " + item.si);
                            Log.e("testtsettt", "2 : " + check_si.get(i));
                            region.get(content[7]).add(item);
                            Log.e("testtsettt", "---------------");
                        } else {
                            Log.e("testtsettt", "3 : " + item.si);
                            Log.e("testtsettt", "3 : " + check_si.get(i));
                            Log.e("testtsettt", "---------------");
                        }
                    }
                }

                //region.get(content[7]).add(item);

                // 여기에서 중복 제외하기(두 단계로만 분류 : 예시)광주광역시-북구)
//                remove_region(content[7], item);

//                if (region.get(content[7]).isEmpty()) {
//                    region.get(content[7]).add(item);
//                } else {
//                    region.get(content[7]).forEach(s -> {
//                        if (!s.si.equals(item.si)) {
//                            region.get(content[7]).add(item);
//                        }
//                    });
//                }
            }
            Log.e("csv", content[0]+","+ content[1]+","+content[2]+","+content[3]+","+content[4]+","+content[5]+",");
        }
    }

    private static void remove_region(String name, RegionDetail item) {
        if (region.get(name).isEmpty()) {
            region.get(name).add(item);
        } else {
            region.get(name).forEach(s -> {
                if (!s.si.equals(item.si)) {
                    region.get(name).add(item);
                }
            });
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

    public int check_region_num(String hi){
        Log.e("hi region name", hi);
        Log.e("region size", region.get(hi).size()+"");

        region.get(hi).forEach(s -> {
            Log.e("hi si name", s.si);
            Log.e("hi gu name", s.gu);
            Log.e("hi center point",  s.center_point.longitude+","+s.center_point.latitude);
        });


        return region.get(hi).size();
    }

    public List<String> check_region_String(String hi){
        Log.e("hi region name", hi);
        Log.e("region size", region.get(hi).size()+"");

        List<String> name_list = new ArrayList<>();

        region.get(hi).forEach(s -> {
            Log.e("hi si name", s.si);

            name_list.add(s.si);
        });

        return name_list;

        //            if (name_list.isEmpty()) {
//                name_list.add(s.si);
//            } else {
//                for (int i = 0; i < name_list.size(); i++) {
//                    // 중복이 아닌 경우만 추가
//                    if (!name_list.get(i).equals(s.si)) {
//                        name_list.add(s.si);
//                    }
//                }
//            }
    }

    public String get_location(String name) {
        region.get(name).forEach(s -> {
            Log.e("test", s.si);
            Log.e("test", s.gu);
            location = Float.toString(s.center_point.longitude) + "," + Float.toString(s.center_point.latitude);
        });

        return location;
    }
}
