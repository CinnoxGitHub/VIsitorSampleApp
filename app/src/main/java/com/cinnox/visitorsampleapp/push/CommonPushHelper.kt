package com.cinnox.visitorsampleapp.push

import android.os.Bundle
import android.util.Log
import org.json.JSONObject

private val TAG = "CommonPushHelper"

fun String.convertToJson(): JSONObject? {
    return try {
        JSONObject(this)
    } catch (e: Exception) {
        Log.e(TAG, "String convertToJson error", e)
        null
    }
}

fun Bundle.convertToJson(): JSONObject {
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

