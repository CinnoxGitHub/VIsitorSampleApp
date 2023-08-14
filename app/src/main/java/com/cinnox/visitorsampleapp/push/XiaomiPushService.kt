package com.cinnox.visitorsampleapp.push

import android.app.ActivityManager
import android.content.Context
import com.xiaomi.mipush.sdk.*
import android.content.pm.PackageManager
import android.util.Log
import com.m800.cinnox.visitor.CinnoxVisitorCore
import com.m800.sdk.core.noti.CinnoxPushType

private const val APP_ID = "xiaomiAPPID"
private const val APP_KEY = "xiaomiAPIKey"

class XiaomiPushService : PushMessageReceiver() {

    private val TAG = XiaomiPushService::class.java.simpleName
    private lateinit var mContext: Context

    fun initialize(context: Context) {
        mContext = context
        initToken()
    }

    override fun onRequirePermissions(contextn: Context?, permissions: Array<out String>?) {
        super.onRequirePermissions(contextn, permissions)
        Log.i(TAG, "onRequirePermissions permissions: $permissions")
    }

    override fun onReceivePassThroughMessage(context: Context, message: MiPushMessage) {
        Log.i(TAG, "onReceivePassThroughMessage message: $message")
        onPushMessage(message)
    }

    override fun onNotificationMessageClicked(context: Context, message: MiPushMessage) {
        Log.i(TAG, "onNotificationMessageClicked message: $message")
    }

    override fun onNotificationMessageArrived(context: Context, message: MiPushMessage) {
        Log.i(TAG, "onNotificationMessageArrived message: $message")
        onPushMessage(message)
    }

    override fun onCommandResult(context: Context, message: MiPushCommandMessage) {
        Log.i(TAG, "onCommandResult message: $message")
        onNewRegId(message)
    }

    override fun onReceiveRegisterResult(context: Context, message: MiPushCommandMessage) {
        Log.i(TAG, "onReceiveRegisterResult message: $message")
        onNewRegId(message)
    }

    private fun initToken() {
        Log.i(TAG, "initToken")
        val appInfo = mContext.packageManager.getApplicationInfo(mContext.packageName, PackageManager.GET_META_DATA)
        val appId = appInfo.metaData.get(APP_ID)?.toString()
        val appKey = appInfo.metaData.get(APP_KEY)?.toString()
        if (!appId.isNullOrEmpty() && !appKey.isNullOrEmpty()) {
            Log.i(TAG, "initToken appId: $appId, appKey: $appKey")
            if (shouldInit()) {
                MiPushClient.registerPush(
                    mContext,
                    appId,
                    appKey
                )
                try {
                    val token = MiPushClient.getRegId(mContext)
                    Log.i(TAG, "initToken token: $token")
                    if (!token.isNullOrEmpty()) {
                        updateToken(token)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "initToken error", e)
                }
            }
        }
    }

    private fun shouldInit(): Boolean {
        val am = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mainProcessName = mContext.packageName
        val myPid = android.os.Process.myPid()
        for (info in am.runningAppProcesses) {
            if (info.pid == myPid && mainProcessName == info.processName) {
                return true
            }
        }
        return false
    }

    private fun onNewRegId(message: MiPushCommandMessage) {
        val arguments = message.commandArguments
        val token = if (arguments != null && arguments.size > 0) arguments.get(0) else null
        if (message.resultCode.toInt() == ErrorCode.SUCCESS && message.command == MiPushClient.COMMAND_REGISTER) {
            if (!token.isNullOrEmpty()) {
                updateToken(token)
            }
        }
    }

    private fun updateToken(token: String) {
        CinnoxVisitorCore.getInstance().getPushManager().updateToken(
            CinnoxPushType.XIAOMI,
            token
        )
    }

    private fun onPushMessage(message: MiPushMessage) {
        val data = genXiaomiRemoteMessagePushData(message)
        Log.i(TAG, "onPushMessage data: $data")
        data?.let {
            CinnoxVisitorCore.getInstance().getPushManager().handlePushNotification(
                CinnoxPushType.XIAOMI,
                it
            )
        }
    }
}