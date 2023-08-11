package com.cinnox.visitorsampleapp.push

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import com.m800.cinnox.visitor.CinnoxVisitorCore
import com.m800.sdk.core.noti.CinnoxPushType
import java.lang.Exception

private const val APP_ID = "huaweiAPPID"

class HuaweiPushService : HmsMessageService() {

    private val TAG = HuaweiPushService::class.java.simpleName
    private lateinit var mContext: Context

    fun initialize(context: Context) {
        mContext = context
        initToken()
    }

    override fun onNewToken(token: String?) {
        Log.i(TAG, "onNewToken token: $token")
        super.onNewToken(token)
        if (!token.isNullOrEmpty()) {
            updateToken(token)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.i(TAG, "onMessageReceived remoteMessage: ${remoteMessage?.toLogString()}")
        super.onMessageReceived(remoteMessage)
        remoteMessage?.let { remoteMessage ->
            val data = genHuaweiRemoteMessagePushData(remoteMessage)
            Log.i(TAG, "onMessageReceived data: $data")
            data?.let {
                CinnoxVisitorCore.getInstance().getPushManager().handlePushNotification(
                    CinnoxPushType.HUAWEI,
                    it
                )
            }
        }
    }

    private fun initToken() {
        Log.i(TAG, "initToken")
        val appInfo = mContext.packageManager.getApplicationInfo(mContext.packageName, PackageManager.GET_META_DATA)
        val appId = appInfo.metaData.get(APP_ID)?.toString()
        Log.i(TAG, "initToken appId: $appId")
        try {
            val token = HmsInstanceId.getInstance(mContext).getToken(appId, "HCM")
            Log.i(TAG, "initToken token: $token")
            if (!token.isNullOrEmpty()) {
                updateToken(token)
            }
        } catch (e: Exception) {
            Log.e(TAG, "initToken error", e)
        }
    }

    private fun updateToken(token: String) {
        CinnoxVisitorCore.getInstance().getPushManager().updateToken(
            CinnoxPushType.HUAWEI,
            token
        )
    }

    fun RemoteMessage.toLogString(): String {
        return "RemoteMessage(collapseKey='$collapseKey', messageId='$messageId', " +
                "messageType=$messageType, sentTime=$sentTime, from=$from, to=$to, data=$data, " +
                "notification=$notification)"
    }
}