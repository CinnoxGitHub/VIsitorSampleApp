package com.cinnox.visitorsampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cinnox.visitorsampleapp.push.genFcmIntentData
import com.m800.cinnox.visitor.CinnoxPushListener
import com.m800.cinnox.visitor.CinnoxVisitorCore
import com.m800.sdk.core.noti.CinnoxPushType
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        handleClickedSystemNotification()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleClickedSystemNotification()
    }

    private val mCinnoxPushListener: CinnoxPushListener = object : CinnoxPushListener {
        override fun onPushMessage(message: JSONObject?) {
            Log.d(TAG, "onPushMessage, message: $message")
        }

    }

    private fun init() {
        val core = CinnoxVisitorCore.getInstance()
        core.registerPushListener(this, mCinnoxPushListener)
    }

    private fun handleClickedSystemNotification() {
        genFcmIntentData(intent)?.let { data ->
            if (data.length() == 0) {
                Log.i(TAG, "handleClickedSystemNotification data is empty")
                return
            }
            Log.i(TAG, "handleClickedSystemNotification data: $data")
            CinnoxVisitorCore.getInstance().getPushManager().handleClickedSystemNotification(
                CinnoxPushType.FCM,
                data
            )
        } ?: run {
            Log.i(TAG, "handleClickedSystemNotification data is null")
        }
    }
}