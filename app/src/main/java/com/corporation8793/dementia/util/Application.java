package com.corporation8793.dementia.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.corporation8793.dementia.MySharedPreferences;
import com.corporation8793.dementia.data.User;

public class Application {

    private static Application application = null;

    public static int standardSize_X, standardSize_Y, displaySize_X, displaySize_Y;
    public static float density;

    public static User user = null;

    public static float fontSize;

    public Application() {
    }

    public static Application getInstance() {

        if (application == null) {
            application = new Application();
        }

        return application;
    }

    public static void getFontSize(String sizeString) {
        Log.e("testt", sizeString);

        switch (sizeString) {
            case "크게":
                fontSize = 1f;
                break;
            case "중간":
                fontSize = 0.8f;
                break;
            case "작게":
                fontSize = 0.6f;
                break;
        }

        Log.e("testt", "fontSize : " + fontSize);
    }

    public static void setFontSize() {
        DisplayFontSize.font_size_x_12 = (float) (Application.standardSize_X /60) * Application.fontSize;
        DisplayFontSize.font_size_x_15 = (float) (Application.standardSize_X /48) * Application.fontSize;
        DisplayFontSize.font_size_x_16 = (float) (Application.standardSize_X /45) * Application.fontSize;
        DisplayFontSize.font_size_x_20 = (float) (Application.standardSize_X /36) * Application.fontSize;
        DisplayFontSize.font_size_x_22 = (float) (Application.standardSize_X /32.7) * Application.fontSize;
        DisplayFontSize.font_size_x_24 = (float) (Application.standardSize_X /30) * Application.fontSize;
        DisplayFontSize.font_size_x_25 = (float) (Application.standardSize_X /28.8) * Application.fontSize;
        DisplayFontSize.font_size_x_26 = (float) (Application.standardSize_X /27.7) * Application.fontSize;
        DisplayFontSize.font_size_x_28 = (float) (Application.standardSize_X /25.7) * Application.fontSize;
        DisplayFontSize.font_size_x_30 = (float) (Application.standardSize_X /24) * Application.fontSize;
        DisplayFontSize.font_size_x_32 = (float) (Application.standardSize_X /22.5) * Application.fontSize;
        DisplayFontSize.font_size_x_34 = (float) (Application.standardSize_X /21.18) * Application.fontSize;
        DisplayFontSize.font_size_x_36 = (float) (Application.standardSize_X /20) * Application.fontSize;
        DisplayFontSize.font_size_x_38 = (float) (Application.standardSize_X /18.95) * Application.fontSize;
        DisplayFontSize.font_size_x_40 = (float) (Application.standardSize_X /18) * Application.fontSize;
        DisplayFontSize.font_size_x_44 = (float) (Application.standardSize_X /16.4) * Application.fontSize;
        DisplayFontSize.font_size_x_46 = (float) (Application.standardSize_X /15.65) * Application.fontSize;
        DisplayFontSize.font_size_x_50 = (float) (Application.standardSize_X /14.4) * Application.fontSize;
        DisplayFontSize.font_size_x_55 = (float) (Application.standardSize_X /13.1) * Application.fontSize;
        DisplayFontSize.font_size_x_56 = (float) (Application.standardSize_X /12.9) * Application.fontSize;
        DisplayFontSize.font_size_x_60 = (float) (Application.standardSize_X /12) * Application.fontSize;
        DisplayFontSize.font_size_x_66 = (float) (Application.standardSize_X /10.91) * Application.fontSize;
        DisplayFontSize.font_size_x_70 = (float) (Application.standardSize_X /10.29) * Application.fontSize;
        DisplayFontSize.font_size_x_92 = (float) (Application.standardSize_X /7.83) * Application.fontSize;
    }

    public static void setUserData(User userData) {
        user = userData;
    }

