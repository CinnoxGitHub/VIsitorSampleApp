package com.cinnox.visitorsampleapp.push

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

private val TAG = "FcmPushHelper"

fun genFcmRemoteMessagePushData(remoteMessage: RemoteMessage): JSONObject? {
    return try {
        JSONObject(remoteMessage.data.toMap())
    } catch (e: Exception) {
        Log.e(TAG, "genFcmRemoteMessagePushData error", e)
        null
    }
}

fun genFcmIntentPushData(intent: Intent): JSONObject? {
    return intent.extras?.convertToJson()
}

