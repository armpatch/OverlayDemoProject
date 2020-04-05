package com.armpatch.android.overlaydemoproject

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
import android.view.WindowManager.LayoutParams.TYPE_PHONE
import android.widget.Button
import androidx.annotation.LayoutRes

class Overlay(private val context: Context, @LayoutRes layoutResId:  Int) {
    private var windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private lateinit var layoutParams: WindowManager.LayoutParams
    lateinit var overlayView: View
    private var isAttached: Boolean = false

    private var listener: ButtonListener? = null

    interface ButtonListener {
        fun onButtonPressed()
    }

    init {
        setLayout(context, layoutResId)
        setDefaultParams()

        val overlayButton: Button = overlayView.findViewById(R.id.overlay_button)
        overlayButton.setOnClickListener {
            listener?.onButtonPressed()
            hideOverlay()
        }
    }

    private fun setDefaultParams() {
        layoutParams = WindowManager.LayoutParams()

        layoutParams.y = -statusBarHeight(context)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = screenHeight(context) + navBarHeight(context) + statusBarHeight(context)
        layoutParams.type =
            if (Build.VERSION.SDK_INT >= 26) TYPE_APPLICATION_OVERLAY else TYPE_PHONE
        layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.format = PixelFormat.TRANSPARENT
        layoutParams.gravity = Gravity.TOP or Gravity.LEFT
    }

    fun showOverlay() {
        if (!isAttached) {
            windowManager.addView(overlayView, layoutParams)
            isAttached = true
        }
    }

    fun hideOverlay() {
        if (isAttached) {
            windowManager.removeView(overlayView)
            isAttached = false
        }
    }

    private fun setLayout(context: Context, @LayoutRes layout: Int) {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        overlayView = inflater.inflate(layout, null)
    }

    fun setButtonListener(listener: ButtonListener) {
        this.listener = listener
    }

}