package com.corporation8793.dementia.util;

import android.app.Activity;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

public class Application {

    private static Application application = null;

    public static int standardSize_X, standardSize_Y, displaySize_X, displaySize_Y;
    public static float density;

    public Application() {
    }

    public static Application getInstance() {

        if (application == null) {
            application = new Application();
        }

        return application;
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
}
