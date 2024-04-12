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
        const val serviceId = "xxxx.cinnox.com"
        const val key = "xxxxx"
        val pushType = CinnoxPushType.FCM
    }

    private val mCoreListener: CinnoxVisitorCoreListener = object : CinnoxVisitorCoreListener {
        override fun onInitializationEnd(success: Boolean, throwable: Throwable?) {
            Log.d(MainActivity.TAG, "onInitializationEnd, isSuccess: $success, throwable: $throwable")
        }
    }
    override fun onCreate() {
        super.onCreate()
        val core = CinnoxVisitorCore.initialize(this, serviceId, key, mCoreListener)
        when (pushType) {
            CinnoxPushType.FCM -> FcmPushService().initialize(this)
            CinnoxPushType.XIAOMI -> XiaomiPushService().initialize(this)
            CinnoxPushType.HUAWEI -> HuaweiPushService().initialize(this)
        }
    }
}