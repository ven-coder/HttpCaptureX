package com.levine.netcaptureX;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;

public class NCP implements Application.ActivityLifecycleCallbacks {
    private static NCP sNCP;
    private Application mApplication;
    private boolean isInitialized = false;
    public static float coordinateX = 0;
    public static float coordinateY = ScreenUtils.getScreenHeight() / 2 / 2;
    public static boolean isEnableActivityFloatView = true; //是否允许显示浮窗，默认允许
    private boolean isShowFloatView = true;
    private Activity currentActivity;

    public static NCP init(Application application) {
        if (sNCP == null) {
            synchronized (NCP.class) {
                if (sNCP == null) {
                    sNCP = new NCP(application);
                }
            }
        }
        return sNCP;
    }

    private NCP(Application application) {
        mApplication = application;
        init();
    }

    public static NCP getInstance() {
        if (sNCP == null) throw new RuntimeException("请先初始化->NCP.init()");
        return sNCP;
    }

    private void init() {
        if (isInitialized) return;
        mApplication.registerActivityLifecycleCallbacks(this);
    }

    public boolean isShowFloatView() {
        return isShowFloatView;
    }

    public void setShowFloatView(boolean showFloatView) {
        isShowFloatView = showFloatView;
        if (currentActivity == null || !isShowFloatView) return;
        ViewGroup decorView = (ViewGroup) currentActivity.getWindow().getDecorView();
        FloatView floatView = new FloatView(currentActivity);
        floatView.setTag(currentActivity.getClass().getName());
        floatView.setCoordinate(coordinateX, coordinateY);
        decorView.addView(floatView);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        currentActivity = activity;
        if (activity.getClass().getName().equals(NetCaptureRecordActivity.class.getName()) || !isShowFloatView) {
            return;
        }
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        FloatView floatView = new FloatView(activity);
        floatView.setTag(activity.getClass().getName());
        floatView.setCoordinate(coordinateX, coordinateY);
        decorView.addView(floatView);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        FloatView floatView = decorView.findViewWithTag(activity.getClass().getName());
        if (floatView != null) floatView.setCoordinate(coordinateX, coordinateY);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
