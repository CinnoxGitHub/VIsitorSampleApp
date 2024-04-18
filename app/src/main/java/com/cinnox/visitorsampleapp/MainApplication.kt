package com.cinnox.visitorsampleapp

import android.app.Application
import android.util.Log
import com.cinnox.visitorsampleapp.push.FcmPushService
import com.cinnox.visitorsampleapp.push.HuaweiPushService
import com.cinnox.visitorsampleapp.push.XiaomiPushService
import com.m800.cinnox.visitor.CinnoxVisitorCore
import com.m800.cinnox.visitor.CinnoxVisitorCoreListener
import com.m800.sdk.core.noti.CinnoxPushType

class MainApplication : Application() {
    companion object {
        private const val TAG = "MainApplication"
        private const val SERVICE_NAME = "xxxx.cinnox.com"
        private const val KEY = "xxxxx"
        val pushType = CinnoxPushType.FCM
    }

    override fun onCreate() {
        super.onCreate()
        CinnoxVisitorCore.getInstance().initialize(
            this,
            SERVICE_NAME,
            KEY
        ).registerListener(object : CinnoxVisitorCoreListener {
            override fun onInitializationEnd(success: Boolean, throwable: Throwable?) {
                Log.d(TAG, "onInitializationEnd, isSuccess: $success, throwable: $throwable")
                when (pushType) {
                    CinnoxPushType.FCM -> FcmPushService().initialize(this@MainApplication)
                    CinnoxPushType.XIAOMI -> XiaomiPushService().initialize(this@MainApplication)
                    CinnoxPushType.HUAWEI -> HuaweiPushService().initialize(this@MainApplication)
                    else -> Unit
                }
            }
        })
    }
}