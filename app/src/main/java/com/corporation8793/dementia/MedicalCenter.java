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

public class MedicalCenter {

    public static Map<String, Set<MedicalCenterDetail>> medicalCenter = new HashMap<>();

    static int num = 0, count = 0, count2 = 0, count3 = 0;

    public static void loadData(Activity activity) throws IOException, CsvException {
        AssetManager assetManager = activity.getAssets();
        InputStream inputStream = assetManager.open("medical_center.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, "EUC-KR"));

        List<String[]> allContent = (List<String[]>) csvReader.readAll();
        // 총 347개 > 첫 줄 제외하면 346이어야 함
        Log.e("test222", "allContent : " + allContent.size());

        for(String content[] : allContent){
            // 치매센터명 0, 소재지도로명주소 2, 소재지지번주소 3, 위도 4, 경도 5, 운영기관 전화번호 15
            Log.e("csv", content[0]+","+ content[2]+","+ content[3]+","+content[4]+","+content[5]+","+content[15]);

            // 첫줄 제외
            if (!content[0].equals("치매센터명")) {
//                count++;
//                Log.e("count : ", count+"");

                // 도로명
                String [] sido = content[2].split(" ");
                // 지번
                String [] sido2 = content[3].split(" ");

//                for (String s : sido) {
//                    Log.e("test222", "sido : " + s);
//                }
//
//                for (String s : sido2) {
//                    Log.e("test222", "sido2 : " + s);
//                }

                MedicalCenterDetail item = new MedicalCenterDetail();
                item.name = content[0];
                item.center_number = content[15];

                if (content[0].equals("대전서구치매안심센터")) {
                    item.si = "대전광역시";
                    item.gu = "서구";

                    item.address = "대전광역시 서구 둔산서로 100, 건강체련관 3층";
                } else if (content[0].equals("태안군치매안심센터")) {
                    item.si = "충청남도";
                    item.gu = "태안군";

                    item.address = "충청남도 태안군 태안읍 서해로 1952-16";
                }
                /*else if (content[0].equals("김포시치매안심센터 보건사업과")) {
                    item.si = "경기도";
                    item.gu = "김포시";
                } else if (content[0].equals("북부보건과 치매관리팀")) {
                    item.si = "경기도";
                    item.gu = "김포시";
                }*/  else {
                    // 도로명이 없으면 지번으로 분류
                    if (sido.length == 1) {
                        Log.e("testtestt", content[0]);

                        item.si = sido2[0];
                        item.gu = sido2[1];

                        item.address = content[3];
                    } else {
                        // 도로명 분류
                        item.si = sido[0];
                        item.gu = sido[1];

                        item.address = content[2];
                    }
                }

                if (content[0].equals("성북구치매안심센터")) {
                    CenterPoint centerPoint = new CenterPoint(Float.valueOf("37.602942"),Float.valueOf("127.039536"));
                    item.center_point = centerPoint;
                } else {
                    CenterPoint centerPoint = new CenterPoint(Float.valueOf(content[4]),Float.valueOf(content[5]));
                    item.center_point = centerPoint;
                }

                Set<MedicalCenterDetail> details = new HashSet<>();
                details.add(item);

                if (!content[2].trim().isEmpty()) {
                    count2++;
//                    medicalCenter.put(content[2]+"::"+count2, details);

                    medicalCenter.put(content[2], details);

                    //Log.e("count2 : ", count2+"");
                } else {
                    count2++;
//                    medicalCenter.put(content[3]+"::"+count2, details);

                    medicalCenter.put(content[3], details);

                    //Log.e("count2 : ", count2+"");
                }

//                medicalCenter.put(content[0], details);

//                if (content[0].equals("대전서구치매안심센터")) {
//                    medicalCenter.put("대전광역시", details);
//                } else if (content[0].equals("태안군치매안심센터")) {
//                    medicalCenter.put("충청남도", details);
//                } else {
//                    // 도로명이 없으면 지번으로 분류
//                    if (sido.length == 1) {
//                        medicalCenter.put(sido2[0], details);
//                    } else {
//                        medicalCenter.put(sido[0], details);
//                    }
//                }
            } else {
                Log.e("testtestt", content[0]);
            }
        }

        medicalCenter.keySet().forEach(s -> {
            Log.e("주소", s);

            num++;
            Log.e("number : ", num+"");
        });
    }

    public List<MedicalCenterDetail> get_center_location(String si, String gu){
        Log.e("region name", si);
        Log.e("region size", medicalCenter.size() + "");

        List<MedicalCenterDetail> center_list = new ArrayList<>();

        medicalCenter.keySet().forEach(s -> {
            //Log.e("keySet name", s);

            switch (si) {
                case "서울특별시":
                    if (s.contains(si) || s.contains("서울시")) {
                        Log.e("매칭 시", s);

                        if (s.contains(gu)) {
                            Log.e("매칭 구", s);

                            count3++;

                            medicalCenter.get(s).forEach(s2 -> {
                                Log.e("s2 loop", "s2 loop");

                                MedicalCenterDetail item = new MedicalCenterDetail();
                                item.name = s2.name;
                                item.si = s2.si;
                                item.gu = s2.gu;
                                item.address = s2.address;
                                item.center_point = s2.center_point;
                                item.center_number = s2.center_number;

                                center_list.add(item);
                            });
                        }
                    }
                    break;
                case "강원특별자치도":
                    if (s.contains(si) || s.contains("강원도")) {
                        Log.e("매칭 시", s);

                        if (s.contains(gu)) {
                            Log.e("매칭 구", s);

                            count3++;

                            medicalCenter.get(s).forEach(s2 -> {
                                Log.e("s2 loop", "s2 loop");

                                MedicalCenterDetail item = new MedicalCenterDetail();
                                item.name = s2.name;
                                item.si = s2.si;
                                item.gu = s2.gu;
                                item.address = s2.address;
                                item.center_point = s2.center_point;
                                item.center_number = s2.center_number;

                                center_list.add(item);
                            });
                        }
                    }
                    break;
                case "전북특별자치도":
                    if (s.contains(si) || s.contains("전라북도")) {
                        Log.e("매칭 시", s);

                        if (s.contains(gu)) {
                            Log.e("매칭 구", s);

                            count3++;

                            medicalCenter.get(s).forEach(s2 -> {
                                Log.e("s2 loop", "s2 loop");

                                MedicalCenterDetail item = new MedicalCenterDetail();
                                item.name = s2.name;
                                item.si = s2.si;
                                item.gu = s2.gu;
                                item.address = s2.address;
                                item.center_point = s2.center_point;
                                item.center_number = s2.center_number;

                                center_list.add(item);
                            });
                        }
                    }
                    break;
                case "세종특별자치시":
                    if (s.contains(si)) {
                        Log.e("매칭 시", s);

                        count3++;

                        medicalCenter.get(s).forEach(s2 -> {
                            Log.e("s2 loop", "s2 loop");

                            MedicalCenterDetail item = new MedicalCenterDetail();
                            item.name = s2.name;
                            item.si = s2.si;
                            item.gu = s2.gu;
                            item.address = s2.address;
                            item.center_point = s2.center_point;
                            item.center_number = s2.center_number;

                            center_list.add(item);
                        });
                    }
                    break;
                default:
                    if (s.contains(si)) {
                        Log.e("매칭 시", s);

                        if (s.contains(gu)) {
                            Log.e("매칭 구", s);

                            count3++;

                            medicalCenter.get(s).forEach(s2 -> {
                                Log.e("s2 loop", "s2 loop");

                                MedicalCenterDetail item = new MedicalCenterDetail();
                                item.name = s2.name;
                                item.si = s2.si;
                                item.gu = s2.gu;
                                item.address = s2.address;
                                item.center_point = s2.center_point;
                                item.center_number = s2.center_number;

                                center_list.add(item);
                            });
                        }
                    }
                    break;
            }
        });

        //Log.e("count3 : ", count3+"");
        Log.e("center_list", center_list.size()+"");

        for (int i = 0; i < center_list.size(); i++) {
            Log.e("center_list", center_list.get(i).name);
            Log.e("center_list", center_list.get(i).si);
            Log.e("center_list", center_list.get(i).gu);
            Log.e("center_list", center_list.get(i).address);
            Log.e("center_list", center_list.get(i).center_point.latitude + "");
            Log.e("center_list", center_list.get(i).center_point.longitude + "");
        }

        return center_list;

//        Log.e("region name", si);
//        Log.e("region size", medicalCenter.get(si).size() + "");
//
//        List<MedicalCenterDetail> center_list = new ArrayList<>();
//
//        medicalCenter.get(si).forEach(s -> {
//            Log.e("name : ", s.name);
//            Log.e("si : ", s.si);
//            Log.e("gu : ", s.gu);
//            Log.e("center point",  s.center_point.longitude+","+s.center_point.latitude);
//
//            if (s.gu.equals(gu)) {
//                center_list.add(s);
//            }
//        });
//
//        Log.e("center_list", center_list+"");
//
//        return center_list;
    }
}
