package com.corporation8793.dementia.util;

public class DisplayFontSize {
    // display x : (720(기준) / dp or sp) 로 계산
    //여기에 글씨 설정에 따라 곱하기(크게 : 1, 중간 : 0.8, 작게 : 0.6) >> 애플리케이션에서 쉐이드프리퍼런스 같이 사용해서 저장하자
    public static float font_size_x_12 = (float) (Application.standardSize_X /60) * Application.fontSize;
    public static float font_size_x_15 = (float) (Application.standardSize_X /48) * Application.fontSize;
    public static float font_size_x_16 = (float) (Application.standardSize_X /45) * Application.fontSize;
    public static float font_size_x_20 = (float) (Application.standardSize_X /36) * Application.fontSize;
    public static float font_size_x_22 = (float) (Application.standardSize_X /32.7) * Application.fontSize;
    public static float font_size_x_24 = (float) (Application.standardSize_X /30) * Application.fontSize;
    public static float font_size_x_25 = (float) (Application.standardSize_X /28.8) * Application.fontSize;
    public static float font_size_x_26 = (float) (Application.standardSize_X /27.7) * Application.fontSize;
    public static float font_size_x_28 = (float) (Application.standardSize_X /25.7) * Application.fontSize;
    public static float font_size_x_30 = (float) (Application.standardSize_X /24) * Application.fontSize;
    public static float font_size_x_32 = (float) (Application.standardSize_X /22.5) * Application.fontSize;
    public static float font_size_x_34 = (float) (Application.standardSize_X /21.18) * Application.fontSize;
    public static float font_size_x_36 = (float) (Application.standardSize_X /20) * Application.fontSize;
    public static float font_size_x_38 = (float) (Application.standardSize_X /18.95) * Application.fontSize;
    public static float font_size_x_40 = (float) (Application.standardSize_X /18) * Application.fontSize;
    public static float font_size_x_44 = (float) (Application.standardSize_X /16.4) * Application.fontSize;
    public static float font_size_x_46 = (float) (Application.standardSize_X /15.65) * Application.fontSize;
    public static float font_size_x_50 = (float) (Application.standardSize_X /14.4) * Application.fontSize;
    public static float font_size_x_55 = (float) (Application.standardSize_X /13.1) * Application.fontSize;
    public static float font_size_x_56 = (float) (Application.standardSize_X /12.9) * Application.fontSize;
    public static float font_size_x_60 = (float) (Application.standardSize_X /12) * Application.fontSize;
    public static float font_size_x_66 = (float) (Application.standardSize_X /10.91) * Application.fontSize;
    public static float font_size_x_70 = (float) (Application.standardSize_X /10.29) * Application.fontSize;
    public static float font_size_x_92 = (float) (Application.standardSize_X /7.83) * Application.fontSize;


    // display y : (1280(기준) / dp or sp) 로 계산
    public static float font_size_y_34 = (float) (Application.standardSize_Y /37.65);


    // display x : (720(기준) / dp or sp) 로 표준이 아닌 실제 크기로 계산
    public static float size_x_20 = (float) (Application.displaySize_X /36);
    public static float size_x_200 = (float) (Application.displaySize_X /3.6);

    // display y : (1280(기준) / dp or sp) 로 표준이 아닌 실제 크기로 계산
    public static float size_y_42 = (float) (Application.displaySize_Y /30.48);
    public static float size_y_58 = (float) (Application.displaySize_Y /22.07);
    public static float size_y_80 = (float) (Application.displaySize_Y /16);
}
