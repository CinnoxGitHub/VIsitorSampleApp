package com.cinnox.visitorsampleapp.push

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.m800.cinnox.visitor.CinnoxVisitorCore
import com.m800.sdk.core.noti.CinnoxPushType

class FcmPushService : FirebaseMessagingService() {

    private val TAG = FcmPushService::class.java.simpleName
    private lateinit var mContext: Context

    fun initialize(context: Context) {
        mContext = context
        initToken()
    }

    override fun onNewToken(token: String) {
        updateToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.i(TAG, "onMessageReceived remoteMessage: ${remoteMessage.toLogString()}")
        val data = genFcmRemoteMessageData(remoteMessage)
        Log.i(TAG, "onMessageReceived data: $data")
        data?.let {
            CinnoxVisitorCore.getInstance().getPushManager().handlePushNotification(
                CinnoxPushType.FCM,
                it
            )
        }
    }

    private fun initToken() {
        Log.i(TAG, "initToken")
        FirebaseApp.initializeApp(mContext)?.let {
            Log.i(TAG, "initToken projectId: ${it.options.projectId}, apiKey: ${it.options.apiKey}")
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    val token = task.result
                    if (!token.isNullOrEmpty()) {
                        Log.i(TAG, "initToken token: $token")
                        updateToken(token)
                    }
                })
                .addOnFailureListener {
                    Log.e(TAG, "initToken addOnFailureListener", it)
                }
        }
    }

    private fun updateToken(token: String) {
        CinnoxVisitorCore.getInstance().getPushManager().updateToken(
            CinnoxPushType.FCM,
            token
        )
    }

    fun RemoteMessage.toLogString(): String {
        return "RemoteMessage(collapseKey='$collapseKey', messageId='$messageId', " +
                "messageType=$messageType, sentTime=$sentTime, from=$from, to=$to, data=$data, " +
                "notification=$notification, originalPriority=$originalPriority, " +
                "priority=$priority, ttl=$ttl)"
    }
}