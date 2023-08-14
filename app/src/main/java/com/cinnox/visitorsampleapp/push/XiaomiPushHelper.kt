package com.cinnox.visitorsampleapp.push

import android.content.Intent
import com.xiaomi.mipush.sdk.MiPushMessage
import org.json.JSONObject

private val TAG = "XiaomiPushHelper"
private const val KEY_MESSAGE = "key_message"

fun genXiaomiRemoteMessagePushData(remoteMessage: MiPushMessage): JSONObject? {
    return remoteMessage.content?.convertToJson()
}

fun genXiaomiIntentPushData(intent: Intent): JSONObject? {
    return (intent.extras?.getSerializable(KEY_MESSAGE) as? MiPushMessage)?.let {
        genXiaomiRemoteMessagePushData(it)
    }
}

