package com.cinnox.visitorsampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.m800.maaiiconnect.mobile.client.CinnoxVisitorCore
import com.m800.maaiiconnect.mobile.client.CinnoxVisitorCoreListener

class MainActivity : AppCompatActivity() {
    companion object{
        const val serviceId = "xxxx.cinnox.com"
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private val mCoreListener: CinnoxVisitorCoreListener = object : CinnoxVisitorCoreListener{
        override fun onInitializationEnd(success: Boolean, throwable: Throwable?) {
            Log.d(TAG, "onInitializationEnd, isSuccess: $success, throwable: $throwable")
        }
    }

    private fun init(){
        val core = CinnoxVisitorCore.initialize(this, serviceId)
        core.registerListener(mCoreListener)
    }
}