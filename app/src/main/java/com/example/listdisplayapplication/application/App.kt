package com.application

import android.app.Application
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.example.listdisplayapplication.R
import com.example.listdisplayapplication.helper.ObjectBox
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)

        AndroidNetworking.initialize(applicationContext)


        ViewPump.init(ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                                .build()))
                .build())

    }





}