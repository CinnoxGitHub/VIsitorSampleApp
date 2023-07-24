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
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }


    private val mCinnoxPushListener: CinnoxPushListener = object : CinnoxPushListener {
        override fun onPushMessage(message: JSONObject?) {
            Log.d(TAG, "onPushMessage, message: $message")
        }

    }

    private fun init() {
        val core = CinnoxVisitorCore.getInstance()
        core?.registerPushListener(this, mCinnoxPushListener)
    }
}