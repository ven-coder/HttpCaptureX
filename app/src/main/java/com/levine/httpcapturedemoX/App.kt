package com.levine.httpcapturedemoX

import android.app.Application
import com.levine.netcaptureX.NCP

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NCP.init(this)
    }
}