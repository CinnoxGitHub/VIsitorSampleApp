package com.cinnox.visitorsampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cinnox.visitorsampleapp.push.genFcmIntentPushData
import com.m800.cinnox.visitor.CinnoxVisitorCore
import com.m800.sdk.core.noti.CinnoxPushType

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleClickedSystemNotification()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleClickedSystemNotification()
    }

    private fun handleClickedSystemNotification() {
        genFcmIntentPushData(intent)?.let { data ->
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