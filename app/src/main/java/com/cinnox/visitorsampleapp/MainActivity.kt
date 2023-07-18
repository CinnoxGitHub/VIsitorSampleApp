package com.cinnox.visitorsampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.m800.cinnox.visitor.CinnoxPushListener
import com.m800.cinnox.visitor.CinnoxVisitorCore
import com.m800.cinnox.visitor.CinnoxVisitorCoreListener
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    companion object{
        const val serviceId = "ao-awsjptest06.cinnox.com"
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

    private val mCinnoxPushListener: CinnoxPushListener = object : CinnoxPushListener{
        override fun onPushMessage(message: JSONObject?) {
            Log.d(TAG, "onPushMessage, message: $message")
        }

    }

    private fun init(){
        val core = CinnoxVisitorCore.initialize(this, serviceId)
        core.registerListener(mCoreListener)
        core.registerPushListener(this, mCinnoxPushListener)
    }
}