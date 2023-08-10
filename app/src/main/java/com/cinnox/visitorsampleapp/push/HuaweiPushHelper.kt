package com.cinnox.visitorsampleapp.push

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

private val TAG = "HuaweiPushHelper"
private const val KEY_DATA = "data"

fun genHuaweiRemoteMessagePushData(remoteMessage: RemoteMessage): JSONObject? {
    return remoteMessage.data?.convertToJson()
}

fun genHuaweiIntentPushData(intent: Intent): JSONObject? {
    return intent.extras?.getString(KEY_DATA)?.convertToJson()
}

private fun String.convertToJson(): JSONObject? {
    return try {
        JSONObject(this)
    } catch (e: Exception) {
        Log.e(TAG, "String convertToJson error", e)
        null
    }
}