    private static Point getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }

    private static int getNavigationBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int usableHeight = metrics.heightPixels;
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

        int realHeight = metrics.heightPixels;

        if (realHeight > usableHeight)
            return realHeight - usableHeight;
        else
            return 0;
    }

    public static void getStandardSize(Activity activity) {
        Point ScreenSize = getScreenSize(activity);
        density  = activity.getResources().getDisplayMetrics().density;

        standardSize_X = (int) (ScreenSize.x / density);
        standardSize_Y = (int) ((ScreenSize.y + getNavigationBarHeight(activity)) / density);

        displaySize_X = ScreenSize.x;
        displaySize_Y = ScreenSize.y + getNavigationBarHeight(activity);

        Log.e("testtest", "standardSize x : " + standardSize_X);
        Log.e("testtest", "standardSize y : " + standardSize_Y);

        Log.e("testtest", "displaySize x : " + displaySize_X);
        Log.e("testtest", "displaySize y : " + displaySize_Y);
    }

    public static void FullScreenMode(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    public static String region_long_to_short(String region) {
        String long_region = "";

        switch (region) {
            case "서울특별시":
            case "서울시":
                long_region = "서울";
                break;
            case "경기도":
                long_region = "경기";
                break;
            case "인천광역시":
                long_region = "인천";
                break;
            case "세종특별자치시":
                long_region = "세종";
                break;
            case "강원특별자치도":
            case "강원도":
                long_region = "강원";
                break;
            case "대전광역시":
                long_region = "대전";
                break;
            case "충청북도":
                long_region = "충북";
                break;
            case "충청남도":
                long_region = "충남";
                break;
            case "부산광역시":
                long_region = "부산";
                break;
            case "울산광역시":
                long_region = "울산";
                break;
            case "대구광역시":
                long_region = "대구";
                break;
            case "경상북도":
                long_region = "경북";
                break;
            case "경상남도":
                long_region = "경남";
                break;
            case "광주광역시":
                long_region = "광주";
                break;
            case "전북특별자치도":
            case "전라북도":
                long_region = "전북";
                break;
            case "전라남도":
                long_region = "전남";
                break;
            case "제주특별자치도":
                long_region = "제주";
                break;
            default:
                long_region = "없음";
                break;
        }

        Log.e("test2", long_region);

        return long_region;
    }

    public static String region_long_to_short_contains(String region) {
        String long_region = "";

        if (region.contains("서울특별시") || region.contains("서울시")) {
            long_region = "서울";
        } else if (region.contains("경기도")) {
            long_region = "경기";
        } else if (region.contains("인천광역시")) {
            long_region = "인천";
        } else if (region.contains("세종특별자치시")) {
            long_region = "세종";
        } else if (region.contains("강원특별자치도") || region.contains("강원도")) {
            long_region = "강원";
        } else if (region.contains("대전광역시")) {
            long_region = "대전";
        } else if (region.contains("충청북도")) {
            long_region = "충북";
        } else if (region.contains("충청남도")) {
            long_region = "충남";
        } else if (region.contains("부산광역시")) {
            long_region = "부산";
        } else if (region.contains("울산광역시")) {
            long_region = "울산";
        } else if (region.contains("대구광역시")) {
            long_region = "대구";
        } else if (region.contains("경상북도")) {
            long_region = "경북";
        } else if (region.contains("경상남도")) {
            long_region = "경남";
        } else if (region.contains("광주광역시")) {
            long_region = "광주";
        } else if (region.contains("전북특별자치도") || region.contains("전라북도")) {
            long_region = "전북";
        } else if (region.contains("전라남도")) {
            long_region = "전남";
        } else if (region.contains("제주특별자치도")) {
            long_region = "제주";
        } else {
            long_region = "없음";
        }

        Log.e("test2", long_region);

        return long_region;
    }

    public static String region_short_to_long(String region) {
        String short_region = "";

        switch (region) {
            case "서울":
                short_region = "서울특별시";
                break;
            case "경기":
                short_region = "경기도";
                break;
            case "인천":
                short_region = "인천광역시";
                break;
            case "세종":
                short_region = "세종특별자치시";
                break;
            case "강원":
                short_region = "강원특별자치도";
                break;
            case "대전":
                short_region = "대전광역시";
                break;
            case "충북":
                short_region = "충청북도";
                break;
            case "충남":
                short_region = "충청남도";
                break;
            case "부산":
                short_region = "부산광역시";
                break;
            case "울산":
                short_region = "울산광역시";
                break;
            case "대구":
                short_region = "대구광역시";
                break;
            case "경북":
                short_region = "경상북도";
                break;
            case "경남":
                short_region = "경상남도";
                break;
            case "광주":
                short_region = "광주광역시";
                break;
            case "전북":
                short_region = "전북특별자치도";
                break;
            case "전남":
                short_region = "전라남도";
                break;
            case "제주":
                short_region = "제주특별자치도";
                break;
            default:
                short_region = "없음";
                break;
        }

        Log.e("test2", short_region);

        return short_region;
    }
}
