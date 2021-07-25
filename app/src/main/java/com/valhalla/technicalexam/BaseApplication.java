package com.valhalla.technicalexam;

import android.app.Application;

import com.valhalla.technicalexam.api.APIService;
import com.valhalla.technicalexam.api.GsonService;
import com.valhalla.technicalexam.api.RetrofitService;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        getRetrofitService();
        GsonService.getInstance();

    }

    public APIService getRetrofitService(){
        return RetrofitService.getInstance();
    }
}
