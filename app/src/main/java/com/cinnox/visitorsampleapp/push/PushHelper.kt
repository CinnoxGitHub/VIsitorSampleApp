package com.cinnox.visitorsampleapp.push

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

private val TAG = "PushHelper"

fun genFcmRemoteMessageData(remoteMessage: RemoteMessage): JSONObject? {
    return try {
        JSONObject(remoteMessage.data.toMap())
    } catch (e: Exception) {
        Log.e(TAG, "genFcmRemoteMessageData error", e)
        null
    }
}

fun genFcmIntentData(intent: Intent): JSONObject? {
    return intent.extras?.convertToJson()
}

private fun Bundle.convertToJson(): JSONObject {
    val jsonObject = JSONObject()
    this.keySet().forEach { key ->
        try {
            jsonObject.put(key, JSONObject.wrap(this.get(key)))
        } catch (e: Exception) {
            Log.e(TAG, "Bundle convertToJson error", e)
        }
    }
    return jsonObject
}

