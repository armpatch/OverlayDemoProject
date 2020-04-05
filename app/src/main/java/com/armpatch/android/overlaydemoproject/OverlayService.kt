package com.armpatch.android.overlaydemoproject

import android.app.Service
import android.content.Intent
import android.os.IBinder

class OverlayService : Service(), Overlay.ButtonListener {

    lateinit var overlay: Overlay

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        overlay = Overlay(applicationContext, R.layout.layout_overlay)
        overlay.setButtonListener(this)
        overlay.showOverlay()
    }

    override fun onButtonPressed() {
        stopSelf()
    }
}