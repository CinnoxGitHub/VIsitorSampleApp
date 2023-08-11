package com.cinnox.visitorsampleapp.push

import android.content.Intent
import com.huawei.hms.push.RemoteMessage
import org.json.JSONObject

private val TAG = "HuaweiPushHelper"
private const val KEY_DATA = "data"

fun genHuaweiRemoteMessagePushData(remoteMessage: RemoteMessage): JSONObject? {
    return remoteMessage.data?.convertToJson()
}

fun genHuaweiIntentPushData(intent: Intent): JSONObject? {
    return intent.extras?.getString(KEY_DATA)?.convertToJson()
}

