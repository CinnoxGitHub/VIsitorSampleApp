package com.cinnox.visitorsampleapp

import android.app.Application
import com.m800.cinnox.visitor.CinnoxVisitorCore

class MainApplication : Application() {
    companion object {
        const val serviceId = "xxxx.cinnox.com"
    }

    override fun onCreate() {
        super.onCreate()
        CinnoxVisitorCore.initialize(this, serviceId)
    }
}