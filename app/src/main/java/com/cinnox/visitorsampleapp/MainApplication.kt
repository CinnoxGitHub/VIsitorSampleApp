package com.cinnox.visitorsampleapp

import android.app.Application
import android.util.Log
import com.cinnox.visitorsampleapp.push.FcmPushService
import com.m800.cinnox.visitor.CinnoxVisitorCore
import com.m800.cinnox.visitor.CinnoxVisitorCoreListener

class MainApplication : Application() {
    companion object {
        const val serviceId = "xxxx.cinnox.com"
    }

    private val mCoreListener: CinnoxVisitorCoreListener = object : CinnoxVisitorCoreListener {
        override fun onInitializationEnd(success: Boolean, throwable: Throwable?) {
            Log.d(MainActivity.TAG, "onInitializationEnd, isSuccess: $success, throwable: $throwable")
        }
    }
    override fun onCreate() {
        super.onCreate()
        val core = CinnoxVisitorCore.initialize(this, serviceId)
        core.registerListener(mCoreListener)
        FcmPushService().initialize(this)
    }
}