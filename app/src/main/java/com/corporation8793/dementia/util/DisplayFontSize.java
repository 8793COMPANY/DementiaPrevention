package com.corporation8793.dementia.util;

public class DisplayFontSize {
    // display x : (720(기준) / dp or sp) 로 계산
    public static float font_size_x_30 = (float) (Application.standardSize_X /24);
    public static float font_size_x_32 = (float) (Application.standardSize_X /22.5);
    public static float font_size_x_34 = (float) (Application.standardSize_X /21.18);
    public static float font_size_x_36 = (float) (Application.standardSize_X /20);
    public static float font_size_x_40 = (float) (Application.standardSize_X /18);
    public static float font_size_x_46 = (float) (Application.standardSize_X /15.65);
    public static float font_size_x_60 = (float) (Application.standardSize_X /12);

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
